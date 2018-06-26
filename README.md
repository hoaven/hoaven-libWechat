### 一、简介
>基于Java简化微信公众号的开发，本项目几乎封装了所有的开发内容，如：消息关系、菜单管理、用户管理、网页授权等.

### 二、使用
#### 1、创建菜单
- 在`menu-test.json`中写上待创建的菜单内容，菜单项的授权认证url可以调用`ClientTest.getAuthorizeBaseUrl()`获得；
- 调用`WeChatClient.main()`即可创建菜单；
#### 2、二次封装
>本项目只是基础包，其中是不包含公众号的`AppId和Secret`，如果要在自己项目中使用，需要继承本项目的`AbstractWeChatClient`进行二次封装；

**示例：**
```java
/**
 * Created by hoaven on 2017/3/25.
 */
@Slf4j
@Component
public class AliAdminWechatClient extends AbstractWeChatClient implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    AliAdminConfigService aliAdminConfigService;
    @Resource
    RedisTemplate<String, String> kvLockTemplate;

//    private final String accessTokenKey = "aliadmin_new_accessToken";


    @Override
    protected String getAccessToken() {
        String accessTokenKey = aliAdminConfigService.getString("weixin.access.token.redis.key","aliadmin_test_accessToken");
        return kvLockTemplate.opsForValue().get(accessTokenKey);
    }

    @Override
    public String getAppId() {
        return aliAdminConfigService.getString("weixin.app.id", "");
    }

    @Override
    public String getSecret() {
        return aliAdminConfigService.getString("weixin.secret", "");
    }

    public String getEncodingAesKey() {
        return aliAdminConfigService.getString("weixin.encodingAesKey", "");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            this.refreshAccessToken();
        }
    }

    public void refreshAccessToken() {
        String accessTokenKey = aliAdminConfigService.getString("weixin.access.token.redis.key","aliadmin_test_accessToken");
        boolean shouldRefreshAccessToken = false;
        String token = getAccessToken();
        if (token != null) {
            Long expireIn = kvLockTemplate.getExpire(accessTokenKey);
            if (expireIn <= 60 * 30) {
                shouldRefreshAccessToken = true;
                log.info("aliAdlim should refresh accessToken");
            } else {
                boolean isValid = this.verifyAccessToken();
                if (!isValid) {
                    shouldRefreshAccessToken = true;
                }
            }
        } else {
            shouldRefreshAccessToken = true;
        }

        if (shouldRefreshAccessToken) {
            AccessToken accessToken = this.getAccessTokenFormWeChat();
            log.info("refresh accessToken {}", accessToken);
            kvLockTemplate.opsForValue().set(accessTokenKey, accessToken.getAccess_token(), accessToken.getExpires_in(), TimeUnit.SECONDS);
        }
    }
}
```
#### 3、微信服务器校验
**示例：**
```java
/**
 * Created by hoaven on 2017/3/16.
 */
@Slf4j
@Controller
@RequestMapping("/rest/wechat")
public class WeChatMessageListener {
    @Resource
    AliAdminWechatClient aliAdminWechatClient;
    @Resource
    AliAdminConfigService aliAdminConfigService;

    @RequestMapping(value = "/allow/checkSign", method = RequestMethod.GET)
    public void authentication(HttpServletResponse response,
                               @RequestParam String signature,
                               @RequestParam String timestamp,
                               @RequestParam String nonce,
                               @RequestParam String echostr
    ) throws Exception {
        log.info("*******signature=" + signature);
        log.info("*******timestamp=" + timestamp);
        log.info("*******nonce=" + nonce);
        log.info("*******echostr=" + echostr);
        PrintWriter out = response.getWriter();

        String token = aliAdminConfigService.getString("weixin.ali.wechat.check.sign.token", "aliAdmin");

        if (WeChatUtils.checkSignature(token, timestamp, nonce, signature)) {
            out.print(echostr);
        } else {
            log.error("=============微信认证失败============");
        }
    }

    @RequestMapping(value = "/allow/checkSign", method = RequestMethod.POST)
    public void receive(HttpServletResponse response,
                        HttpServletRequest request,
                        @RequestParam String signature,
                        @RequestParam String timestamp,
                        @RequestParam String nonce,
                        @RequestParam(required = false) String encrypt_type,
                        @RequestParam(required = false) String msg_signature
    ) throws Exception {
        String token = aliAdminConfigService.getString("weixin.ali.wechat.check.sign.token", "aliAdmin");
        //使用WXBizMsgCrypt加密/解密
        WXBizMsgCrypt weixin = new WXBizMsgCrypt(token, aliAdminWechatClient.getEncodingAesKey(), aliAdminWechatClient.getAppId());
        String xml = getRequestMessageXml(request, timestamp, nonce, encrypt_type, msg_signature, weixin);
        AcceptableXmlMessage acceptableXmlMessage = WeChatReceiveMessageXmlFactory.create(xml);
        WeChatMessage weChatMessage = (WeChatMessage) acceptableXmlMessage;

        if (weChatMessage.getMsgType() == WeChatMessage.MsgType.event) {
            EventMessage eventMessage = (EventMessage) weChatMessage;
            //关注
            if (eventMessage.getEvent() == EventMessage.Event.subscribe) {
                //第一次进入需要进行oauth2认证
                String loginUrl = aliAdminConfigService.getString("aliadmin.domain", "") +
                        aliAdminConfigService.getString("weixin.aliadmin.oauth2.base.url", "");
                String url = aliAdminWechatClient.getAuthorizeBaseUrl(loginUrl, "login");
                log.info("init assess auth base url {}", url);
                aliAdminWechatClient.sendNewsMessage(weChatMessage.getOpenId(),
                        "欢迎您来到阿里地区日土县干部外出系统", "点我开始注册/登录,我们期待您的加入。", url, null);
            }
        }
    }

    /**
     * 将请求消息转为xml
     */
    public String getRequestMessageXml(HttpServletRequest request,
                                       String timestamp,
                                       String nonce,
                                       String encrypt_type,
                                       String msg_signature,
                                       WXBizMsgCrypt wxBizMsgCrypt) throws IOException, AesException {
        String xml = IOUtils.toString(request.getInputStream());
        if ("aes".equals(encrypt_type)) {
            xml = wxBizMsgCrypt.decryptMsg(msg_signature, timestamp, nonce, xml);
        }
        return xml;
    }

    /**
     * 响应消息
     */
    public void responseMessageXml(PrintWriter out,
                                   String returnXml,
                                   String timestamp,
                                   String nonce,
                                   String encrypt_type,
                                   WXBizMsgCrypt wxBizMsgCrypt) throws AesException, IOException {
        if ("aes".equals(encrypt_type)) {
            returnXml = wxBizMsgCrypt.encryptMsg(returnXml, timestamp, nonce);
        }
        out.print(returnXml);
    }
}
```
#### 4、OAuth2网页授权
**示例：**
```java
/**
 * Created by hoaven on 2017/3/25.
 */
@Slf4j
@Controller
@RequestMapping("/rest/oauth2")
public class OAuth2Controller extends BaseRest {
    @Resource
    AliAdminWechatClient aliAdminWechatClient;

    @Resource
    UserService userService;

    @RequestMapping(value = "/base/allow", method = RequestMethod.GET)
    public String baseOauth(@RequestParam(required = false) String code,
                            @RequestParam(required = false) String state,
                            HttpServletRequest request
    ) {
        if (state != null && code != null) {
            String loginUrl = aliAdminConfigService.getString("weixin.aliadmin.login.h5.url", "");
            String domain = aliAdminConfigService.getString("aliadmin.domain", "");

            UserAccessToken userAccessToken = aliAdminWechatClient.getUserAccessTokenByCode(code);
            String openId = userAccessToken.getOpenid();
            //保存openId -> 供未绑定微信时登录使用
            request.getSession().setAttribute(SESSION_OPEN_ID, openId);

            //检查有没有获取过用户信息
            List<WechatAuth> wechatAuthList = aliAdminBaseService.listQueryBySQL(String.format("openId = '%s'", openId), WechatAuth.class);
            if (wechatAuthList == null || wechatAuthList.size() == 0) {
                WeChatUserInfo weChatUserInfo = aliAdminWechatClient.getWeChatSubscribeUserInfo(openId);
                WechatAuth wechatAuth = convertToWechatAuth(weChatUserInfo);
                aliAdminBaseService.insert(wechatAuth);

                return "redirect:" + domain + loginUrl;
            }

            //检查有没有建立openId--userId关联
            List<UserWechat> userWechatList = aliAdminBaseService.listQueryBySQL(String.format("openId = '%s'", openId), UserWechat.class);
            if (userWechatList == null || userWechatList.size() == 0) {
                return "redirect:" + domain + loginUrl;
            }

            Long userId = userWechatList.get(0).getUserId();
            List<User> userList = aliAdminBaseService.listQueryBySQL(String.format("id = %d", userId), User.class);
            if (userList == null || userList.size() == 0) {
                //脏数据
                return "redirect:" + domain + loginUrl;
            }

            User user = userList.get(0);
            //没有登录则程序自动登录
            if (!SecurityUtils.getSubject().isAuthenticated()) {
                Subject subject = SecurityUtils.getSubject();
                subject.login(new UsernameToken(user.getUsername()));
            }

            //检查用户补充信息是否完成、注册审核是否通过
            List<WorkUnit> workUnitList = aliAdminBaseService.listQueryBySQL("where userId = #{0}", WorkUnit.class, user.getId());
            if (workUnitList == null || workUnitList.size() == 0) {
                log.warn("user {} need profile", user.getId());
                String url = aliAdminConfigService.getString("weixin.aliadmin.need.profile.url", "");
                return "redirect:" + domain + url;
            } else if (!RegisterStatus.审批通过.toString().equals(user.getVerifyStatus())) {
                log.warn("user {} wait register approval", user.getId());
                String url = aliAdminConfigService.getString("weixin.aliadmin.wait.register.approval.url", "");
                return "redirect:" + domain + url;
            }

            String url = aliAdminConfigService.getString("weixin.outh2.redirect.h5.url." + state, "");
            String stateKeys = aliAdminConfigService.getString("weixin.outh2.states", "");
            log.info("openId {} click {}", openId, state);

            if (stateKeys.contains(state)) {
                return "redirect:" + domain + url;
            } else {
                return "redirect:" + domain + loginUrl;
            }
        }
        return "";
    }
}
```

package com.love.iLove.filter.qq;

import com.love.iLove.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QQAuthenticationFilter  implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private final static String CODE = "code";

    /**
     * 获取 Token 的 API
     */
    private final static String accessTokenUri = "https://graph.qq.com/oauth2.0/token";

    /**
     * grant_type 由腾讯提供
     */
    private final static String grantType = "authorization_code";

    /**
     * client_id 由腾讯提供
     */
    static final String clientId = "101555884";

    /**
     * client_secret 由腾讯提供
     */
    private final static String clientSecret = "f971d329363d602c0b24e08230c7756d";

    /**
     * redirect_uri 腾讯回调地址
     */
    private final static String redirectUri = "https://tianzijiaozi.top/login/qqLogin";

    /**
     * 获取 OpenID 的 API 地址
     */
    private final static String openIdUri = "https://graph.qq.com/oauth2.0/me?access_token=";

    /**
     * 获取 token 的地址拼接
     */
    private final static String TOKEN_ACCESS_API = "%s?grant_type=%s&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    public QQAuthenticationFilter(String defaultFilterProcessesUrl) {
//        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, "GET"));
    }

//    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter(CODE);
        log.debug("code:{}",code);
        String tokenAccessApi = String.format(TOKEN_ACCESS_API, accessTokenUri, grantType, clientId, clientSecret, code, redirectUri);
        QQToken qqToken = this.getToken(tokenAccessApi);
        log.debug("qqToken:{}",qqToken);

        if (qqToken != null){
            String openId = getOpenId(qqToken.getAccessToken());//获取用户的openId
            log.debug("openId:{}",openId);
            //根据openId和默认密码创建一个用户
            UserRoleService userRoleService = (UserRoleService) applicationContext.getBean("userRoleService");
            Integer r = userRoleService.regist(openId,"123456");
            if (r!=null&openId != null){
                // 生成验证 authenticationToken
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(qqToken.getAccessToken(), "123456");
                // 返回验证结果
//                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        return null;
    }

    /**
     * 请求验证登录通过后，调用此方法，源码中调用successHandler，直接跳转到登录成功的url
     * 在这里，先写成继续调用filter链
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    protected void successfulAuthentication(HttpServletRequest request,HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        chain.doFilter(request,response);
    }

    private QQToken getToken(String tokenAccessApi) throws IOException{
        Document document = Jsoup.connect(tokenAccessApi).get();
        String tokenResult = document.text();
        String[] results = tokenResult.split("&");
        if (results.length == 3){
            QQToken qqToken = new QQToken();
            String accessToken = results[0].replace("access_token=", "");
            int expiresIn = Integer.valueOf(results[1].replace("expires_in=", ""));
            String refreshToken = results[2].replace("refresh_token=", "");
            qqToken.setAccessToken(accessToken);
            qqToken.setExpiresIn(expiresIn);
            qqToken.setRefresh_token(refreshToken);
            return qqToken;
        }
        return null;
    }

    private String getOpenId(String accessToken) throws IOException{
        String url = openIdUri + accessToken;
        Document document = Jsoup.connect(url).get();
        String resultText = document.text();
        Matcher matcher = Pattern.compile("\"openid\":\"(.*?)\"").matcher(resultText);
        if (matcher.find()){
            return matcher.group(1);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        QQAuthenticationFilter.applicationContext = applicationContext;
    }


    class QQToken {

        /**
         * token
         */
        private String accessToken;

        /**
         * 有效期
         */
        private int expiresIn;

        /**
         * 刷新时用的 token
         */
        private String refresh_token;

        String getAccessToken() {
            return accessToken;
        }

        void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }
    }


}

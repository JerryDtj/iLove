package com.love.iLove.filter.qq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.love.iLove.domain.User;
import com.love.iLove.domain.UserDetail;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.love.iLove.filter.qq.QQAuthenticationFilter.clientId;


public class QQAuthenticationManager implements AuthenticationManager {
    private static final List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

    /**
     * 获取 QQ 登录信息的 API 地址
     */
    private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

    /**
     * 获取 QQ 用户信息的地址拼接
     */
    private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        if (auth.getName() != null && auth.getCredentials() != null) {
            UserDetail userDetails = getUserInfo(auth.getName(), (String) (auth.getCredentials()));
            User user = new User();
            user.setUsername("111");
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode("111"));
            return new UsernamePasswordAuthenticationToken(user,null, AUTHORITIES);
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    private UserDetail getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_API, userInfoUri, accessToken, clientId, openId);
        Document document;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BadCredentialsException("Bad Credentials!");
        }
        String resultText = document.text();
        JSONObject json = JSON.parseObject(resultText);

        UserDetail userDetails = new UserDetail();
        userDetails.setNickName(json.getString("nickname"));
        userDetails.setGender(json.getInteger("gender"));
        userDetails.setProvince(json.getString("province"));
        userDetails.setYear(json.getDate("year"));
        userDetails.setAvatar(json.getString("figureurl_qq_2"));
        return userDetails;
    }
}

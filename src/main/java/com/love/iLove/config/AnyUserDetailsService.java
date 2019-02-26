package com.love.iLove.config;

import com.love.iLove.domain.User;
import com.love.iLove.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.get(username);
        if (user==null){
            throw new RuntimeException("user is null");
        }
        List<SimpleGrantedAuthority> list = this.getCrantedAuthority(user.getRoles());
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
    }

    private List<SimpleGrantedAuthority> getCrantedAuthority(String roles) {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        String[] role = roles.split(",");
        for (String s:role){
            list.add(new SimpleGrantedAuthority(s));
        }
        return list;
    }
}

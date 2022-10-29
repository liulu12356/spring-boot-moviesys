package com.qf.moviesys.security.service;


import com.qf.moviesys.dao.UserMapper;
import com.qf.moviesys.pojo.UserInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Log4j2
public class MyUserDetailsService implements UserDetailsService {

    // private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("{} -> {}", "UserDetailsService", "loadUserByUsername");
        UserInfo userInfo = null;

        try {
            // QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            // wrapper.eq("username", username);



            userInfo = userMapper.selectByUsername(username);

            // FIXME 以后补充基于方法颗粒度的鉴权：适可而止（用户敏感信息、财务相关）
            userInfo.setMenus(Arrays.asList("类别信息管理", "电影基本信息管理"));

            // 从db中查询出用户信息，然后在业务逻辑中进行判断是否合法
            // select * from user_info where username=? and password=?
            // 在业务逻辑中 SpringSecurity 为了保护密码，做了很多类似于加密工作，为的是不让密码以“明文”方式呈现在代码逻辑中（日志中）

            // select * from user_info where username=?

            // 先创建一个基于ROLE_USER（“角色名称”）的一套权限
            List<GrantedAuthority> authorities =
                    // AuthorityUtils.commaSeparatedStringToAuthorityList("create,search");
                    // AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,ROLE_user,ROLE_guest");
                    AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_default");

            userInfo.setAuthorities(authorities);
        } catch (Exception e) {
            log.error("认证出现异常:{}", e.getMessage());
            System.out.println(e);
            // if (userInfo == null) throw new UsernameNotFoundException("用户未找到");

            // e是造成当前UsernameNotFoundException出现的原因
            throw new UsernameNotFoundException("用户未找到", e);// 一定要把原始的异常作为cause(底层原因)参数提交
        }


        // 此处admin是后台db中真正的密码
        // 以后在db中密码是以“密文”方式存储的 aadsfasdf-asdfasdf-asdfadsfas

        return userInfo;
    }
}

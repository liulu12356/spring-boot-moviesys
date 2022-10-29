package com.qf.moviesys.security.filter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.moviesys.pojo.UserInfo;
import com.qf.moviesys.util.RedisTokenUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
// @WebFilter("/????")
public class JwtFilter extends OncePerRequestFilter { // 保证每一个请求指挥触发一次doFilter逻辑

    @Autowired
    RedisTokenUtil redisTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 首先从request中获取token信息
        String jwtToken = request.getHeader("jwtToken");
        log.debug("doFilterInternal:{}", jwtToken);

        if (StringUtils.isEmpty(jwtToken)) { // jwtToken==null or "".equals(jwtToken)
            filterChain.doFilter(request, response); // 放行，让后续的过滤器链进行认证
            return;
        }

        // jwtToken证明是非登录请求，需要对jwtToken进行有效性检查
        try {
            // Jws<Claims> claims = JwtUtils.getClaims(jwtToken);
            // String subject = claims.getBody().getSubject();
            // ObjectMapper mapper = new ObjectMapper();
            // UserInfo info = mapper.readValue(subject, UserInfo.class);
            final UserInfo info = redisTokenUtil.get(jwtToken);


            Object token = request.getServletContext().getAttribute(info.getId().toString());


            log.info("doFilterInternal:{}", "认证通过...");

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    info.getUsername(),
                    null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList("default"));
            // authentication.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 把请求往后传递（DispatcherServlet -> Controller）
            filterChain.doFilter(request, response);


        } catch (Exception e) {
            log.error("Token无效", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws JsonProcessingException {


        // String json = "{\\\"id\\\":7,\\\"username\\\":\\\"root\\\",\\\"password\\\":null,\\\"roleId\\\":null,\\\"enabled\\\":true,\\\"authorities\\\":null,\\\"accountNonExpired\\\":true,\\\"credentialsNonExpired\\\":true,\\\"accountNonLocked\\\":true}";
        UserInfo userInfo = new ObjectMapper().readValue("{\"id\":7,\"username\":\"root\"}", UserInfo.class);
        System.out.println(userInfo.getId());
    }
}

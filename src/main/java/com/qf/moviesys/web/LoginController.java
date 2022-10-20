package com.qf.moviesys.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.moviesys.dao.MenuMapper;
import com.qf.moviesys.pojo.Menu;
import com.qf.moviesys.pojo.ResultVO;
import com.qf.moviesys.pojo.UserInfo;
import com.qf.moviesys.util.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager; // 过滤器链的入口

    @Autowired
    ServletContext servletContext;




    @PostMapping("/checkLogin")
    public ResultVO<String> checkLogin(@RequestBody UserInfo userInfo) {
        log.info("前端{}",userInfo);

        try {
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), userInfo.getPassword());

            // 得到认证之后的信息
            Authentication authentication = authenticationManager.authenticate(authRequest);
            System.out.println(authentication);

            // 判断认证结果
            if (authentication == null) return new ResultVO<>(401, "认证失败");

            log.error(authentication.getPrincipal());

            // 根据认证通过的authentication包装token，返回客户端
            String token = wrapAndStoreToken(authentication);

            log.debug(token);
            return new ResultVO<>(200, "登录成功", token);
        } catch (AuthenticationException e) {
            log.error("登录发生异常", e);
            throw new RuntimeException(e);
        }
    }

    @Autowired
    MenuMapper menuMapper;

    @GetMapping("/loadMenus") // 前端需要携带jwtToken来访问该接口
    public ResultVO<List<Menu>> loadMenus(Integer roleId) {
        // 解析token，获取用户信息roleId - 前端直接提交
        // 查询出来的菜单信息是平行结构
        List<Menu> menuList = menuMapper.loadMenus(roleId); // roleId = 1 size = 12
        // 安排层级关系
        List<Menu> menus = handleMenus(menuList); // size = 3
        // JsonUtils.writeJSON(menus, resp);


        // 根据用户信息来加载菜单
        return new ResultVO<>(HttpStatus.OK.value(), "success", menus);
    }

    private static List<Menu> handleMenus(List<Menu> menuList) {
        // parenId == null 根节点
        List<Menu> menus = findRoot(menuList); // 有3个根节点
        // path == null 有子节点
        loadChildren(menus, menuList);
        return menus;
    }

    private static void loadChildren(List<Menu> parentList, List<Menu> menuList) {
        parentList.forEach(parent -> {
            // 到menuList中查找，哪些元素是parent的子节点
            List<Menu> children = menuList.stream()
                    .filter(menu -> menu.getParentId() == parent.getId())
                    .sorted(Comparator.comparing(Menu::getSeq))
                    .collect(Collectors.toList());
            parent.setChildren(children);
            // loadChildren(children, menuList);

            List<Menu> collect = children.stream().filter(menu -> menu.getPath() == null).collect(Collectors.toList());
            loadChildren(collect, menuList);
        });
    }

    private static List<Menu> findRoot(List<Menu> menuList) {
        return menuList.stream().filter((menu -> menu.getParentId() == null || menu.getParentId() == 0)).collect(Collectors.toList());
    }

    private String wrapAndStoreToken(Authentication authentication) {
        UserInfo info = (UserInfo) authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            info.setPassword("***"); // 切记不要暴露密码给客户端
            info.setMenus(null);
            // userInfo:{username:xxx,password:xxx,roleId:1}
            String json = objectMapper.writeValueAsString(info);

            // 在容器中保留token
            // SecurityContextHolder.getContext().setAuthentication(authentication); // session.setAttribute(threadId, new UsernamePasswordToken());
            servletContext.setAttribute(info.getId().toString(), json);
            return JwtUtils.generateToken(json);
        } catch (JsonProcessingException e) {
            log.error("JSON解析出现异常", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/logout")
    public ResultVO<String> logout() {
        // 从请求中获取token，得到user的id
        servletContext.removeAttribute("0");
        return new ResultVO<>(200, "注销成功");
    }

}

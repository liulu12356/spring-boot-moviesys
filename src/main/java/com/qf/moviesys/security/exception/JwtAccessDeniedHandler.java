package com.qf.moviesys.security.exception;


import com.qf.moviesys.pojo.ResultVO;
import com.qf.moviesys.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Log4j2
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error("JwtAccessDeniedHandler:handle()=>{}", accessDeniedException.getMessage());
        //以JSON格式给前端响应
        ResultVO<String> vo = new ResultVO<>(HttpStatus.FORBIDDEN.value(), "鉴权失败");
        try {
            JsonUtils.writeToJson(response, vo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

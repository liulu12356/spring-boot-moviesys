package com.qf.moviesys.util;


import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonUtils {

    public static void writeToJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String json=  new ObjectMapper().writeValueAsString(data);
        response.getWriter().print(json);
    }

}

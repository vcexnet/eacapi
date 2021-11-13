package com.dhj.eac_api.interceptor;

import com.dhj.eac_api.conf.RpcAndWebParamProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: WebSafeInterceptor
 * @author: dhj
 * @date: 2021/11/4 19:50
 * @version: v1.0
 */
@Slf4j
@Component
public class WebOrApiOpenInterceptor implements HandlerInterceptor {

    @Autowired
    RpcAndWebParamProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.info("拦截器拦截路径：{}", uri);
        System.out.println(properties.toString());
        if (!properties.isOpenWeb() && !properties.isOpenApi()) {
            returnJson(response, "<h3>服务器访问已关闭！请联系管理员！</h3>");
            return false;
        } else if (!properties.isOpenWeb() && "/".equals(uri)) {
            if (properties.isOpenApi() && !"/".equals(uri)) {
                return true;
            }
            returnJson(response, "<h3>web 访问已关闭！请联系管理员！</h3>");
            return false;
        } else if (!properties.isOpenApi()) {
            if (properties.isOpenWeb() && "/".equals(uri)) {
                return true;
            }
            returnJson(response, "<h3>api 访问已关闭！请联系管理员！</h3>");
            return false;
        }
        return true;
    }

    /**
     * 拦截成功，向前端响应数据
     *
     * @param response resp 对象
     * @param json     json 数据
     */
    private void returnJson(HttpServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

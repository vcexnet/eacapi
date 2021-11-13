package com.dhj.eac_api.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: RpcUriUtil
 * @author: dhj
 * @date: 2021/11/5 11:32
 * @version: v1.0
 */
public class RpcUriUtil {

    /**
     * 带路径参数的 uri 格式化
     *
     * @param request 请求对象
     * @return 返回格式化后的 uri
     */
    public static String[] formatUri(HttpServletRequest request) {
        return request.getRequestURI().split("/");
    }
}

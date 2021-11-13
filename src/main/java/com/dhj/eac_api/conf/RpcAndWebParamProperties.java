package com.dhj.eac_api.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description: ApiParamProperties
 * @author: dhj
 * @date: 2021/10/30 23:39
 * @version: v1.0
 */
@ConfigurationProperties(prefix = "conf")
public class RpcAndWebParamProperties {
    /**
     * rpc 主机地址
     */
    private String rpcip;

    /**
     * rpc 端口号
     */
    private String rpcport;

    /**
     * rpc 用户名
     */
    private String rpcuser;

    /**
     * rpc 用户密码
     */
    private String rpcpassword;

    /**
     * api 对外访问接口
     */
    private String apiurl;

    /**
     * api 端口
     */
    private String apiport;

    /**
     * 是否启用 usessl
     */
    private boolean usessl;

    /**
     * 是否启用 web 访问功能
     */
    private boolean isOpenWeb;

    private boolean isOpenApi;

    public boolean isOpenApi() {
        return isOpenApi;
    }

    public void setOpenApi(boolean openApi) {
        isOpenApi = openApi;
    }

    public boolean isOpenWeb() {
        return isOpenWeb;
    }

    public void setOpenWeb(boolean openWeb) {
        isOpenWeb = openWeb;
    }

    public String getRpcip() {
        return rpcip;
    }

    public void setRpcip(String rpcip) {
        this.rpcip = rpcip;
    }

    public String getRpcport() {
        return rpcport;
    }

    public void setRpcport(String rpcport) {
        this.rpcport = rpcport;
    }

    public String getRpcuser() {
        return rpcuser;
    }

    public void setRpcuser(String rpcuser) {
        this.rpcuser = rpcuser;
    }

    public String getRpcpassword() {
        return rpcpassword;
    }

    public void setRpcpassword(String rpcpassword) {
        this.rpcpassword = rpcpassword;
    }

    public String getApiurl() {
        return apiurl;
    }

    public void setApiurl(String apiurl) {
        this.apiurl = apiurl;
    }

    public String getApiport() {
        return apiport;
    }

    public void setApiport(String apiport) {
        this.apiport = apiport;
    }

    public boolean isUsessl() {
        return usessl;
    }

    public void setUsessl(boolean usessl) {
        this.usessl = usessl;
    }

    @Override
    public String toString() {
        return "ApiParamProperties{" +
                "rpcip='" + rpcip + '\'' +
                ", rpcport='" + rpcport + '\'' +
                ", rpcuser='" + rpcuser + '\'' +
                ", rpcpassword='" + rpcpassword + '\'' +
                ", apiurl='" + apiurl + '\'' +
                ", apiport='" + apiport + '\'' +
                ", usessl=" + usessl +
                '}';
    }
}

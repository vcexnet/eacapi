package com.dhj.eac_api.controller;

import com.dhj.eac_api.service.ApiService;
import com.dhj.eac_api.utils.RpcClient;
import com.dhj.eac_api.utils.RpcUriUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description: ApiController
 * @author: dhj
 * @date: 2021/10/31 0:33
 * @version: v1.0
 */
@Slf4j
@RestController
public class ApiController {

    @Autowired
    RpcClient rpcClient;

    @Autowired
    ApiService apiService;

    /**
     * 获取区块链信息（无参）
     */
    @GetMapping("/getinfo")
    public String getinfo(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 区块链节点信息（无参）
     */
    @GetMapping("/getpeerinfo")
    public String getpeerinfo(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 得到块高度（无参）
     */
    @GetMapping("/getblockcount")
    public String getblockcount(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 区块链难度（无参）
     */
    @GetMapping("/getdifficulty")
    public String getdifficulty(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 块 hash（有参）,使用 /** 匹配任意数量的路径参数
     */
    @GetMapping("/getblockhash/**")
    public String getblockhash(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 最新区块链 hash（无参）
     */
    @GetMapping("/getbestblockhash")
    public String getbestblockhash(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 得到块高度（有参）
     */
    @GetMapping("/getblock/**")
    public String getblock(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * 获得交易信息（有参）
     */
    @GetMapping("/getrawtransaction/**")
    public String getrawtransaction(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * gettxout（有参）
     */
    @GetMapping("/gettxout/**")
    public String gettxout(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * sendrawtransaction（有参）
     */
    @GetMapping("/sendrawtransaction/**")
    public String sendrawtransaction(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * sendcoins（有参）
     */
    @GetMapping("/sendcoins/**")
    public String sendcoins(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * validateaddress（有参）
     */
    @GetMapping("/validateaddress/**")
    public String validateaddress(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * getnetworkhashps（无参）
     */
    @GetMapping("/getnetworkhashps")
    public String getnetworkhashps(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * unspent（有参）
     */
    @GetMapping("/unspent")
    public String unspent(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * getbalance（有参）
     */
    @GetMapping("/getbalance/**")
    public String getbalance(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * checktransaction（有参）
     */
    @GetMapping("/checktransaction/**")
    public String checktransaction(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * explorer（有参）
     */
    @GetMapping("/explorer")
    public String explorer(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * transaction（有参）
     */
    @GetMapping("/transaction")
    public String transaction(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * addressinfo（有参）
     */
    @GetMapping("/addressinfo")
    public String addressinfo(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }

    /**
     * balanc（有参）
     */
    @GetMapping("/balance")
    public String balance(HttpServletRequest request) {
        return apiService.resp(RpcUriUtil.formatUri(request));
    }
}

package com.dhj.eac_api.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhj.eac_api.model.getinfo.GetInfoPojo;
import com.dhj.eac_api.model.getpeerinfo.GetPeerInfoRoot;
import com.dhj.eac_api.model.vo.PageVO;
import com.dhj.eac_api.service.ApiService;
import com.dhj.eac_api.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description: WebController
 * @author: dhj
 * @date: 2021/11/4 19:57
 * @version: v1.0
 */
@Controller
public class WebController {
    @Autowired
    private ApiService apiService;

    private static final int COUNT = 5;

    @GetMapping("/")
    public String index(Integer pageNum, Model model) {
        String getInfoJson = apiService.resp(new String[]{"", "getinfo"});
        /*
        获取所有信息
         */
        // 区块链信息
        GetInfoPojo getInfoPojo = JSONObject.parseObject(getInfoJson, GetInfoPojo.class);

        // 区块链节点信息
        String peerInfoJson = apiService.resp(new String[]{"", "getpeerinfo"});
        List<GetPeerInfoRoot> getPeerInfoRootsCache = JSONObject.parseArray(peerInfoJson, GetPeerInfoRoot.class);
        // 分页数据获取
        if (pageNum == null) {
            pageNum = 1;
        }
        PageVO<GetPeerInfoRoot> page = WebService.page(getPeerInfoRootsCache, pageNum, COUNT);
        model.addAttribute("getInfoPojo", getInfoPojo);
        model.addAttribute("page", page);
        return "index";
    }
}

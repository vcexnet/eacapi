package com.dhj.eac_api.service;


import com.dhj.eac_api.model.getpeerinfo.GetPeerInfoRoot;
import com.dhj.eac_api.model.vo.PageVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: ApiDataService
 * @author: dhj
 * @date: 2021/11/3 9:28
 * @version: v1.0
 */
public class WebService {

    /**
     * api 数据分页
     *
     * @param pageSource 分页数据源
     * @param pageNum    当前页
     * @return 返回页面数据
     */
    public static PageVO<GetPeerInfoRoot> page(List<GetPeerInfoRoot> pageSource, int pageNum, int count) {
        // 分页模型
        PageVO<GetPeerInfoRoot> page = new PageVO<>();
        // 总记录数
        int totalCount = pageSource.size();
        // 总页数
        int totalPage = totalCount % count == 0 ? totalCount / count : (totalCount / count) + 1;
        page.setTotalPage(totalPage);
        page.setTotalCount(totalCount);
        int limit = (pageNum - 1) * count;
        if (pageNum == page.getTotalPage()) {
            page.setRecords(pageSource.subList(limit, (limit + totalPage % count) + 1));
        } else {
            page.setRecords(pageSource.subList(limit, limit + count));
        }
        page.setPageNum(pageNum);

        List<Integer> pageCodes = new ArrayList<>();
        for (int i = 0; i < page.getTotalPage(); i++) {
            pageCodes.add(i + 1);
        }
        page.setPageCodes(pageCodes);
        return page;
    }
}

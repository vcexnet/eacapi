package com.dhj.eac_api.model.getpeerinfo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @description: GetPeerInfoRoot
 * @author: dhj
 * @date: 2021/11/2 20:05
 * @version: v1.0
 */
@Data
@ToString
public class GetPeerInfoRoot {
    private long synced_headers;
    private int banscore;
    private boolean inbound;
    private long bytesrecv;
    private long lastsend;
    private BytessentPerMsg bytessent_per_msg;
    private long bytessent;
    private boolean relaytxes;
    private long conntime;
    private int id;
    private String addrlocal;
    private String addr;
    private int timeoffset;
    private String services;
    private long lastrecv;
    private long version;
    private BytesrecvPerMsg bytesrecv_per_msg;
    private long synced_blocks;
    private List<String> inflight;
    private double minping;
    private String addrbind;
    private boolean addnode;
    private double pingtime;
    private long startingheight;
    private boolean whitelisted;
    private String subver;
}

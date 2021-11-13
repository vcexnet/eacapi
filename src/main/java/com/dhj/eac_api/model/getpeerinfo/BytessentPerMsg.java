package com.dhj.eac_api.model.getpeerinfo;

import lombok.Data;
import lombok.ToString;

/**
 * @description: BytessentPerMsg
 * @author: dhj
 * @date: 2021/11/2 20:06
 * @version: v1.0
 */
@Data
@ToString
public class BytessentPerMsg {
    private long headers;
    private int sendcmpct;
    private int sendheaders;
    private int tx;
    private long ping;
    private int verack;
    private int feefilter;
    private int getaddr;
    private long getdata;
    private int getheaders;
    private long pong;
    private int version;
    private int inv;
    private long cmpctblock;
    private int addr;
}

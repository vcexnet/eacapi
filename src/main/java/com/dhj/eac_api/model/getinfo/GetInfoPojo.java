package com.dhj.eac_api.model.getinfo;

import lombok.Data;
import lombok.ToString;

/**
 * @description: GetInfoPojo
 * @author: dhj
 * @date: 2021/11/2 20:04
 * @version: v1.0
 */
@Data
@ToString
public class GetInfoPojo {
    private double difficulty;
    private long headers;
    private String chain;
    private String chainwork;
    private long size_on_disk;
    private long mediantime;
    private double verificationprogress;
    private long blocks;
    private boolean pruned;
    private String warnings;
    private String bestblockhash;
    private boolean initialblockdownload;
}

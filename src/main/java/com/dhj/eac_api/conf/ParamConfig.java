/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dhj.eac_api.conf;

import com.dhj.eac_api.utils.RpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RpcAndWebParamProperties.class)
public class ParamConfig {

    @Autowired
    RpcAndWebParamProperties properties;

    @Bean
    public RpcClient rpcClient() {
        System.out.println(properties.toString());
        return new RpcClient(
                properties.getRpcip(),
                Integer.parseInt(properties.getRpcport()),
                properties.getRpcuser(),
                properties.getRpcpassword()
        );
    }
}

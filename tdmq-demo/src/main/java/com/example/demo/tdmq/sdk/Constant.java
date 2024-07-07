package com.example.demo.tdmq.sdk;

/**
 * @author zhangtao
 * @since 2024/7/7 16:12
 */
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;


public class Constant {


    /**
     * 服务接入地址，位于【集群管理】页面接入地址
     */
    private static final String SERVICE_URL = "pulsar://pulsar-test.xiaoying.io:6650";

    /**
     * 要使用的命名空间授权的角密钥，位于【角色管理】页面
     */
    private static final String AUTHENTICATION = System.getenv("TDMQ_SECRET");


    /**
     * 初始化pulsar客户端
     *
     * @return pulsar客户端
     */
    public static PulsarClient initPulsarClient() throws PulsarClientException {
        // 一个Pulsar client对应一个客户端链接
        // 原则上一个进程一个client，尽量避免重复创建，消耗资源
        // 关于客户端和生产消费者的实践教程，可以参考官方文档 https://cloud.tencent.com/document/product/1179/58090
        PulsarClient pulsarClient = PulsarClient.builder()
                // 服务接入地址
                .serviceUrl(SERVICE_URL)
                // 授权角色密钥
                .authentication(AuthenticationFactory.token(AUTHENTICATION)).build();
        System.out.println(">> pulsar client created.");
        return pulsarClient;
    }
}


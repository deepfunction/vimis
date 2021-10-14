package ru.akrtkv.egisz.vimis.service;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Service
public class ServicesDiscoveryService {

    @Value("${zookeeper.url}")
    String zookeeperUrl;

    @Value("${service.address}")
    String serviceAddress;

    @Value("${server.port}")
    int servicePort;

    private CuratorFramework curatorFramework;

    private ServiceDiscovery<Object> serviceDiscovery;

    private ServiceInstance<Object> serviceInstance;

    @PostConstruct
    void registerService() throws Exception {
        curatorFramework = CuratorFrameworkFactory.newClient(
                zookeeperUrl,
                new RetryNTimes(5, 1000)
        );
        curatorFramework.start();

        curatorFramework.blockUntilConnected(5, TimeUnit.SECONDS);

        if (curatorFramework.getZookeeperClient().isConnected()) {
            serviceInstance = ServiceInstance
                    .builder()
                    .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                    .address(serviceAddress)
                    .port(servicePort)
                    .name("vimis")
                    .build();

            serviceDiscovery = ServiceDiscoveryBuilder
                    .builder(Object.class)
                    .basePath("services")
                    .client(curatorFramework)
                    .thisInstance(serviceInstance)
                    .build();
            serviceDiscovery.start();

            curatorFramework.setData().forPath("/services/vimis", "online".getBytes(StandardCharsets.UTF_8));
        } else {
            curatorFramework.close();
        }
    }

    @PreDestroy
    void unregisterService() throws Exception {
        if (!curatorFramework.getState().name().equals("STOPPED")) {
            curatorFramework.setData().forPath("/services/vimis", "offline".getBytes(StandardCharsets.UTF_8));
            serviceDiscovery.unregisterService(serviceInstance);
        }
    }
}

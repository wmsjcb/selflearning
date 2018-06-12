package com.learn.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorSelfDemo {

    public static void main(String[] args) {

        try{
            CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                    .connectString("192.168.1.155:2181,192.168.1.156:2181,192.168.1.157:2181")
                    .sessionTimeoutMs(4000).retryPolicy(new ExponentialBackoffRetry(1000,3))
                    .namespace("curator").build();
            curatorFramework.start();

            curatorFramework.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT).forPath("/ceshi/node1","123".getBytes());
            Stat stat=new Stat();
            byte[] bytes= curatorFramework.getData().storingStatIn(stat).forPath("/ceshi/node");
            System.out.println(new String(bytes));
            curatorFramework.setData().withVersion(stat.getVersion()).forPath("/ceshi/node","abc".getBytes());
            curatorFramework.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

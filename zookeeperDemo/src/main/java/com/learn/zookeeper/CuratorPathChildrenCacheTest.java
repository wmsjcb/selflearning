package com.learn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

public class CuratorPathChildrenCacheTest {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = getClient();
        String parentPath  = "/ceshi";
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,parentPath,true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println("事件类型："  + event.getType() + "；操作节点：" + event.getData().getPath());

            }
        });
        String path = "/ceshi/d1";
        client.create().withMode(CreateMode.PERSISTENT).forPath(path);
       // Thread.sleep(1000); // 此处需留意，如果没有现成睡眠则无法触发监听事件
        client.delete().forPath(path);

        Thread.sleep(15000);


    }

    public  static CuratorFramework getClient(){
        CuratorFramework curatorFramework=CuratorFrameworkFactory.
                builder().connectString("192.168.1.155:2181," +
                "192.168.1.156:2181,192.168.1.157:2181").
                sessionTimeoutMs(4000).retryPolicy(new
                ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
        curatorFramework.start();
        return curatorFramework;
    }
}

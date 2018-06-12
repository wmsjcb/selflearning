package com.learn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorNodeCacheTest {
    public static void main(String[] args) throws Exception {

        CuratorFramework client = getClient();
        String path = "/ceshi";
        final NodeCache nodeCache = new NodeCache(client,path);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("监听事件触发");
                System.out.println("重新获得节点内容为：" + new String(nodeCache.getCurrentData().getData()));

            }
        });
        client.setData().forPath(path,"456".getBytes());
        client.setData().forPath(path,"789".getBytes());
        client.setData().forPath(path,"123".getBytes());
        client.setData().forPath(path,"222".getBytes());
        client.setData().forPath(path,"333".getBytes());
        client.setData().forPath(path,"444".getBytes());
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

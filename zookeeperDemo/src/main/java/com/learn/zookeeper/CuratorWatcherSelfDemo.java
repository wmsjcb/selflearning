package com.learn.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.io.IOException;

public class CuratorWatcherSelfDemo {

    public static void main(String[] args) throws IOException {
        CuratorFramework curatorFramework=CuratorFrameworkFactory.
                builder().connectString("192.168.11.153:2181," +
                "192.168.11.154:2181,192.168.11.155:2181").
                sessionTimeoutMs(4000).retryPolicy(new
                ExponentialBackoffRetry(1000,3)).
                namespace("curator").build();
           curatorFramework.start();
        addListenerWithTreeCache(curatorFramework,"/ceshi");
        System.in.read();





    }


    public static void addListenerWithPathChildCache(CuratorFramework curatorFramework,String path){
        try {
            PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,path,true);
            PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                    System.out.println("Receive Event:"+event.getType());
                }
            };

            pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
            pathChildrenCache.start(PathChildrenCache.StartMode.NORMAL);

        }catch (Exception e){
            e.printStackTrace();
        }



    }

    public  static  void addListenerWithTreeCache(CuratorFramework curatorFramework,String path){
        TreeCache treeCache = new TreeCache(curatorFramework,path);
        TreeCacheListener treeCacheListener = new TreeCacheListener(){

            @Override
            public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                System.out.println(event.getType()+"->"+event.getData().getPath());
            }
        };

    }




}

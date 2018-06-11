package com.learn.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

public class ZKConnection {

    public static void main(String[] args) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(1);

//            ZooKeeper zooKeeper = new ZooKeeper("192.168.1.155:2181,192.168.1.156:2181,192.168.1.157:2181"5000,null);
//
//           // zooKeeper.create("/zk-java","testJava".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//            zooKeeper.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            //System.out.println("》》》》》》"+zooKeeper.getData("/zk-book",false,null));

            //ountDownLatch.await();
           // Thread.sleep(1000);
           // System.out.println(zooKeeper.getState());
         System.out.println("11s3431");


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}


package com.learn.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZKConnection {

    public static void main(String[] args) {
        ZKConnection zkConnection = new ZKConnection();
        //不带监听
       //zkConnection.noWatcher();

        //带监听
        zkConnection.withWatcher();
    }

    public void withWatcher(){
     try {
           final CountDownLatch countDownLatch = new CountDownLatch(1);
         ZooKeeper zooKeeper = new ZooKeeper("192.168.1.155:2181", 4000, new Watcher() {
             @Override
             public void process(WatchedEvent event) {
                 if(Event.KeeperState.SyncConnected == event.getState()){

                     countDownLatch.countDown();
                 }
             }
         });
         countDownLatch.await();
         //必须父节点存在才能创建
         zooKeeper.create("/zk-boo/abc", "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
         System.out.println(zooKeeper.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  void noWatcher(){

        try{
            ZooKeeper zooKeeper = new ZooKeeper("192.168.1.155:2181,192.168.1.156:2181,192.168.1.157:2181",5000,null);
            //
            //zooKeeper.create("/testRootPath", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            Stat stat = new Stat();
            byte[] bytes= zooKeeper.getData("/zk-book",false,stat);
            System.out.println("》》》》》》"+new String(bytes));

            zooKeeper.setData("/zk-book","abc".getBytes(),stat.getVersion());

            System.out.println(new String(zooKeeper.getData("/zk-book",false,stat)));

            zooKeeper.delete("/zk-book",stat.getVersion());

            zooKeeper.close();
        }catch (Exception e){
          e.printStackTrace();
        }

    }

}


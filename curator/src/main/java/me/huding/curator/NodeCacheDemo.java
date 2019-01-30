package me.huding.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;


/**
 *
 * Node Cache与Path Cache类似，Node Cache只是监听某一个特定的节点。它涉及到下面的三个类：
 *
 * NodeCache - Node Cache实现类
 * NodeCacheListener - 节点监听器
 * ChildData - 节点数据
 * 注意：使用cache，依然要调用它的start()方法，使用完后调用close()方法。
 *
 * getCurrentData()将得到节点当前的状态，通过它的状态可以得到当前的值。
 *
 */
public class NodeCacheDemo {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.create().creatingParentsIfNeeded().forPath(PATH);
        final NodeCache cache = new NodeCache(client, PATH);
        NodeCacheListener listener = () -> {
            ChildData data = cache.getCurrentData();
            if (null != data) {
                System.out.println("节点数据：" + new String(cache.getCurrentData().getData()));
            } else {
                System.out.println("节点被删除!");
            }
        };
        cache.getListenable().addListener(listener);
        cache.start();
        client.setData().forPath(PATH, "01".getBytes());
        Thread.sleep(100);
        client.setData().forPath(PATH, "02".getBytes());
        Thread.sleep(100);
        client.delete().deletingChildrenIfNeeded().forPath(PATH);
        Thread.sleep(1000 * 2);
        cache.close();
        client.close();
        System.out.println("OK!");
    }
}
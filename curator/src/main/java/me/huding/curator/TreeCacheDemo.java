package me.huding.curator;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 *
 * Tree Cache可以监控整个树上的所有节点，类似于PathCache和NodeCache的组合，主要涉及到下面四个类：
 *
 * TreeCache - Tree Cache实现类
 * TreeCacheListener - 监听器类
 * TreeCacheEvent - 触发的事件类
 * ChildData - 节点数据
 *
 *
 * 注意：在此示例中没有使用Thread.sleep(10)，但是事件触发次数也是正常的。
 *
 * 注意：TreeCache在初始化(调用start()方法)的时候会回调TreeCacheListener实例一个事TreeCacheEvent，而回调的TreeCacheEvent对象的Type为INITIALIZED，ChildData为null，此时event.getData().getPath()很有可能导致空指针异常，这里应该主动处理并避免这种情况。
 *
 */
public class TreeCacheDemo {

    private static final String PATH = "/example/cache";

    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        CuratorFramework client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(1000, 3));
        client.start();
        client.create().creatingParentsIfNeeded().forPath(PATH);
        TreeCache cache = new TreeCache(client, PATH);
        TreeCacheListener listener = (client1, event) ->
                System.out.println("事件类型：" + event.getType() +
                        " | 路径：" + (null != event.getData() ? event.getData().getPath() : null));
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
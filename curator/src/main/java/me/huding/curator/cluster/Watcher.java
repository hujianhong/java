package me.huding.curator.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created By hujianhong
 * Date: 2018/11/27
 */
public class Watcher {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .builder().connectString(LeaderSelectorExample.connectString)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();


        client.start();

        TreeCache cache = new TreeCache(client, LeaderSelectorExample.PATH);

        TreeCacheListener listener = (client1, event) ->
                System.out.println("事件类型：" + event.getType() +
                        " | 路径：" + (null != event.getData() ? event.getData().getPath() : null));

        cache.getListenable().addListener(listener);

        cache.start();

        while (true);
    }
}

package me.huding.curator.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * Created By hujianhong
 * Date: 2018/11/27
 */
public class TreeWatcher {

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .builder().connectString(LeaderSelectorExample.connectString)
                .retryPolicy(new ExponentialBackoffRetry(1000,3)).build();

        client.start();


        TreeCache cache = new TreeCache(client,LeaderSelectorExample.MASTER_PATH);

        TreeCacheListener listener = (client1, event) -> {
            System.out.println("事件类型：" + event.getType() +
                    " | 路径：" + (null != event.getData() ? event.getData().getPath() : null));
            byte[] data = client1.getData().forPath(LeaderSelectorExample.MASTER_PATH);
            List<String> childrenPath = client1.getChildren().forPath(LeaderSelectorExample.MASTER_PATH);
            System.out.println("childendPath size is:" + childrenPath.size());
//            System.out.println(childrenPath);
            if(data != null){
                System.out.println("master is :" + new String(data));
            }
            if(event.getData() != null){
                System.out.println("event data is :" + new String(event.getData().getData()));
            }
        };


        cache.getListenable().addListener(listener);

        cache.start();

        while (true);
    }
}

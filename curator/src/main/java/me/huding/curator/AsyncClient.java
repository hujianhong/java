package me.huding.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Curator提供异步接口，引入了BackgroundCallback接口用于处理异步接口调用之后服务端返回的结果信息。BackgroundCallback接口中一个重要的回调值为CuratorEvent，里面包含事件类型、响应吗和节点的详细信息。
 * <p>
 * CuratorEventType
 * <p>
 * 事件类型	对应CuratorFramework实例的方法
 * CREATE	#create()
 * DELETE	#delete()
 * EXISTS	#checkExists()
 * GET_DATA	#getData()
 * SET_DATA	#setData()
 * CHILDREN	#getChildren()
 * SYNC	#sync(String,Object)
 * GET_ACL	#getACL()
 * SET_ACL	#setACL()
 * WATCHED	#TreeWatcher(TreeWatcher)
 * CLOSING	#close()
 * 响应码(#getResultCode())
 * <p>
 * 响应码	意义
 * 0	OK，即调用成功
 * -4	ConnectionLoss，即客户端与服务端断开连接
 * -110	NodeExists，即节点已经存在
 * -112	SessionExpired，即会话过期
 * 一个异步创建节点的例子如下：
 * <p>
 * Executor executor = Executors.newFixedThreadPool(2);
 * client.create()
 * .creatingParentsIfNeeded()
 * .withMode(CreateMode.EPHEMERAL)
 * .inBackground((curatorFramework, curatorEvent) -> {
 * System.out.println(String.format("eventType:%s,resultCode:%s",curatorEvent.getType(),curatorEvent.getResultCode()));
 * },executor)
 * .forPath("path");
 * 注意：如果#inBackground()方法不指定executor，那么会默认使用Curator的EventThread去进行异步处理。
 * *
 * Created By hujianhong
 * Date: 2018/11/8
 */
public class AsyncClient {


    public static void main(String[] args) throws Exception {
        String connectString = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(retryPolicy)
                .namespace("base")
                .build();
        //
        client.start();

        /* create path and init data */
        // 创建一个节点，附带初始化内容
        Stat stat = client.checkExists().forPath("/path1");
        if (stat == null) {
            String string = client.create().forPath("/path1", "init".getBytes());
            System.out.println(string);
        }


        Executor executor = Executors.newFixedThreadPool(2);
        stat = client.checkExists().forPath("/async_path2");
        if(stat == null){
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .inBackground((framework, event) -> {
                        System.out.println(String.format("eventType:%s,resultCode:%s",
                                event.getType(), event.getResultCode()));
                    }, executor)
                    .forPath("/async_path2", "init_async".getBytes());
        }

        client.getData()
                .inBackground((framework, event) -> {
                    byte[] bytes = event.getData();
                    System.out.println(new String(bytes));
                    System.out.println(String.format("eventType:%s,resultCode:%s",
                            event.getType(), event.getResultCode()));
                }, executor)
                .forPath("/async_path2");

        while (true){

        }
    }

}

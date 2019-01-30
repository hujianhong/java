package me.huding.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 *
 *
 * Zookeeper的节点创建模式：
 *
 * PERSISTENT：持久化
 * PERSISTENT_SEQUENTIAL：持久化并且带序列号
 * EPHEMERAL：临时
 * EPHEMERAL_SEQUENTIAL：临时并且带序列号
 *
 *
 * Created By hujianhong
 * Date: 2018/11/8
 *
 */
public class Client {


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

        /* delete path */

        // 删除一个节点,注意，此方法只能删除叶子节点，否则会抛出异常。
        if(client.checkExists().forPath("/path11") != null){
            client.delete().forPath("/path11");

            // 删除一个节点，并且递归删除其所有的子节点
            client.delete().deletingChildrenIfNeeded().forPath("/path3");
        }

        /* create path and init data */
        // 创建一个节点，附带初始化内容
        String string = client.create().forPath("/path11","init".getBytes());
        System.out.println(string);

        // 创建一个节点，指定创建模式（临时节点），附带初始化内容
        string = client.create().withMode(CreateMode.EPHEMERAL).forPath("/path2","init2".getBytes());
        System.out.println(string);

        // 创建一个节点，指定创建模式（临时节点），附带初始化内容，并且自动递归创建父节点
        string = client.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL).forPath("/path3/subPath","init3".getBytes());
        System.out.println(string);

        /* read data from path */
        // 读取一个节点的数据内容
        byte[] bytes = client.getData().forPath("/path11");
        System.out.println(new String(bytes));

        // 读取一个节点的数据内容，同时获取到该节点的stat
        Stat stat = new Stat();
        bytes = client.getData().storingStatIn(stat).forPath("/path2");
        System.out.println(new String(bytes));
        System.out.println(stat);


        /* update data for path */
        //
        stat = client.setData().forPath("/path11","init_new".getBytes());
        System.out.println(stat);

        bytes = client.getData().forPath("/path11");
        System.out.println(new String(bytes));

        // 更新一个节点的数据内容，强制指定版本进行更新
        try {
            stat = client.setData().withVersion(10086).forPath("/path2","init2_new".getBytes());
            System.out.println(stat);
        } catch (Exception e){
            e.printStackTrace();
        }

        // 检查节点是否存在;
        // 注意：该方法返回一个Stat实例，用于检查ZNode是否存在的操作. 可以调用额外的方法(监控或者后台处理)并在最后调用forPath( )指定要操作的ZNode
        stat = client.checkExists().forPath("/path11");
        System.out.println(stat);

        stat = client.checkExists().forPath("/path4");
        System.out.println(stat);

        // 获取某个节点的所有子节点路径
        List<String> subPaths = client.getChildren().forPath("/path3");
        System.out.println(subPaths);

        // 事务
        // CuratorFramework的实例包含inTransaction( )接口方法，调用此方法开启一个ZooKeeper事务.
        // 可以复合create, setData, check, and/or delete 等操作然后调用commit()作为一个原子操作提交。

//        client.inTransaction().check().forPath("path")
//                .and()
//                .create().withMode(CreateMode.EPHEMERAL).forPath("path","data".getBytes())
//                .and()
//                .setData().withVersion(10086).forPath("path","data2".getBytes())
//                .and()
//                .commit();



        // 异步接口
        // 上面提到的创建、删除、更新、读取等方法都是同步的，Curator提供异步接口，
        // 引入了BackgroundCallback接口用于处理异步接口调用之后服务端返回的结果信息。
        // BackgroundCallback接口中一个重要的回调值为CuratorEvent，里面包含事件类型、响应吗和节点的详细信息。



        /* delete path */

        // 删除一个节点,注意，此方法只能删除叶子节点，否则会抛出异常。
        client.delete().forPath("/path11");

        // 删除一个节点，并且递归删除其所有的子节点
        client.delete().deletingChildrenIfNeeded().forPath("/path3");

        // 删除一个节点，强制指定版本进行删除
        // client.delete().withVersion(10086).forPath("path");

        // 删除一个节点，强制保证删除;
        // guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功。
        client.delete().guaranteed().forPath("/path2");





    }

}

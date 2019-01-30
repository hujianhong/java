//package me.huding.curator;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.apache.curator.retry.ExponentialBackoffRetry;
//import org.apache.curator.utils.CloseableUtils;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
///**
// * 分布式锁
// * @author J
// */
//public class DistLock {
//
//    private static final String rootPath = "distLock";
//
//    private static final int DEFAULT_CONNECT_TIMEOUT = 2000;
//
//    private static final int DEFAULT_SESSION_TIMEOUT = 2000;
//
//    private static final int DEFAULT_LOCK_TIMEOUT = 120;
//
//    private String connectionString;
//
//    private int connectionTimeout;
//
//    private int sessionTimeout;
//
//    private CuratorFramework client;
//
//    private Map<String, InterProcessMutex> locks;
//
//    public DistLock() {
//        this.connectionString = PropertyUtil.get("zkCluster");
//        this.connectionTimeout =
//                StringUtils.isBlank(PropertyUtil.get("connect.timeout")) ? DEFAULT_CONNECT_TIMEOUT
//                        : Integer.valueOf(PropertyUtil.get("connect.timeout"));
//        this.sessionTimeout =
//                StringUtils.isBlank(PropertyUtil.get("request.timeout")) ? DEFAULT_SESSION_TIMEOUT
//                        : Integer.valueOf(PropertyUtil.get("request.timeout"));
//        this.client =
//                CuratorFrameworkFactory.builder().connectString(connectionString)
//                        .retryPolicy(new ExponentialBackoffRetry(1000, 3))
//                        .connectionTimeoutMs(this.connectionTimeout)
//                        .sessionTimeoutMs(this.sessionTimeout).namespace(rootPath).build();
//        this.client.start();
//        locks = new HashMap<>(32);
//    }
//
//    /**
//     *
//     * <p>Descrption: 获取zk客户端</p>
//     * @Author J
//     * @return CuratorFramework
//     * @return
//     */
//    public CuratorFramework getClient() {
//        return client;
//    }
//
//    /**
//     *
//     * <p>Descrption: 获取分布式锁</p>
//     * @Author J
//     * @return boolean
//     * @param action
//     * @param lockId
//     * @param time
//     * @return
//     * @throws Exception
//     */
//    public boolean lock(String action, String lockId, int time) throws Exception {
//        String uniqueLockId = action+"_"+lockId;
//
//        InterProcessMutex lock = new InterProcessMutex(this.client, "/"+uniqueLockId);
//        boolean isLocked = lock.acquire(time, TimeUnit.SECONDS);
//        if (isLocked) {
//            this.locks.put(uniqueLockId, lock);
//        }
//        return isLocked;
//    }
//
//    /**
//     *
//     * <p>Descrption: 获取分布式锁</p>
//     * @Author J
//     * @return boolean
//     * @param action
//     * @param lockId
//     * @return
//     * @throws Exception
//     */
//    public boolean lock(String action, String lockId) throws Exception {
//        return lock(action, lockId, DEFAULT_LOCK_TIMEOUT);
//    }
//
//    /**
//     *
//     * <p>Descrption: 批量锁</p>
//     * @Author J
//     * @return boolean
//     * @param action
//     * @param lockIds
//     * @param time
//     * @return
//     * @throws Exception
//     */
//
//    public boolean batchLock(String action, Collection<String> lockIds, int time) throws Exception {
//        boolean ret = true;
//        for (String lockId : lockIds) {
//            ret = ret && lock(action, lockId, time);
//            // 所有加锁成功才算成功
//            if (!ret) {
//                unBatchLock(action, lockIds);
//                break;
//            }
//        }
//        return ret;
//    }
//
//    public boolean batchLock(String action, Collection<String> lockIds) throws Exception {
//        return batchLock(action, lockIds, DEFAULT_LOCK_TIMEOUT);
//    }
//
//    /**
//     *
//     * <p>Descrption: 返回无法获取锁的lockId</p>
//     * @Author J
//     * @return Set<String>
//     * @param action
//     * @param lockIds
//     * @param time
//     * @return
//     * @throws Exception
//     */
//    public Set<String> batchLockRetUnlock(String action, Collection<String> lockIds, int time) throws Exception {
//        Set<String> unlockIds = new HashSet<>();
//        for (String lockId : lockIds) {
//            if (!lock(action, lockId, time)){
//                unlockIds.add(lockId);
//            }
//        }
//        return unlockIds;
//    }
//
//    public Set<String> batchLockRetUnlock(String action, Collection<String> lockIds) throws Exception {
//        return batchLockRetUnlock(action, lockIds, DEFAULT_LOCK_TIMEOUT);
//    }
//
//    /**
//     *
//     * <p>Descrption: 并发批量加锁</p>
//     * @Author J
//     * @return Set<String>
//     * @param action
//     * @param lockIds
//     * @param time
//     * @return
//     * @throws Exception
//     */
//    public Set<String> batchParalleLockRetUnlock(String action, Collection<String> lockIds, int time) throws Exception {
//        final Set<String> unlockIds = new HashSet<>();
////        lockIds.parallelStream().limit(100).forEach(lockId -> {
//    	for(String lockId : lockIds){
//            try {
//                if (!lock(action, lockId, time)) {
//                    unlockIds.add(lockId);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//        return unlockIds;
//    }
//
//    public Set<String> batchParalleLockRetUnlock(String action, Collection<String> lockIds) throws Exception {
//        return batchParalleLockRetUnlock(action, lockIds, DEFAULT_LOCK_TIMEOUT);
//    }
//
//    /**
//     *
//     * <p>Descrption: 释放锁</p>
//     * @Author J
//     * @return void
//     * @param action
//     * @param lockId
//     * @throws Exception
//     */
//    public void unlock(String action, String lockId) throws Exception {
//        String uniqueLockId = action+"_"+lockId;
//        InterProcessMutex lock = null;
//        if ((lock = this.locks.get(uniqueLockId)) != null) {
//            this.locks.remove(uniqueLockId);
//            lock.release();
//        }
//    }
//
//    /**
//     *
//     * <p>Descrption: 批量释放锁</p>
//     * @Author J
//     * @return void
//     * @param action
//     * @param lockId
//     * @throws Exception
//     */
//    public void unBatchLock(String action, Collection<String> lockIds) {
//        for (String lockId : lockIds) {
//            try {
//                unlock(action, lockId);
//            } catch (Exception e) {
//                continue;
//            }
//        }
//    }
//
//    /**
//     *
//     * <p>Descrption: 并发批量解锁</p>
//     * @Author J
//     * @return void
//     * @param action
//     * @param lockIds
//     */
//    public void unBatchParalleLock(String action, Collection<String> lockIds) {
////        lockIds.parallelStream().limit(100).forEach(lockId -> {
//    	for(String lockId : lockIds){
//            try {
//                unlock(action, lockId);
//            } catch (Exception e) {
//                // DO NOTHING
//            }
//        };
//    }
//
//    /**
//     *
//     * <p>Descrption: 关闭客户端</p>
//     * @Author J
//     * @return void
//     */
//    public void close() {
//        if (this.client != null) {
//            CloseableUtils.closeQuietly(this.client);
//        }
//    }
//}

## 什么是Atomix？

Atomix是一种以各种不同方式解决常见分布式系统问题的工具。对于它所解决的问题，它没有任何意义，而是提供解决问题的原语。它提供的原语的一些例子是：

* 分布式数据结构（地图，集合，树，计数器，值等）
* 分布式通信（直接，发布 - 订阅等）
* 分布式协调（锁定，领导者选举，信号量，障碍等）
* 集团成员
```java
AtomicMap<String, String> map = atomix.<String, String>mapBuilder("my-map")
  .withCacheEnabled()
  .build();
```

可以使用具有不同保证的各种可配置分布式系统协议来复制这些原语中的每一个：

Multi-Raft - 一种强一致的分区共识算法
Multi-Primary - 基于分区的基于领导的同步/异步复制算法
反熵 - 一种高度可扩展的最终一致的八卦/协调协议
CRDT - 最终强烈一致的八卦式复制协议
```java
MultiRaftProtocol protocol = MultiRaftProtocol.builder()
  .withReadConsistency(ReadConsistency.LINEARIZABLE)
  .build()

Map<String, String> map = atomix.<String, String>mapBuilder("my-map")
  .withProtocol(protocol)
  .withCacheEnabled()
  .build();

map.put("foo", "bar");
```

基元是线程安全的，异步的和被动的，严重依赖事件通知来检测系统中的状态变化：
```java
LeaderElection<MemberId> election = atomix.getElection("my-election");

election.addListener(event -> {
  Leader leader = event.newLeadership().leader();
  ...
});
election.run(atomix.getMembershipService().getLocalMember().id());
```



并且可以通过多种不同方式访问，包括：

* 异步API
* 同步API
* REST API
```
AsyncAtomicMap<String, String> asyncMap = atomix.getMap("my-map").async();

asyncMap.put("foo", "baz").thenRun(() -> {
  ...
});

```

同样，它们可以在代码或配置文件中配置：

* Java构建者
* HOCON配置
* JSON配置

```java
primitives.my-map {
  type: map
  protocol {
    type: multi-raft
    group: raft
    readConsistency: linearizable
  }
}
```

这种灵活性允许架构师构建极其多样化的系统。

ONOS用例
该[ONOS项目](https://onosproject.org/)Atomix主要开发的是使用上面概述的Atomix功能的一个很好的用例。ONOS的核心是使用Atomix组成员资格和消息传递进行集群管理和通信。此外，ONOS中的存储严重依赖于Atomix原语进行状态复制和协调。使用Atomix，ONOS工程师可以为每个商店的用例选择理想的原语。例如，一些商店可以使用同步原语以简化，而其他商店可以使用异步（非阻塞）原语来实现并发。一些商店使用Atomix原语来构建更高级别的自定义复制协议。Atomix的不受欢迎的性质允许工程师使用最好的工具，


原文：
* [what-is-atomix](https://atomix.io/docs/latest/user-manual/introduction/what-is-atomix/)
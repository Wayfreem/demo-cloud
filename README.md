## Spring Cloud Alibaba 学习

#### nacos

#### linux 中安装 nacos

- 下载官方的包，并且解压
- 进入 nacos 的 bin 路径下，执行 nacos 的启动命令：`sh startup.sh -m standalone`
- 需要添加开放端口 8848，以及重新加载防火墙
- 访问地址为：`localhost:8848/nacos`， 用户密码为：nacos/nacos

另外在启动完成之后，可以进入 nacos 路径下的 logs 目录，查看启日志 `tail start.out`

#### 程序代码修改

在 demo-video、demo-order 的 pom 文件中添加依赖

```xml
<!--添加nacos客户端-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

#### 配置 Nacos 的地址

```yml
server:
  port:9000

spring:
  application:
    name: demo-video
  cloud:
    nacos:
      discovery:
        server-addr:127.0.0.1:8848
```

启动类增加注解  `@EnableDiscoveryClient`

### 集成 sentinel

#### sentinel 控制台

sentinel 有一个单独的控制台，可以去官网上面去下载，然后通过命令行启动 [链接地址](https://sentinelguard.io/zh-cn/docs/dashboard.html)

**注意：如果是通过下面的方式启动，需要开放防火墙端口**

默认的账号、密码都是 sentinel
```shell
java -Dserver.port=8858 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.0.jar

nohup java -Dserver.port=8858 -Dcsp.sentinel.dashboard.server=localhost:8858 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.0.jar &
```

#### 程序代码修改

**在 demo-video、demo-order 的 pom 文件中添加依赖**

```xml
<!--添加sentinel客户端-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
```

**增加对应的配置文件内容**

#dashboard: 8080 控制台端口
#port: 9999 本地启的端口，随机选个不能被占用的，与dashboard进行数据交互，会在应用对应的机器上启动一个 Http Server，该 Server 会与 Sentinel 控制台做交互, 若被占用,则开始+1一次扫描

```yml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: 127.0.0.1:8858 
        port: 9999
```
微服务注册上去后，由于Sentinel是懒加载模式，所以需要访问微服务后才会在控制台出现



# 牛客论坛

#### 介绍
牛客论坛



#### 软件版本

1.  kafka_2.12-2.3.0
2.  elasticsearch-6.4.3
3.  Java 8

#### 使用说明
安装配置：
1.  kafka安装目录下的 config/zookeeper.properties 将dataDir设置为自定义的数据存放地址
     同理 对config/zookeeper.properties中的log.dirs也进行相应设置
2.  consumer.properties中 设置 group.id=community-consumer-group
3.  elasticsearch安装目录下 config/elasticsearch.yml 设置  cluster.name: nowcoder

启动项目前：
1.  本地启动redis
2.  进入kafka安装目录 用命令行启动zookeeper：bin\windows\zookeeper-server-start.bat config\zookeeper.properties
3.  不要关闭以上命令窗口 新开一个命令窗口启动kafka：bin\windows\kafka-server-start.bat config\server.properties
4.  进入elasticsearch目录下的bin文件夹 双击elasticsearch.bat 启动服务

关闭项目前 一定不能直接关闭kafka的命令窗口！！而是通过输入指令来关闭服务：
1.  先关闭kafka：bin\windows\kafka-server-stop.bat
2.  再关闭zookeeper：bin\windows\zookeeper-server-stop.bat


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request





# 牛客论坛

#### 介绍
牛客论坛



#### 软件版本

1.  kafka_2.12-2.3.0
2.  elasticsearch-6.4.3
3.  Java 8

#### 使用说明
安装配置：

1. 执行sql文件夹下的三个sql文件 并在配置文件对数据库进行相应配置
2. kafka安装目录下的 config/zookeeper.properties 将dataDir设置为自定义的数据存放地址
     同理 对config/zookeeper.properties中的log.dirs也进行相应设置
3. consumer.properties中 设置 group.id=community-consumer-group
4. elasticsearch安装目录下 config/elasticsearch.yml 设置  cluster.name: nowcoder
5. 安装qq群里的RKCSSVA.exe(wkhtmltopdf) 到目录d:/work/wkhtmltopdf下 也可以自己修改application.properties
6. 因为utf-8不兼容富文本编辑器的emoji表情 故需要将discuss__post表中对应的content列进行字符集的重新配置 
    详见这两篇博客 https://blog.csdn.net/qq_43461877/article/details/103101859    
                             https://www.cnblogs.com/liupeifeng3514/p/10214392.html

启动项目前：
1. 本地启动redis
2. 进入kafka安装目录 用命令行启动zookeeper：bin\windows\zookeeper-server-start.bat config\zookeeper.properties
3. 不要关闭以上命令窗口 新开一个命令窗口 进入kafka安装目录启动kafka：bin\windows\kafka-server-start.bat config\server.properties
4. 进入elasticsearch目录下的bin文件夹 双击elasticsearch.bat 启动服务

关闭项目前：
 一定不能直接关闭kafka的命令窗口！！而是通过输入指令来关闭服务！
1.  先关闭kafka：bin\windows\kafka-server-stop.bat
2.  再关闭zookeeper：bin\windows\zookeeper-server-stop.bat


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request





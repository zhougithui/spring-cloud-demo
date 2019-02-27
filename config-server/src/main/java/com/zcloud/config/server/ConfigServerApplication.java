package com.zcloud.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 使用Config Server，您可以在所有环境中管理应用程序的外部属性。
 * 客户端和服务器上的概念映射与Spring Environment和PropertySource抽象相同，
 * 因此它们与Spring应用程序非常契合，但可以与任何以任何语言运行的应用程序一起使用。
 * 随着应用程序通过从开发人员到测试和生产的部署流程，您可以管理这些环境之间的配置，
 * 并确定应用程序具有迁移时需要运行的一切。服务器存储后端的默认实现使用git，
 * 因此它轻松支持标签版本的配置环境，以及可以访问用于管理内容的各种工具。
 * 很容易添加替代实现，并使用Spring配置将其插入
 *
 * 说明：充分利用git的版本管理优势
 *
 * 配置信息使用场景
 * 1）.  多个客户使用同一配置： 比如，多台服务器组成的集群，假如后端使用同一数据库，那么每台服务器都是用相同的配置。
 * 2）.  不同客户使用不同的配置： 比如典型的场景是，开发，测试，生产使用相同的系统，但使用不同的数据库
 *
 * 使用git作为配置管理，网络问题是否能够容易解决？
 *
 *
 * config server也可以作为节点注册到注册中心，实现高可用
 * servier需要配置git仓库的地址和查找路径
 *
 *
 */
@EnableEurekaServer
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

    /**
     * 启动后，访问http://localhost:8888/abc/xyz成功
     * abc 就是application的名字，xyz是profile的名字， 注意这里的abc, xyz均是随便输入的名字，
     * 并不需要真实存在，config server这个REST接口返回的只是应用名为abc, profile名为xyz时，GIT配置环境的结构。
     *
     * 如果profile环境为dev，则
     * 访问http://localhost:8888/config-client-dev.properties就可以显示配置文件内容
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
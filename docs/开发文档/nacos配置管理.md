## 1.1.统一配置管理

当微服务部署的实例越来越多，达到数十、数百时，逐个修改微服务配置就会让人抓狂，而且很容易出错。我们需要一种统一配置管理方案，可以集中管理所有实例的配置。

![image-20210714164426792](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\image-20210714164426792.png)



Nacos一方面可以将配置集中管理，另一方可以在配置变更时，及时通知微服务，实现配置的热更新。



### 1.1.1.在nacos中添加配置文件

![image-20210714164742924](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\image-20210714164742924.png)

然后在弹出的表单中，填写配置信息：

![image-20210714164856664](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\image-20210714164856664.png)



> 注意：项目的核心配置，需要热更新的配置才有放到nacos管理的必要。基本不会变更的一些配置还是保存在微服务本地比较好。



### 1.1.2.从微服务拉取配置

微服务要拉取nacos中管理的配置，并且与本地的application.yml配置合并，才能完成项目启动。

但如果尚未读取application.yml，又如何得知nacos地址呢？

因此spring引入了一种新的配置文件：bootstrap.yaml文件，会在application.yml之前被读取，流程如下：

![img](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\L0iFYNF.png)



1）引入nacos-config依赖

首先，在相关服务中，引入nacos-config的客户端依赖：

```xml
<!--nacos配置管理依赖-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

2）添加bootstrap.yaml

然后，在相关服务中添加一个bootstrap.yaml文件，内容如下：

```yaml
spring:
  application:
    name: userservice # 服务名称
  profiles:
    active: dev #开发环境，这里是dev 
  cloud:
    nacos:
      server-addr: localhost:8848 # Nacos地址
      config:
        file-extension: yaml # 文件后缀名
```

这里会根据spring.cloud.nacos.server-addr获取nacos地址，再根据

`${spring.application.name}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}`作为文件id，来读取配置。

本例中，就是去读取`userservice-dev.yaml`：

![image-20210714170845901](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\image-20210714170845901.png)



3）读取nacos配置

在相关服务的UserController中添加业务逻辑，读取pattern.dateformat配置：

![image-20220409120552242](C:\Users\15900\AppData\Roaming\Typora\typora-user-images\image-20220409120552242.png)





在页面访问，可以看到效果：

![image-20220409120614692](C:\Users\15900\AppData\Roaming\Typora\typora-user-images\image-20220409120614692.png)





## 1.2.配置热更新

我们最终的目的，是修改nacos中的配置后，微服务中无需重启即可让配置生效，也就是**配置热更新**。



要实现配置热更新，可以使用两种方式：

推荐使用方式一

### 1.2.1.方式一

在@Value注入的变量所在类上添加注解@RefreshScope：



![image-20220409120649849](C:\Users\15900\AppData\Roaming\Typora\typora-user-images\image-20220409120649849.png)

### 1.2.2.方式二

使用@ConfigurationProperties注解代替@Value注解。

在user-service服务中，添加一个类，读取patterrn.dateformat属性：

```java
package cn.itcast.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "pattern")
public class PatternProperties {
    private String dateformat;
}
```



在UserController中使用这个类代替@Value：

![image-20210714171316124](E:\BaiduNetdiskDownload\实用篇\学习资料\day02-SpringCloud02\讲义\assets\image-20210714171316124.png)





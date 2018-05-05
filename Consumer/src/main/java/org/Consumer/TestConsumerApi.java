package org.Consumer;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;
import com.test.UserServiceBo;

/**
 * Hello world!
 * 
 */
public class TestConsumerApi {
	public static void main(String[] args) throws InterruptedException {
        //(1) 配置应用信息
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubboConsumer");
		
		//（2）配置服务注册中心地址
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");

        //（3）配置从服务注册中心查找监控中心地址
		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setProtocol("registry");
		
		//(4)创建服务消费者配置类
		ReferenceConfig<UserServiceBo> reference = new ReferenceConfig<UserServiceBo>(); // 
		reference.setApplication(application);
		reference.setRegistry(registry); 
		reference.setMonitor(monitorConfig);
		
		//(4.1)interface+version+group 
		reference.setInterface(UserServiceBo.class);
		reference.setVersion("1.0.0");
		reference.setGroup("dubbo1");
		//(4.2)设置客户端超时时间
		reference.setTimeout(3000);
		
        //(4.4)获取远程服务代理类
		UserServiceBo userService = reference.get(); 
		//(4.5)具体调用远程方法执行
		System.out.println(userService.sayHello("哈哈哈"));
		
	}
}

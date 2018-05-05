package com.test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.MonitorConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

public class TestProviderApi {
 
	public static void main(String[] arg) throws InterruptedException {
		//(1) 创建要提供的服务的实例
		UserServiceBo userService = new UserServiceImpl();
        //(2) 配置应用信息
		ApplicationConfig application = new ApplicationConfig();
		application.setName("dubboProvider");
		
		//（3）配置服务注册中心地址
		RegistryConfig registry = new RegistryConfig();
		registry.setAddress("127.0.0.1:2181");
		registry.setProtocol("zookeeper");

		//(4)使用的传输协议
		ProtocolConfig protocol = new ProtocolConfig();
		protocol.setName("dubbo");
		protocol.setPort(20880);

        //（5）配置从服务注册中心查找监控中心地址
		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setProtocol("registry");

		//(6)创建服务提供者配置类
		ServiceConfig<UserServiceBo> service = new ServiceConfig<UserServiceBo>();
		service.setApplication(application);
		service.setMonitor(monitorConfig);
		service.setRegistry(registry); 
		service.setProtocol(protocol); 
		
		//(6.1)interface + version+group
		service.setInterface(UserServiceBo.class);
		service.setRef(userService);
		service.setVersion("1.0.0");
		service.setGroup("dubbo");
		//(6.2)客户端超时时间
		service.setTimeout(3000);
		//(6.3)导出服务
		service.export();

	    //挂起当前线程
		Thread.currentThread().join();
	}
}

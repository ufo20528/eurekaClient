package com.weizhe.eurekaClient.springcloudproxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 
 * 调用其他服务
 */
@FeignClient(name="springboot") //注册到eureka里的服务名称
public interface IUserProxy {

	
	@GetMapping("/user/user") //要调用服务的地址
	String getUser();
	
}

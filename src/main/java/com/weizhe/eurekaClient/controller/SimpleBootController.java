package com.weizhe.eurekaClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.weizhe.eurekaClient.springcloudproxy.IUserProxy;

@RestController
@RequestMapping("/boot")
public class SimpleBootController {

	// 第1种调用方法需要注入
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	// 第2种调用方法需要注入
	@Autowired
	private IUserProxy userProxy;

	@RequestMapping("/test")
	public String hello() {
		return "hello2";
	}

	/**
	 * 第一中调用其他服务的方法
	 * 
	 * @return
	 */
	@RequestMapping("/testtemplete")
	public String testRestTemplete() {

		ServiceInstance instance = loadBalancerClient.choose("SPRINGBOOT");
		String url = String.format("http://%s:%s", instance.getHost(), instance.getPort() + "/user/user");

		RestTemplate restTemplete = new RestTemplate();
		// String result = restTemplete.getForObject("http://localhost:8080/user/user",
		// String.class);
		String result = restTemplete.getForObject(url, String.class);
		System.out.println(result);

		return result;
	}

	/**
	 * 调用其他服务的第2种方法，需要通过feign实现
	 */
	@RequestMapping("/testfeign")
	public String testFeign() {
		String result = userProxy.getUser();
		System.out.println(result);
		return result;
	}

}

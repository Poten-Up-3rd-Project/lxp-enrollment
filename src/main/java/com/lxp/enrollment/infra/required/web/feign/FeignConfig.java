package com.lxp.enrollment.infra.required.web.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.lxp.enrollment.infra.adapter.required.web.feign")
public class FeignConfig {
}

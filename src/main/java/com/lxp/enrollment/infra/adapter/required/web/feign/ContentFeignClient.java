package com.lxp.enrollment.infra.adapter.required.web.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "content-service", url = "${services.content-service.url}")
public interface ContentFeignClient {

    @GetMapping("/courses/{courseId}/exists")
    ResponseEntity<Boolean> courseExists(@PathVariable String courseId);
}

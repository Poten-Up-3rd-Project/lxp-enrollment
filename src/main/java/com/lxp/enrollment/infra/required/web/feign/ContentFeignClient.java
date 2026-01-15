package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.infra.required.web.feign.config.FeignErrorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "content-service",
        url = "${services.content-service.url}/internal/api-v1",
        configuration = FeignErrorConfig.class
)
public interface ContentFeignClient {

    @GetMapping("/courses/{courseId}/exists")
    ResponseEntity<Boolean> courseExists(@PathVariable String courseId);
}

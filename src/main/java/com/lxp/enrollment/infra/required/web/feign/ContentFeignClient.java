package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.infra.required.web.feign.config.FeignErrorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@FeignClient(
        name = "content-service",
        url = "${services.content-service.url}/internal/api-v1",
        configuration = FeignErrorConfig.class
)
public interface ContentFeignClient {

    @GetMapping("/courses/{courseId}/exists")
    ResponseEntity<Boolean> courseExists(@PathVariable String courseId);

    // To Do: 아직 해당 내부 api 확정 안 됨, 왔다고 치고 구현
    @GetMapping("/courses/{courseId}")
    ResponseEntity<CourseSummary> getCourseSummary(@PathVariable String courseId);

    // To Do: 아직 해당 내부 api 확정 안 됨, 왔다고 치고 구현
    @GetMapping("/courses")
    ResponseEntity<List<CourseSummary>> getCourseSummaries(@PathVariable Set<String> courseIds);
}

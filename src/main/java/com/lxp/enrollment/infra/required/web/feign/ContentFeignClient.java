package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.application.required.web.dto.CourseFilterInternalRequest;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.infra.required.web.feign.config.FeignErrorConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "content-service",
        url = "${services.content-service.url}/internal/api-v1",
        configuration = FeignErrorConfig.class
)
public interface ContentFeignClient {

    @GetMapping("/courses/{courseId}/exists")
    ResponseEntity<Boolean> courseExists(@PathVariable String courseId);

    @GetMapping("/courses/filter")
    public ResponseEntity<List<CourseSummary>> getCourseSummary(@RequestBody CourseFilterInternalRequest request);
}

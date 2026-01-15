package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.infra.required.web.utils.ApiPostman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ContentClientFeignAdapter implements ContentClient {

    private final ContentFeignClient contentFeignClient;

    public ContentClientFeignAdapter(ContentFeignClient contentFeignClient) {
        this.contentFeignClient = contentFeignClient;
    }

    @Override
    public Boolean courseExists(UUID courseId) {
        return ApiPostman.demand(
                () -> contentFeignClient.courseExists(courseId.toString())
        );
    }

    @Override
    public Boolean courseNotExists(UUID courseId) {
        return !this.courseExists(courseId);
    }

    @Override
    public CourseSummary getCourseSummary(UUID courseId) {
        CourseSummary courseSummary = ApiPostman.demand(
                () -> contentFeignClient.getCourseSummary(courseId.toString())
        );

        // To Do: 필수 값 null 여부 검증해야 함
        // courseSummary.required == null
        //      --then-> throw new ApiException(ApiErrorCode.REQUIRED_FIELD_IS_NOT_PROVIDED)

        return courseSummary;
    }
}

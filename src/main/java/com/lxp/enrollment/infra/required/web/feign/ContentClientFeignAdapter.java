package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.application.required.web.dto.CourseFilterInternalRequest;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.infra.required.web.utils.ApiPostman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
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

        CourseFilterInternalRequest request = new CourseFilterInternalRequest(
                List.of(courseId.toString()),
                null,
                1
        );

        List<CourseSummary> courseSummary = ApiPostman.demand(
                () -> contentFeignClient.getCourseSummary(request)
        );

        // To Do: 응답 검증해야 함 (ex. 필수 값 null 여부)
        // courseSummary.required == null
        //      --then-> throw new ApiException(ApiErrorCode.REQUIRED_FIELD_IS_NOT_PROVIDED)

        return courseSummary.get(0);
    }

    @Override
    public List<CourseSummary> getCourseSummaries(Set<UUID> courseIds) {

        CourseFilterInternalRequest request = new CourseFilterInternalRequest(
                courseIds.stream().map(UUID::toString).toList(),
                null,
                courseIds.size()
        );

        List<CourseSummary> courseSummary = ApiPostman.demand(() -> contentFeignClient.getCourseSummary(request));

        // To Do: 응답 검증해야 함 (ex. 필수 값 null 여부)
        // courseSummary.required == null
        //      --then-> throw new ApiException(ApiErrorCode.REQUIRED_FIELD_IS_NOT_PROVIDED)

        return courseSummary;
    }
}

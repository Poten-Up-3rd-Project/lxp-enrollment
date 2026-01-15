package com.lxp.enrollment.infra.required.web.feign;

import com.lxp.enrollment.application.required.web.ContentClient;
import com.lxp.enrollment.application.required.web.dto.CourseSummary;
import com.lxp.enrollment.infra.required.web.utils.ApiPostman;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

        // To Do: content 서비스에서 해당 api 개발 후 연동
        CourseSummary courseSummary = ApiPostman.demand(
                () -> contentFeignClient.getCourseSummary(courseId.toString())
        );

        // To Do: 필수 값 null 여부 검증해야 함
        // courseSummary.required == null
        //      --then-> throw new ApiException(ApiErrorCode.REQUIRED_FIELD_IS_NOT_PROVIDED)

        return courseSummary;
    }

    @Override
    public List<CourseSummary> getCourseSummaries(Set<UUID> courseIds) {
        List<CourseSummary> courseSummaries = ApiPostman.demand(
                () -> contentFeignClient.getCourseSummaries(
                        courseIds.stream()
                                .map(UUID::toString)
                                .collect(Collectors.toSet())
                )
        );

        // To Do: 필수 값 null 여부 검증해야 함?

        return courseSummaries;
    }
}

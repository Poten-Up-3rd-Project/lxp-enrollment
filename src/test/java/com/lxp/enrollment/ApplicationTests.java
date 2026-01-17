package com.lxp.enrollment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxp.common.domain.pagination.Page;
import com.lxp.common.domain.pagination.Sort;
import com.lxp.enrollment.application.provided.command.dto.CancelByUserCommand;
import com.lxp.enrollment.application.provided.command.dto.EnrollCommand;
import com.lxp.enrollment.application.provided.command.dto.view.CancelByUserSuccessView;
import com.lxp.enrollment.application.provided.command.dto.view.EnrollSuccessView;
import com.lxp.enrollment.application.provided.command.usecase.CancelByUserUseCase;
import com.lxp.enrollment.application.provided.command.usecase.EnrollUseCase;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentDetailsQuery;
import com.lxp.enrollment.application.provided.query.dto.EnrollmentSummariesQuery;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentDetailsQueryView;
import com.lxp.enrollment.application.provided.query.dto.view.EnrollmentSummaryQueryView;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentDetailsQueryUseCase;
import com.lxp.enrollment.application.provided.query.usecase.EnrollmentSummariesQueryUseCase;
import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ApplicationTests {

    private final EnrollUseCase enrollUseCase;
    private final CancelByUserUseCase cancelByUserUseCase;
    private final EnrollmentDetailsQueryUseCase enrollmentDetailsQueryUseCase;
    private final EnrollmentSummariesQueryUseCase enrollmentSummariesQueryUseCase;
    private final ObjectMapper objectMapper;

    @Autowired
    public ApplicationTests(
            EnrollUseCase enrollUseCase,
            CancelByUserUseCase cancelByUserUseCase,
            EnrollmentDetailsQueryUseCase enrollmentDetailsQueryUseCase,
            EnrollmentSummariesQueryUseCase enrollmentSummariesQueryUseCase,
            ObjectMapper objectMapper
    ) {
        this.enrollUseCase = enrollUseCase;
        this.cancelByUserUseCase = cancelByUserUseCase;
        this.enrollmentDetailsQueryUseCase = enrollmentDetailsQueryUseCase;
        this.enrollmentSummariesQueryUseCase = enrollmentSummariesQueryUseCase;
        this.objectMapper = objectMapper;
    }

    @Test
    void contextLoads() {
    }

    private final UUID USER_UUID = UUID.fromString("93ac8fef-76ac-4275-ba50-b84f3b705097");
    private final UUID COURSE_UUID = UUID.fromString("019bc9fe-44bd-7db0-a6bb-70ab67f41a67");

    @Test
    void testEnroll() throws JsonProcessingException {


        EnrollCommand enrollCommand = new EnrollCommand(USER_UUID, COURSE_UUID);
        EnrollSuccessView view = enrollUseCase.execute(enrollCommand);

        System.out.println("수강 등록 결과");
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(view));
    }

    @Test
    void testCancel() throws JsonProcessingException {


        CancelReasonType reasonType = CancelReasonType.I_DONT_LIKE_IT;
        String reason = "강사 머리가 너무 눈부셔요";
        CancelByUserCommand cancelCommand = new CancelByUserCommand(
                USER_UUID,
                COURSE_UUID,
                reasonType,
                reason
        );
        CancelByUserSuccessView view = cancelByUserUseCase.execute(cancelCommand);

        System.out.println("수강 취소 결과");
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(view));
    }

    @Test
    void testEnrollmentDetailsQuery() throws JsonProcessingException {


        EnrollmentDetailsQuery query = new EnrollmentDetailsQuery(USER_UUID, COURSE_UUID);
        EnrollmentDetailsQueryView view = enrollmentDetailsQueryUseCase.execute(query);

        System.out.println("수강 상세 조회 결과");
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(view));
    }

    @Test
    void testMyEnrollmentsQuery() throws JsonProcessingException {

        EnrollmentSummariesQuery query = new EnrollmentSummariesQuery(
                USER_UUID,
                com.lxp.common.domain.pagination.PageRequest.of(
                        0, 20, Sort.by(Sort.Direction.DESC, "enrolledAt")
                )
        );
        Page<EnrollmentSummaryQueryView> view = enrollmentSummariesQueryUseCase.execute(query);

        System.out.println("내 수강 목록 조회 결과");
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(view));
    }
}

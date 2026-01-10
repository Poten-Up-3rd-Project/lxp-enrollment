package com.lxp.enrollment.infra.adapter.required.persistence.jpa.model;

import com.lxp.enrollment.domain.model.enums.CancelReasonType;
import com.lxp.enrollment.domain.model.enums.CancelType;
import com.lxp.enrollment.domain.model.vo.CancelDetails;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CancelDetailsJpaEmbeddable {

    private Instant canceledAt;
    @Enumerated(EnumType.STRING)
    private CancelType cancelType;
    @Enumerated(EnumType.STRING)
    private CancelReasonType cancelReasonType;
    private String cancelReasonComment;

    private CancelDetailsJpaEmbeddable(
            Instant canceledAt,
            CancelType cancelType,
            CancelReasonType cancelReasonType,
            String cancelReasonComment
    ) {
        this.canceledAt = canceledAt;
        this.cancelType = cancelType;
        this.cancelReasonType = cancelReasonType;
        this.cancelReasonComment = cancelReasonComment;
    }

    public static CancelDetailsJpaEmbeddable of(CancelDetails cancelDetails) {
        return new CancelDetailsJpaEmbeddable(
                cancelDetails.cancelledAt(),
                cancelDetails.cancelType(),
                cancelDetails.cancelReasonType(),
                cancelDetails.cancelReasonComment()
        );
    }
}

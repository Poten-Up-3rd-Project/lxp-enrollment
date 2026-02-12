package com.lxp.enrollment.application.event.mapper;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.event.integration.EnrollmentCreatedIntegrationEvent;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EventToIntegrationMapperTest {

    @Test
    void created_event_produces_deterministic_but_distinct_ids() {
        EventToIntegrationMapper mapper = new EventToIntegrationMapper();

        String aggregateId = "enr-123";
        LocalDateTime t1 = LocalDateTime.of(2026, 2, 11, 12, 0, 0);
        LocalDateTime t2 = t1.plusMinutes(1);

        EnrollmentCreated domain1 = new EnrollmentCreated("evt-agg", t1, aggregateId, "course-1", "user-1");
        EnrollmentCreated domain1Copy = new EnrollmentCreated("evt-agg", t1, aggregateId, "course-1", "user-1");
        EnrollmentCreated domain2 = new EnrollmentCreated("evt-agg-2", t2, aggregateId, "course-1", "user-1");

        IntegrationEvent i1 = mapper.convert(domain1);
        IntegrationEvent i1again = mapper.convert(domain1Copy);
        IntegrationEvent i2 = mapper.convert(domain2);

        // same domain occurrence -> same integration eventId (idempotent across retries)
        assertThat(i1.getEventId()).isEqualTo(i1again.getEventId());

        // different occurrence (different occurredAt) -> different integration eventId
        assertThat(i1.getEventId()).isNotEqualTo(i2.getEventId());

        // correlationId should be aggregateId
        EnrollmentCreatedIntegrationEvent typed = (EnrollmentCreatedIntegrationEvent) i1;
        assertThat(typed.getCorrelationId()).isEqualTo(aggregateId);
    }
}
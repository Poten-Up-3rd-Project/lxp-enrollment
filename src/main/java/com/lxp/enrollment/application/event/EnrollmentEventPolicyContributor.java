package com.lxp.enrollment.application.event;

import com.lxp.common.application.event.policy.EventPolicyContributor;
import com.lxp.common.application.event.policy.EventPolicyRegistry;
import com.lxp.common.application.event.policy.EventPublishPolicy;
import com.lxp.common.application.event.policy.delivery.AtLeastOnce;
import com.lxp.common.application.event.policy.failure.RetryThenDlq;
import com.lxp.common.application.event.policy.ordering.Parallel;
import com.lxp.common.application.event.policy.priority.NormalPriority;
import com.lxp.enrollment.domain.event.EnrollmentCancelled;
import com.lxp.enrollment.domain.event.EnrollmentCreated;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class EnrollmentEventPolicyContributor implements EventPolicyContributor {
    @Override
    public void contribute(EventPolicyRegistry.Builder builder) {
        builder
            .register(EnrollmentCreated.class, new EventPublishPolicy(
                new AtLeastOnce(),
                new NormalPriority(),
                new Parallel(),
                new RetryThenDlq(3, Duration.ofSeconds(5))
            ))
            .register(EnrollmentCancelled.class, new EventPublishPolicy(
                new AtLeastOnce(),
                new NormalPriority(),
                new Parallel(),
                new RetryThenDlq(3, Duration.ofSeconds(5))
            ))
        ;
    }
}

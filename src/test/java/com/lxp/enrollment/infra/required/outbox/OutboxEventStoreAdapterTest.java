package com.lxp.enrollment.infra.required.outbox;

import com.lxp.common.application.event.IntegrationEvent;
import com.lxp.enrollment.application.event.integration.EventMetadata;
import com.lxp.enrollment.application.required.EventSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutboxEventStoreAdapterTest {

    EnrollmentOutboxRepository repository;
    EventSerializer serializer;
    OutboxEventStoreAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(EnrollmentOutboxRepository.class);
        serializer = mock(EventSerializer.class);
        adapter = new OutboxEventStoreAdapter(repository, serializer);
    }

    @Test
    void save_serializes_and_persists_outbox_event() {
        IntegrationEvent event = mock(IntegrationEvent.class);
        when(event.getEventId()).thenReturn("evt-1");
        when(event.getEventType()).thenReturn("enrollment.created");
        when(serializer.serialize(event)).thenReturn("{json}");

        EventMetadata metadata = new EventMetadata("agg-1", "EnrollmentCreated", "enrollment.exchange");

        adapter.save(event, metadata);

        verify(serializer).serialize(event);
        verify(repository).save(any());
    }
}

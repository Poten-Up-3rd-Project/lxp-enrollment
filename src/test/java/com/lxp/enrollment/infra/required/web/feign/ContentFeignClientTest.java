package com.lxp.enrollment.infra.required.web.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@SpringBootTest
class ContentFeignClientTest {

    private final ContentFeignClient contentFeignClient;

    @Autowired
    public ContentFeignClientTest(ContentFeignClient contentFeignClient) {
        this.contentFeignClient = contentFeignClient;
    }

    @Test
    public void test_courseExists() {
        ResponseEntity<Boolean> response = contentFeignClient.courseExists(UUID.randomUUID().toString());
    }
}
package com.lxp.enrollment.infra.adapter.required.web.feign;

import com.lxp.enrollment.application.port.required.web.ContentClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ContentClientFeignAdapter implements ContentClient {

    private final ContentFeignClient contentFeignClient;

    public ContentClientFeignAdapter(ContentFeignClient contentFeignClient) {
        this.contentFeignClient = contentFeignClient;
    }

    @Override
    public boolean courseNotExists(UUID courseId) {
        ResponseEntity<Boolean> response = contentFeignClient.courseExists(courseId.toString());
        if (response.getBody() == null) {
            return false;
        }

        return response.getBody();

    }
}

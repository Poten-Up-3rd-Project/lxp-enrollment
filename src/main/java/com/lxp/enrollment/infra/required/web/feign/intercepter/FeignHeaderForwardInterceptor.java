package com.lxp.enrollment.infra.required.web.feign.intercepter;

import com.lxp.passport.core.support.PassportHeaderProvider;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeignHeaderForwardInterceptor implements RequestInterceptor {

    private final PassportHeaderProvider provider;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        provider.headers().forEach(requestTemplate::header);
    }
}

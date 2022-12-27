package co.hwan.order.common.intercept;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class HttpRequestIntercept implements HandlerInterceptor {
    private static final String REQUEST_ID_PREFIX = "x-request-id";

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String requestEventId = request.getHeader(REQUEST_ID_PREFIX);

        if (StringUtils.isBlank(requestEventId))
            requestEventId = UUID.randomUUID().toString();

        MDC.put(REQUEST_ID_PREFIX, requestEventId);
        response.addHeader(REQUEST_ID_PREFIX, requestEventId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

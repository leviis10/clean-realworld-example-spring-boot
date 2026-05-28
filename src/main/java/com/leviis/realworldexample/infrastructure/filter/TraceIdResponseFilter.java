package com.leviis.realworldexample.infrastructure.filter;

import com.leviis.realworldexample.infrastructure.constants.HeaderConstants;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public final class TraceIdResponseFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final String traceId = MDC.get("traceId");
        if (traceId != null) {
            httpServletResponse.addHeader(HeaderConstants.TRACE_ID_HEADER, traceId);
        }
        chain.doFilter(request, response);
    }
}

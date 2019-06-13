package service.controller;

import com.github.philippheuer.error.reporting.service.ErrorReportingService;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
abstract class AbstractController {

    @Autowired
    protected Tracer tracer;

    @Autowired(required = false)
    protected ErrorReportingService errorReportingService;

    protected CompositeMeterRegistry metricsRegistry = Metrics.globalRegistry;

    /**
     * Gets a new span for the specified section
     *
     * @param sectionName Section
     * @return Span
     */
    public Span activateSpan(String sectionName, Span parentSpan) {
        Span span = parentSpan;
        if (span == null) {
            span = tracer.activeSpan();
        }

        return tracer.buildSpan(String.format("%s.%s.%s", new Throwable().getStackTrace()[1].getClassName(), new Throwable().getStackTrace()[1].getMethodName(), sectionName)).asChildOf(span).start();
    }

}

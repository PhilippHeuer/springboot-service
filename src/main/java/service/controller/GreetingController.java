package service.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value="/v1", produces = {
    MediaType.APPLICATION_JSON_UTF8_VALUE
})
@Slf4j
public class GreetingController {

    @Autowired
    private Tracer tracer;

    @Autowired
    private MeterRegistry meterRegistry;

    /**
     * Greetings
     *
     * @param name
     * @return
     */
    @RequestMapping("/hello")
    @Timed
    public ResponseEntity<Mono<Map>> greeting(@RequestParam(value="name", defaultValue="World") String name) {
        Map<String, String> result = new HashMap<>();
        result.put("id", "1");
        result.put("text", String.format("Hello, %s!", name));

        Counter counter = meterRegistry.counter("test");
        counter.increment(2);

        // partial span to track independent operations
        Span requestSpan = tracer.activeSpan();
        Span span = tracer.buildSpan(String.format("%s.%s.%s", getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName(), "test")).asChildOf(requestSpan).start();
        try (Scope scope = tracer.scopeManager().activate(span)) {


        } catch(Exception ex) {
            Tags.ERROR.set(span, true);


            //span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
        } finally {
            span.finish();
        }




        return ResponseEntity.ok().body(Mono.just(result));
    }

}

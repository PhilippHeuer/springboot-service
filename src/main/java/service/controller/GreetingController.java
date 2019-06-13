package service.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.log.Fields;
import io.opentracing.tag.Tags;
import java.util.HashMap;
import java.util.Map;
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
public class GreetingController extends AbstractController {

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

        // partial span to track independent operations
        Span span = activateSpan("Response", null);
        try (Scope scope = tracer.scopeManager().activate(span)) {
            // step in the workflow
            result.put("id", "1");
            result.put("text", String.format("Hello, %s!", name));
            // end
        } catch (Exception ex) {
            Tags.ERROR.set(span, true);
            span.log(Map.of(Fields.EVENT, "error", Fields.ERROR_OBJECT, ex, Fields.MESSAGE, ex.getMessage()));
            // errorReportingService.handleException(ex);
        } finally {
            span.finish();
        }

        return ResponseEntity.ok().body(Mono.just(result));
    }

}

package io.webapp.monitor.endpoint;

import io.webapp.common.annotation.AdminEndPoint;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.util.List;

/**
 * @author ADMIN
 */
@AdminEndPoint
public class AdminHttpTraceEndpoint {

    private final HttpTraceRepository repository;

    public AdminHttpTraceEndpoint(HttpTraceRepository repository) {
        this.repository = repository;
    }

    public AdminHttpTraceDescriptor traces() {
        return new AdminHttpTraceDescriptor(this.repository.findAll());
    }

    public static final class AdminHttpTraceDescriptor {

        private final List<HttpTrace> traces;

        private AdminHttpTraceDescriptor(List<HttpTrace> traces) {
            this.traces = traces;
        }

        public List<HttpTrace> getTraces() {
            return this.traces;
        }
    }
}

package com.example.kibana_deploy_template.user.system.logs;
import org.slf4j.Logger;
import net.logstash.logback.marker.LogstashMarker;
import net.logstash.logback.marker.Markers;
import java.util.Map;



import java.util.HashMap;
import java.util.Map;

public class StructuredLogger {

    private final Logger logger;

    public StructuredLogger(Logger logger) {
        this.logger = logger;
    }

    public LogBuilder logBuilder() {
        return new LogBuilder();
    }

    public class LogBuilder {
        private String message;
        private final Map<String, Object> logFields = new HashMap<>();
        private String level = "INFO";

        public LogBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public LogBuilder withField(String key, Object value) {
            logFields.put(key, value);
            return this;
        }

        public LogBuilder withLevel(String level) {
            this.level = level;
            return this;
        }

        public void log() {
            LogstashMarker markers = Markers.appendEntries(logFields);
            switch (level.toUpperCase()) {
                case "INFO":
                    logger.info(markers, message);
                    break;
                case "ERROR":
                    logger.error(markers, message);
                    break;
                case "WARN":
                    logger.warn(markers, message);
                    break;
                case "DEBUG":
                    logger.debug(markers, message);
                    break;
                default:
                    logger.info(markers, message);
                    break;
            }
        }
    }
}

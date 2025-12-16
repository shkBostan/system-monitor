package com.shkbostan.system_monitor.service;

import com.shkbostan.system_monitor.config.MonitorProperties;
import com.shkbostan.system_monitor.domain.SystemMetrics;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on Dec, 2025
 *
 * Alert service evaluates system metrics and sends notifications if thresholds are exceeded.
 * Adds cooldown logic to prevent spamming emails.
 *
 * @author s Bostan
 */

@Service
public class AlertEvaluatorService {

    private final MonitorProperties properties;
    private final EmailNotificationService emailService;

    // Keeps track of the last time each alert type was sent
    private final Map<String, Instant> lastSentMap = new HashMap<>();

    // Cooldown period in seconds (adjustable)
    private final long COOLDOWN_SECONDS = 300; // 5 minutes

    public AlertEvaluatorService(MonitorProperties properties,
                                 EmailNotificationService emailService) {
        this.properties = properties;
        this.emailService = emailService;
    }

    public void evaluate(SystemMetrics metrics) {
        // If email notifications are disabled, do nothing
        if (!properties.getNotification().getEmail().isEnabled()) {
            return;
        }

        checkAndSendAlert("CPU_TEMPERATURE", metrics.getCpuTemperature(),
                properties.getThresholds().getCpuTemperature(),
                "CPU Temperature Alert",
                "CPU temperature is %.2f°C (threshold: %.2f°C)");

        checkAndSendAlert("CPU_LOAD", metrics.getCpuLoad(),
                properties.getThresholds().getCpuLoad(),
                "CPU Load Alert",
                "CPU load is %.2f%% (threshold: %.2f%%)");

        checkAndSendAlert("RAM_USAGE", metrics.getRamUsage(),
                properties.getThresholds().getRamUsage(),
                "RAM Usage Alert",
                "RAM usage is %.2f%% (threshold: %.2f%%)");
    }

    private void checkAndSendAlert(String type, double value, double threshold, String subject, String bodyFormat) {
        Instant now = Instant.now();
        Instant lastSent = lastSentMap.getOrDefault(type, Instant.EPOCH);

        // Check threshold and cooldown
        if (value >= threshold && now.isAfter(lastSent.plusSeconds(COOLDOWN_SECONDS))) {
            String body = String.format(bodyFormat, value, threshold);
            emailService.send(subject, body);

            // Update last sent time
            lastSentMap.put(type, now);
        }
    }
}


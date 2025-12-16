package com.shkbostan.system_monitor.service;

import com.shkbostan.system_monitor.config.MonitorProperties;
import com.shkbostan.system_monitor.domain.SystemMetrics;
import org.springframework.stereotype.Service;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */

@Service
public class AlertEvaluatorService {

    private final MonitorProperties properties;
    private final EmailNotificationService emailService;

    public AlertEvaluatorService(MonitorProperties properties,
                                 EmailNotificationService emailService) {
        this.properties = properties;
        this.emailService = emailService;
    }

    public void evaluate(SystemMetrics metrics) {

        if (metrics.getCpuTemperature() >=
                properties.getThresholds().getCpuTemperature()) {
            emailService.send("CPU Temperature Alert",
                    "CPU temperature is " + metrics.getCpuTemperature());
        }

        if (metrics.getCpuLoad() >=
                properties.getThresholds().getCpuLoad()) {
            emailService.send("CPU Load Alert",
                    "CPU load is " + metrics.getCpuLoad());
        }

        if (metrics.getRamUsage() >=
                properties.getThresholds().getRamUsage()) {
            emailService.send("RAM Usage Alert",
                    "RAM usage is " + metrics.getRamUsage());
        }
    }
}


package com.shkbostan.system_monitor.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "monitor")
public class MonitorProperties {

    private long intervalMs;
    private Thresholds thresholds;

    @Getter
    @Setter
    public static class Thresholds {
        private double cpuTemperature;
        private double cpuLoad;
        private double ramUsage;
    }
}

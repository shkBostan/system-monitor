package com.shkbostan.system_monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "monitor")
public class MonitorProperties {

    private long intervalMs;
    private Thresholds thresholds = new Thresholds();
    private Notification notification = new Notification();

    // getters and setters
    public long getIntervalMs() { return intervalMs; }
    public void setIntervalMs(long intervalMs) { this.intervalMs = intervalMs; }

    public Thresholds getThresholds() { return thresholds; }
    public void setThresholds(Thresholds thresholds) { this.thresholds = thresholds; }

    public Notification getNotification() { return notification; }
    public void setNotification(Notification notification) { this.notification = notification; }

    public static class Thresholds {
        private double cpuTemperature;
        private double cpuLoad;
        private double ramUsage;

        // getters and setters
        public double getCpuTemperature() { return cpuTemperature; }
        public void setCpuTemperature(double cpuTemperature) { this.cpuTemperature = cpuTemperature; }

        public double getCpuLoad() { return cpuLoad; }
        public void setCpuLoad(double cpuLoad) { this.cpuLoad = cpuLoad; }

        public double getRamUsage() { return ramUsage; }
        public void setRamUsage(double ramUsage) { this.ramUsage = ramUsage; }
    }

    public static class Notification {
        private Email email = new Email();

        // getters and setters
        public Email getEmail() { return email; }
        public void setEmail(Email email) { this.email = email; }

        public static class Email {
            private boolean enabled;
            private String to;

            // getters and setters
            public boolean isEnabled() { return enabled; }
            public void setEnabled(boolean enabled) { this.enabled = enabled; }

            public String getTo() { return to; }
            public void setTo(String to) { this.to = to; }
        }
    }
}


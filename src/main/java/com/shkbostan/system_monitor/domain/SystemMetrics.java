package com.shkbostan.system_monitor.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */
@Getter
@Setter
public class SystemMetrics {

    private final double cpuTemperature;
    private final double cpuLoad;
    private final double ramUsage;

    public SystemMetrics(double cpuTemperature, double cpuLoad, double ramUsage) {
        this.cpuTemperature = cpuTemperature;
        this.cpuLoad = cpuLoad;
        this.ramUsage = ramUsage;
    }
}

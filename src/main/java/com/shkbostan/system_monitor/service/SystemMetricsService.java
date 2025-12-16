package com.shkbostan.system_monitor.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * System metrics collector using OSHI.
 * Provides CPU load, RAM usage.
 *
 * Compatible with OSHI 6.x+
 *
 * @author S. Bostan
 */
@Service
public class SystemMetricsService {

    private final HardwareAbstractionLayer hardware;
    private final CentralProcessor processor;

    /**
     * Stores previous CPU ticks for correct load calculation.
     */
    private long[] previousCpuTicks;

    public SystemMetricsService() {
        SystemInfo systemInfo = new SystemInfo();
        this.hardware = systemInfo.getHardware();
        this.processor = hardware.getProcessor();
    }

    /**
     * Initialize CPU ticks after bean creation.
     */
    @PostConstruct
    public void init() {
        this.previousCpuTicks = processor.getSystemCpuLoadTicks();
    }

    /**
     * Returns RAM usage percentage.
     */
    public double getRamUsagePercent() {
        GlobalMemory memory = hardware.getMemory();

        double used = memory.getTotal() - memory.getAvailable();
        double usage = (used / memory.getTotal()) * 100;

        return round(usage);
    }

    /**
     * Returns system-wide CPU load percentage.
     * Must be called with a time gap (e.g. 3–5 seconds).
     */
    public double getCpuLoadPercent() {
        long[] currentTicks = processor.getSystemCpuLoadTicks();

        double load = processor
                .getSystemCpuLoadBetweenTicks(previousCpuTicks) * 100;

        previousCpuTicks = currentTicks;

        return round(load);
    }

    /**
     * Utility method for rounding to 2 decimals.
     */
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

package com.shkbostan.system_monitor.scheduler;

import com.shkbostan.system_monitor.domain.SystemMetrics;
import com.shkbostan.system_monitor.service.AlertEvaluatorService;
import com.shkbostan.system_monitor.service.CpuTemperatureService;
import com.shkbostan.system_monitor.service.SystemMetricsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */

@Component
public class MonitoringScheduler {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringScheduler.class);

    private final CpuTemperatureService tempService;
    private final SystemMetricsService metricsService;
    private final AlertEvaluatorService evaluatorService;

    public MonitoringScheduler(CpuTemperatureService tempService,
                               SystemMetricsService metricsService,
                               AlertEvaluatorService evaluatorService) {
        this.tempService = tempService;
        this.metricsService = metricsService;
        this.evaluatorService = evaluatorService;
    }

    @Scheduled(fixedRateString = "#{@monitorProperties.intervalMs}")
    public void monitor() {
        SystemMetrics metrics = new SystemMetrics(
                tempService.readCpuTemperature(),
                metricsService.getCpuLoadPercent(),
                metricsService.getRamUsagePercent()
        );

        logger.info("Monitoring metrics: CPU Temp={}°C, CPU Load={}%, RAM Usage={}%",
                metrics.getCpuTemperature(),
                metrics.getCpuLoad(),
                metrics.getRamUsage());

        evaluatorService.evaluate(metrics);
    }
}



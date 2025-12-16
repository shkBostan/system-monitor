package com.shkbostan.system_monitor.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */

@Service
public class CpuTemperatureService {

    /**
     * Reads CPU Package temperature via LibreHardwareMonitor (WMI).
     */
    public double readCpuTemperature() {
        try {
            String command =
                    "Get-WmiObject -Namespace root\\LibreHardwareMonitor -Class Sensor " +
                            "| Where-Object { $_.SensorType -eq 'Temperature' -and $_.Name -eq 'CPU Package' } " +
                            "| Select-Object -ExpandProperty Value";

            Process process = new ProcessBuilder(
                    "powershell.exe", "-NoProfile", "-Command", command
            ).start();

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String line = reader.readLine();
                if (line != null && !line.isBlank()) {
                    return Double.parseDouble(line.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}

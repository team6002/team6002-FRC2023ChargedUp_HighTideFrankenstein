package frc.robot;

import java.util.Map;

import edu.wpi.first.util.datalog.*;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;

public class DataLogger {
    private static Map<String, BooleanLogEntry> boolMap;
    private static Map<String, DoubleLogEntry> doubleMap;
    private static boolean loggingEnabled = false;

    public static void start() {
        /* Enable recording data sent to NetworkTables (i.e. SmartDashboard) */
        DataLogManager.logNetworkTables(true);

        /* Start data logging */
        DataLogManager.start();

        /* Log DS control and joystick data */
        DriverStation.startDataLog(DataLogManager.getLog());

        loggingEnabled = true;
    }

    public static void log(String key, boolean value) {
        if (!loggingEnabled) {
            return;
        }

        if (!boolMap.containsKey(key)) {
            boolMap.put(key, new BooleanLogEntry(DataLogManager.getLog(), key));
        }

        BooleanLogEntry entry = boolMap.get(key);
        entry.append(value);
    }

    public static void log(String key, double value) {
        if (!loggingEnabled) {
            return;
        }

        if (!doubleMap.containsKey(key)) {
            doubleMap.put(key, new DoubleLogEntry(DataLogManager.getLog(), key));
        }

        DoubleLogEntry entry = doubleMap.get(key);
        entry.append(value);
    }

    public static void log(String message) {
        if (!loggingEnabled) {
            return;
        }

        DataLogManager.log(message);
    }
}

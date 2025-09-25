package edu.ccrm.config;

import java.nio.file.Paths;

public final class AppConfig {
    private static AppConfig instance;
    private final String dataFolder;

    private AppConfig() {
        // Load config from file / env or hardcode demo path
        this.dataFolder = "data";
    }

    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getDataFolder() {
        return dataFolder;
    }

    public String getStudentsFile() {
        return Paths.get(dataFolder, "students.csv").toString();
    }

    public String getCoursesFile() {
        return Paths.get(dataFolder, "courses.csv").toString();
    }
}

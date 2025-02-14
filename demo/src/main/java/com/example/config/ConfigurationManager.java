package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.example.exception.EditorException;

public class ConfigurationManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManager.class);
    private static ConfigurationManager instance;
    private Configuration configuration;

    private static final String DEFAULT_CONFIG_DIR = ".my-html";
    private static final String CONFIG_FILENAME = "config.json";

    private ConfigurationManager() {
        Path configPath = getConfigPath();
        this.configuration = new JsonConfiguration(configPath);
        initializeDefaultConfig();
    }

    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private Path getConfigPath() {
        String userHome = System.getProperty("user.home");
        Path configDir = Paths.get(userHome, DEFAULT_CONFIG_DIR);
        if (!configDir.toFile().exists()) {
            configDir.toFile().mkdirs();
        }
        return configDir.resolve(CONFIG_FILENAME);
    }

    private void initializeDefaultConfig() {
        // 编辑器基本设置
        setIfNotExists("editor.showId", true);
        setIfNotExists("editor.indentSize", 2);
        setIfNotExists("editor.backup.enabled", true);
        setIfNotExists("editor.backup.interval", 300); // 5分钟
        setIfNotExists("editor.backup.maxFiles", 5);

        // 拼写检查设置
        setIfNotExists("spellCheck.enabled", true);
        setIfNotExists("spellCheck.language", "en_US");
        setIfNotExists("spellCheck.customDictionary", "custom.dict");
        setIfNotExists("spellCheck.skipTags", "code,pre,script,style");

        // 文件系统设置
        setIfNotExists("filesystem.ignorePatterns", 
            ".git,.idea,node_modules,target,build");
        setIfNotExists("filesystem.maxDepth", 10);

        // UI设置
        setIfNotExists("ui.theme", "light");
        setIfNotExists("ui.font.family", "monospace");
        setIfNotExists("ui.font.size", 12);
    }

    private void setIfNotExists(String key, Object value) {
        try {
            configuration.getString(key);
        } catch (EditorException e) {
            configuration.set(key, value);
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}

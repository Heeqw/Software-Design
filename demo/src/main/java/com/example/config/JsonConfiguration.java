package com.example.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import com.example.exception.EditorException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConfiguration implements Configuration {
    private static final Logger logger = LoggerFactory.getLogger(JsonConfiguration.class);

    private final ObjectMapper mapper;
    private final Path configPath;
    private ObjectNode config;

    public JsonConfiguration(Path configPath) {
        this.configPath = configPath;
        this.mapper = new ObjectMapper();
        reload();
    }

    @Override
    public void reload(){
        try {
            if (!Files.exists(configPath)){
                config = mapper.createObjectNode();
                save();
            } else {
                config = (ObjectNode) mapper.readTree(configPath.toFile());
            }
        } catch (IOException e) {
            logger.error("Failed to load configuration from {}", configPath, e);
            throw new EditorException("Failed to load configuration", e);
        }
    }

    @Override
    public void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(configPath.toFile(), config);
        } catch (IOException e) {
            logger.error("Failed to save configuration to {}", configPath, e);
            throw new EditorException("Failed to save configuration", e);
        }
    }

    @Override
    public String getString(String key) {
        validateKey(key);
        return config.get(key).asText();
    }

    @Override
    public String getString(String key, String defaultValue) {
        return config.has(key) ? config.get(key).asText() : defaultValue;
    }

    @Override
    public int getInt(String key) {
        validateKey(key);
        return config.get(key).asInt();
    }

    @Override
    public int getInt(String key, int defaultValue) {
        return config.has(key) ? config.get(key).asInt() : defaultValue;
    }

    @Override
    public boolean getBoolean(String key) {
        validateKey(key);
        return config.get(key).asBoolean();
    }

    @Override
    public boolean getBoolean(String key, boolean defaultValue) {
        return config.has(key) ? config.get(key).asBoolean() : defaultValue;
    }

    @Override
    public Path getPath(String key) {
        validateKey(key);
        return Path.of(config.get(key).asText());
    }

    @Override
    public Path getPath(String key, Path defaultValue) {
        return config.has(key) ? Path.of(config.get(key).asText()) : defaultValue;
    }

    @Override
    public void set(String key, Object value) {
        if (value instanceof String) {
            config.put(key, (String) value);
        } else if (value instanceof Integer) {
            config.put(key, (Integer) value);
        } else if (value instanceof Boolean) {
            config.put(key, (Boolean) value);
        } else if (value instanceof Path) {
            config.put(key, value.toString());
        } else {
            throw new EditorException("Unsupported configuration value type: " + 
                value.getClass().getName());
        }
    }

    @Override
    public Map<String, Object> getAll() {
        Map<String, Object> result = new HashMap<>();
        config.fields().forEachRemaining(entry -> 
            result.put(entry.getKey(), entry.getValue()));
        return result;
    }

    private void validateKey(String key) {
        if (!config.has(key)) {
            throw new EditorException("Configuration key not found: " + key);
        }
    }
}

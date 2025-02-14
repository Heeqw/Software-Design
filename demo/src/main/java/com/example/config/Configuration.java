package com.example.config;
import java.nio.file.Path;
import java.util.Map;

public interface Configuration {
    /**
     * 获取配置项
     */
    String getString(String key);
    String getString(String key, String defaultValue);
    int getInt(String key);
    int getInt(String key, int defaultValue);
    boolean getBoolean(String key);
    boolean getBoolean(String key, boolean defaultValue);
    Path getPath(String key);
    Path getPath(String key, Path defaultValue);
    
    /**
     * 获取所有配置项
     */
    Map<String, Object> getAll();
    
    /**
     * 设置配置项
     */
    void set(String key, Object value);
    
    /**
     * 保存配置
     */
    void save();
    
    /**
     * 重载配置
     */
    void reload();
}

package com.example.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.example.exception.EditorException;

class JsonConfigurationTest {
    
    @TempDir
    Path tempDir;
    
    private Path configPath;
    private JsonConfiguration config;
    
    @BeforeEach
    void setUp() throws IOException {
        configPath = tempDir.resolve("test-config.json");
        config = new JsonConfiguration(configPath);
    }
    
    @Test
    void testNewConfigurationCreation() {
        assertTrue(Files.exists(configPath), "Configuration file should be created");
        
        // 验证是否创建了空的配置文件
        Map<String, Object> all = config.getAll();
        assertTrue(all.isEmpty(), "New configuration should be empty");
    }
    
    @Test
    void testSetAndGetString() {
        config.set("test.string", "value");
        assertEquals("value", config.getString("test.string"));
        assertEquals("default", config.getString("nonexistent", "default"));
    }
    
    @Test
    void testSetAndGetInt() {
        config.set("test.int", 42);
        assertEquals(42, config.getInt("test.int"));
        assertEquals(100, config.getInt("nonexistent", 100));
    }
    
    @Test
    void testSetAndGetBoolean() {
        config.set("test.boolean", true);
        assertTrue(config.getBoolean("test.boolean"));
        assertFalse(config.getBoolean("nonexistent", false));
    }
    
    @Test
    void testSetAndGetPath() {
        Path testPath = Path.of("/test/path");
        config.set("test.path", testPath);
        assertEquals(testPath, config.getPath("test.path"));
        
        Path defaultPath = Path.of("/default/path");
        assertEquals(defaultPath, config.getPath("nonexistent", defaultPath));
    }
    
    @Test
    void testSaveAndReload() {
        // 设置一些测试值
        config.set("test.string", "value");
        config.set("test.int", 42);
        config.set("test.boolean", true);
        
        // 保存配置
        config.save();
        
        // 创建新的配置实例来加载保存的文件
        JsonConfiguration newConfig = new JsonConfiguration(configPath);
        
        // 验证值是否正确加载
        assertEquals("value", newConfig.getString("test.string"));
        assertEquals(42, newConfig.getInt("test.int"));
        assertTrue(newConfig.getBoolean("test.boolean"));
    }
    
    @Test
    void testGetAll() {
        config.set("test.string", "value");
        config.set("test.int", 42);
        config.set("test.boolean", true);
        
        Map<String, Object> all = config.getAll();
        
        assertEquals(3, all.size());
        assertTrue(all.containsKey("test.string"));
        assertTrue(all.containsKey("test.int"));
        assertTrue(all.containsKey("test.boolean"));
    }
    
    @Test
    void testNonexistentKey() {
        assertThrows(EditorException.class, () -> config.getString("nonexistent"));
        assertThrows(EditorException.class, () -> config.getInt("nonexistent"));
        assertThrows(EditorException.class, () -> config.getBoolean("nonexistent"));
        assertThrows(EditorException.class, () -> config.getPath("nonexistent"));
    }
    
    @Test
    void testUnsupportedType() {
        assertThrows(EditorException.class, () -> config.set("test.unsupported", new Object()));
    }
    
    @Test
    void testInvalidConfigFile() throws IOException {
        // 创建一个无效的JSON文件
        Files.writeString(configPath, "invalid json content");
        
        assertThrows(EditorException.class, () -> new JsonConfiguration(configPath));
    }
    
    
} 
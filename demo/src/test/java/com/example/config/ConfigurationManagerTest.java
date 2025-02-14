package com.example.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ConfigurationManagerTest {
    
    @Test
    void testGetInstance() {
        ConfigurationManager manager1 = ConfigurationManager.getInstance();
        ConfigurationManager manager2 = ConfigurationManager.getInstance();
        
        // 验证单例模式
        assertSame(manager1, manager2);
    }
    
    @Test
    void testDefaultConfiguration() {
        Configuration config = ConfigurationManager.getInstance().getConfiguration();
        
        // 验证默认配置值
        assertTrue(config.getBoolean(EditorConfig.SHOW_ID));
        assertEquals(2, config.getInt(EditorConfig.INDENT_SIZE));
        assertTrue(config.getBoolean(EditorConfig.BACKUP_ENABLED));
        assertEquals(300, config.getInt(EditorConfig.BACKUP_INTERVAL));
        assertEquals(5, config.getInt(EditorConfig.BACKUP_MAX_FILES));
        
        assertTrue(config.getBoolean(EditorConfig.SPELL_CHECK_ENABLED));
        assertNotNull(config.getString(EditorConfig.IGNORE_PATTERNS));
        assertEquals(10, config.getInt(EditorConfig.MAX_DEPTH));
        
        assertEquals("light", config.getString(EditorConfig.THEME));
        assertEquals("monospace", config.getString(EditorConfig.FONT_FAMILY));
        assertEquals(12, config.getInt(EditorConfig.FONT_SIZE));
    }
} 
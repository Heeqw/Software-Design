package com.example.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SessionStateTest {
    
    @Test
    void testSessionState() {
        SessionState state = new SessionState();
        
        // 测试打开的文件列表
        List<String> openFiles = Arrays.asList("file1.html", "file2.html");
        state.setOpenFiles(openFiles);
        assertEquals(openFiles, state.getOpenFiles());
        
        // 测试活动文件
        state.setActiveFile("file1.html");
        assertEquals("file1.html", state.getActiveFile());
        
        // 测试显示ID设置
        Map<String, Boolean> showIdSettings = new HashMap<>();
        showIdSettings.put("file1.html", true);
        showIdSettings.put("file2.html", false);
        state.setShowIdSettings(showIdSettings);
        assertEquals(showIdSettings, state.getShowIdSettings());
    }
} 
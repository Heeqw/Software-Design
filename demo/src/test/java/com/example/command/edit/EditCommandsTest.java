package com.example.command.edit;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.core.Editor;

class EditCommandsTest {
    
    @Mock private Editor mockEditor;
    @Mock private Document mockDocument;
    @Mock private Element mockElement;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // 基本设置
        when(mockEditor.getDocument()).thenReturn(mockDocument);
        
        // 模拟文档操作
        when(mockDocument.createElement(anyString())).thenReturn(mockElement);
        
        // 模拟元素操作
        when(mockElement.attr(anyString(), anyString())).thenReturn(mockElement);
        when(mockElement.text(anyString())).thenReturn(mockElement);
        when(mockElement.clone()).thenReturn(mockElement);
        when(mockElement.parent()).thenReturn(mockElement);
        when(mockElement.children()).thenReturn(new org.jsoup.select.Elements());
    }
    
    @Test
    void testAppendCommand() {
        // 模拟父元素
        Element mockParent = mock(Element.class);
        when(mockParent.appendChild(any(Element.class))).thenReturn(mockParent);
        when(mockDocument.getElementById("parent-id")).thenReturn(mockParent);
        
        // 确保新ID不存在
        when(mockDocument.getElementById("new-id")).thenReturn(null);
        
        AppendCommand command = new AppendCommand(mockEditor, "div", "new-id", "parent-id", "test text");
        command.execute();
        
        verify(mockElement).attr("id", "new-id");
        verify(mockElement).text("test text");
        verify(mockParent).appendChild(mockElement);
        verify(mockEditor).markModified();
        
        // 测试撤销
        command.undo();
        verify(mockElement).remove();
    }
    
    // ... 其他测试方法类似修改
} 
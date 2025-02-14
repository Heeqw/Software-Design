package com.example.config;

public final class EditorConfig {
    // 编辑器基本设置
    public static final String SHOW_ID = "editor.showId";
    public static final String INDENT_SIZE = "editor.indentSize";
    public static final String BACKUP_ENABLED = "editor.backup.enabled";
    public static final String BACKUP_INTERVAL = "editor.backup.interval";
    public static final String BACKUP_MAX_FILES = "editor.backup.maxFiles";

    // 拼写检查设置
    public static final String SPELL_CHECK_ENABLED = "spellCheck.enabled";
    // public static final String SPELL_CHECK_LANGUAGE = "spellCheck.language";
    // public static final String SPELL_CHECK_CUSTOM_DICT = "spellCheck.customDictionary";
    // public static final String SPELL_CHECK_SKIP_TAGS = "spellCheck.skipTags";

    // 文件系统设置
    public static final String IGNORE_PATTERNS = "filesystem.ignorePatterns";
    public static final String MAX_DEPTH = "filesystem.maxDepth";

    // UI设置
    public static final String THEME = "ui.theme";
    public static final String FONT_FAMILY = "ui.font.family";
    public static final String FONT_SIZE = "ui.font.size";

    private EditorConfig() {
        // 防止实例化
    }
}

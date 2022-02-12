package com.danifoldi.microbase;

public interface BaseMessage {
    BaseMessage rawText(String text);
    BaseMessage colorizedText(String text);
    BaseMessage providedText(String key);
    default BaseMessage newLine() {
        return rawText("\n");
    }

    BaseMessage replace(String template, String replacement);

    BaseMessage click(ClickType clickType);
    BaseMessage hover();
    BaseMessage text();

    enum ClickType {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        CHANGE_PAGE,
        COPY_TO_CLIPBOARD
    }
}

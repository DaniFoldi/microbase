package com.danifoldi.microbase;


import com.danifoldi.microbase.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BaseMessage {
    private final List<Operation> operations = new ArrayList<>();

    public BaseMessage rawText(String text) {
        operations.add(new Operation("text.raw", Pair.of(text, null)));
        return this;
    }

    public BaseMessage colorizedText(String text) {
        operations.add(new Operation("text.colorized", Pair.of(text, null)));
        return this;
    }

    public BaseMessage providedText(String key) {
        operations.add(new Operation("text.provide", Pair.of(key, null)));
        return this;
    }

    public BaseMessage newLine() {
        return rawText("\n");
    }

    public BaseMessage replace(String template, String replacement) {
        operations.add(new Operation("template.all", Pair.of(template, replacement)));
        return this;
    }

    public BaseMessage click(ClickType clickType) {
        operations.add(new Operation("mode.click", Pair.of(clickType.name(), null)));
        return this;
    }

    public BaseMessage hover() {
        operations.add(new Operation("mode.hover", null));
        return this;
    }

    public BaseMessage text() {
        operations.add(new Operation("mode.text", null));
        return this;
    }

    public Component convert() {
        Component result = Component.empty();

        String mode = "";
        Component text = Component.empty();
        Component hover = Component.empty();
        String clickType = null;
        StringBuilder clickValue = new StringBuilder();

        for (Operation operation : operations) {
            Pair<String, String> action = Pair.of(operation.type.split("\\."));
            switch (action.a()) {
                case "mode":
                    if (!mode.equals(action.b())) {
                        mode = action.b();
                        if (mode.equals("click")) {
                            clickType = operation.value.a();
                        }
                    } else {
                        result = result.append(
                                text
                                        .hoverEvent(hover)
                                        .clickEvent(clickType == null ? null : ClickEvent.clickEvent(ClickEvent.Action.valueOf(clickType), clickValue.toString()))
                        );
                        text = Component.empty();
                        hover = Component.empty();
                        clickType = null;
                        clickValue = new StringBuilder();
                    }
                    break;
                case "template":
                    switch (mode) {
                        case "text" -> text = text.replaceText(TextReplacementConfig.builder().matchLiteral(operation.value.a()).replacement(operation.value.b()).build());
                        case "hover" -> hover = hover.replaceText(TextReplacementConfig.builder().matchLiteral(operation.value.a()).replacement(operation.value.b()).build());
                        case "click" -> clickValue = new StringBuilder(clickValue.toString().replace(operation.value.a(), operation.value.b()));
                    }
                    break;
                case "text":
                    switch (mode) {
                        case "text" -> text = switch (action.b()) {
                            case "raw" -> text.append(Component.text(operation.value.a()));
                            case "colorized" -> text.append(LegacyComponentSerializer.legacyAmpersand().deserialize(operation.value.a()));
                            case "provide" -> text.append(LegacyComponentSerializer.legacyAmpersand().deserialize(Microbase.provideMessage(operation.value.a())));
                            default -> text;
                        };
                        case "hover" -> hover = switch (action.b()) {
                            case "raw" -> hover.append(Component.text(operation.value.a()));
                            case "colorized" -> hover.append(LegacyComponentSerializer.legacyAmpersand().deserialize(operation.value.a()));
                            case "provide" -> hover.append(LegacyComponentSerializer.legacyAmpersand().deserialize(Microbase.provideMessage(operation.value.a())));
                            default -> hover;
                        };
                        case "click" -> clickValue.append(switch (action.b()) {
                            case "raw", "colorized" -> operation.value.a();
                            case "provide" -> Microbase.provideMessage(operation.value.a());
                            default -> "";
                        });
                    }
                    break;
            }
        }

        return result;
    }

    public enum ClickType {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        CHANGE_PAGE,
        COPY_TO_CLIPBOARD
    }

    public static class Operation {
        String type;
        Pair<String, String> value;

        Operation(String type, Pair<String, String> value) {
            this.type = type;
            this.value = value;
        }
    }
}

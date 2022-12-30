package com.danifoldi.microbase;


import com.danifoldi.microbase.util.MapUtil;
import com.danifoldi.microbase.util.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class BaseMessage {
    private static final Map<Character, Integer> widths = MapUtil.make(
            ' ', 8,
            '!', 4,
            '"', 8,
            '#', 12,
            '$', 12,
            '%', 12,
            '&', 12,
            '\'', 4,
            '(', 8,
            ')', 8,
            '*', 8,
            '+', 12,
            ',', 4,
            '-', 12,
            '.', 4,
            '/', 12,
            '0', 12,
            '1', 12,
            '2', 12,
            '3', 12,
            '4', 12,
            '5', 12,
            '6', 12,
            '7', 12,
            '8', 12,
            '9', 12,
            ':', 4,
            ';', 4,
            '<', 10,
            '=', 12,
            '>', 10,
            '?', 12,
            '@', 14,
            'A', 12,
            'Á', 12,
            'B', 12,
            'C', 12,
            'D', 12,
            'E', 12,
            'É', 12,
            'F', 12,
            'G', 12,
            'H', 12,
            'I', 8,
            'Í', 8,
            'J', 12,
            'K', 12,
            'L', 12,
            'M', 12,
            'N', 12,
            'O', 12,
            'Ó', 12,
            'Ö', 12,
            'Ő', 12,
            'P', 12,
            'Q', 12,
            'S', 12,
            'T', 12,
            'U', 12,
            'Ú', 12,
            'Ü', 12,
            'Ű', 12,
            'V', 12,
            'W', 12,
            'X', 12,
            'Y', 12,
            'Z', 12,
            '[', 8,
            '\\', 12,
            ']', 8,
            '^', 12,
            '_', 12,
            '`', 6,
            'a', 12,
            'á', 12,
            'b', 12,
            'c', 12,
            'd', 12,
            'e', 12,
            'é', 12,
            'f', 10,
            'g', 12,
            'h', 12,
            'i', 4,
            'í', 6,
            'j', 12,
            'k', 10,
            'l', 6,
            'm', 12,
            'n', 12,
            'o', 12,
            'ó', 12,
            'ö', 12,
            'ő', 12,
            'p', 12,
            'q', 12,
            'r', 12,
            's', 12,
            't', 8,
            'u', 12,
            'ú', 12,
            'ü', 12,
            'ű', 12,
            'v', 12,
            'w', 12,
            'x', 12,
            'y', 12,
            'z', 12,
            '{', 8,
            '|', 4,
            '}', 8,
            '~', 14
    );
    private static final Map<Character, Integer> boldWidths = MapUtil.make(
            ' ', 10,
            '!', 6,
            '"', 10,
            '#', 14,
            '$', 14,
            '%', 14,
            '&', 14,
            '\'', 6,
            '(', 10,
            ')', 10,
            '*', 10,
            '+', 14,
            ',', 6,
            '-', 14,
            '.', 6,
            '/', 14,
            '0', 17,
            '1', 14,
            '2', 14,
            '3', 14,
            '4', 14,
            '5', 14,
            '6', 14,
            '7', 14,
            '8', 14,
            '9', 14,
            ':', 6,
            ';', 6,
            '<', 12,
            '=', 14,
            '>', 12,
            '?', 14,
            '@', 16,
            'A', 14,
            'Á', 14,
            'B', 14,
            'C', 14,
            'D', 14,
            'E', 14,
            'É', 14,
            'F', 14,
            'G', 14,
            'H', 14,
            'I', 10,
            'Í', 10,
            'J', 14,
            'K', 14,
            'L', 14,
            'M', 14,
            'N', 14,
            'O', 14,
            'Ó', 14,
            'Ö', 14,
            'Ő', 14,
            'P', 14,
            'Q', 14,
            'S', 14,
            'T', 14,
            'U', 14,
            'Ú', 14,
            'Ü', 14,
            'Ű', 14,
            'V', 14,
            'W', 14,
            'X', 14,
            'Y', 14,
            'Z', 14,
            '[', 10,
            '\\', 14,
            ']', 10,
            '^', 14,
            '_', 14,
            '`', 8,
            'a', 14,
            'á', 14,
            'b', 14,
            'c', 14,
            'd', 14,
            'e', 14,
            'é', 14,
            'f', 12,
            'g', 14,
            'h', 14,
            'i', 6,
            'í', 8,
            'j', 14,
            'k', 12,
            'l', 8,
            'm', 14,
            'n', 14,
            'o', 14,
            'ó', 14,
            'ö', 14,
            'ő', 14,
            'p', 14,
            'q', 14,
            'r', 14,
            's', 14,
            't', 10,
            'u', 14,
            'ú', 14,
            'ü', 14,
            'ű', 14,
            'v', 14,
            'w', 14,
            'x', 14,
            'y', 14,
            'z', 14,
            '{', 10,
            '|', 6,
            '}', 10,
            '~', 16
    );
    private final List<Operation> operations = new ArrayList<>();

    private static Component convertString(String message, boolean serialize, boolean center) {
        TextComponent component = serialize ? LegacyComponentSerializer.legacyAmpersand().deserialize(message) : Component.text(message);
        return center ? centerComponent(component) : component;
    }

    private static final int leftOffset = 15;
    private static final int defaultOffset = 12;
    private static final int lineSize = 664;
    private static Component centerComponent(TextComponent component) {
        return component
                .content(
                Arrays.stream(
                                component
                                        .content()
                                        .split("\n")
                        )
                        .map(line -> {
                                    int length = line
                                            .codePoints()
                                            .mapToObj(c -> (char) c)
                                            .map(c -> component.hasDecoration(TextDecoration.BOLD) ? boldWidths.get(c) : widths.get(c))
                                            .reduce(Integer::sum)
                                            .orElse(0);
                                    return " ".repeat(((lineSize - leftOffset - defaultOffset) / 2 - length / 2) / widths.get(' '));
                                }
                        )
                        .collect(Collectors.joining("\n"))
        )
                .children(new ArrayList<>(component
                        .children()
                        .stream()
                        .map(child -> child instanceof TextComponent tc ? centerComponent(tc) : child).toList()));
    }

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

    public BaseMessage center() {
        operations.add(new Operation("center"));
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

    public BaseMessage append(BaseMessage message) {
        operations.addAll(message.operations);
        return this;
    }

    public Component convert() {
        Component result = Component.empty();

        String mode = "";
        Component text = Component.empty();
        Component hover = Component.empty();
        String clickType = null;
        StringBuilder clickValue = new StringBuilder();
        boolean center = false;

        for (Operation operation : operations) {
            Pair<String, String> action = Pair.from(operation.type);
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
                        center = false;
                    }
                    break;
                case "center":
                    center = true;
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
                        case "text" -> //noinspection DuplicatedCode
                                text = switch (action.b()) {
                            case "raw" -> text.append(convertString(operation.value.a(), false, center));
                            case "colorized" -> text.append(convertString(operation.value.a(), true, center));
                            case "provide" -> text.append(convertString(Microbase.provideMessage(operation.value.a()), false, center));
                            default -> text;
                        };
                        case "hover" -> //noinspection DuplicatedCode
                                hover = switch (action.b()) {
                            case "raw" -> hover.append(convertString(operation.value.a(), false, center));
                            case "colorized" -> hover.append(convertString(operation.value.a(), true, center));
                            case "provide" -> hover.append(convertString(Microbase.provideMessage(operation.value.a()), false, center));
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
        final String type;
        Pair<String, String> value;

        Operation(String type, Pair<String, String> value) {
            this.type = type;
            this.value = value;
        }

        Operation(String type) {
            this.type = type;
        }
    }
}

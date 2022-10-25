package com.danifoldi.microbase.bungeecord;

import com.danifoldi.microbase.BaseMessage;
import com.danifoldi.microbase.Microbase;
import com.danifoldi.microbase.util.Pair;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class BungeecordBaseMessage implements BaseMessage {
    List<Operation> operations = new ArrayList<>();

    @Override
    public BaseMessage rawText(String text) {
        operations.add(new Operation("text.raw", Pair.of(text, null)));
        return this;
    }

    @Override
    public BaseMessage colorizedText(String text) {
        operations.add(new Operation("text.colorized", Pair.of(text, null)));
        return this;
    }

    @Override
    public BaseMessage providedText(String key) {
        operations.add(new Operation("text.provide", Pair.of(key, null)));
        return this;
    }

    @Override
    public BaseMessage replace(String template, String replacement) {
        operations.add(new Operation("template.all", Pair.of(template, replacement)));
        return this;
    }

    @Override
    public BaseMessage click(ClickType clickType) {
        operations.add(new Operation("mode.click", Pair.of(clickType.name(), null)));
        return this;
    }

    @Override
    public BaseMessage hover() {
        operations.add(new Operation("mode.hover", null));
        return this;
    }

    @Override
    public BaseMessage text() {
        operations.add(new Operation("mode.text", null));
        return this;
    }

    @SuppressWarnings("deprecation")
    BaseComponent convert() {
        BaseComponent result = new TextComponent();

        String mode = "";
        BaseComponent text = new TextComponent();
        BaseComponent hover = new TextComponent();
        String clickType = null;
        StringBuilder clickValue = new StringBuilder();

        for (Operation operation: operations) {
            Pair<String, String> action = Pair.of(operation.type.split("\\."));
            switch (action.a()) {
                case "mode":
                    if (!mode.equals(action.b())) {
                        mode = action.b();
                        if (mode.equals("click")) {
                            clickType = operation.value.a();
                        }
                    } else {
                        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{hover});
                        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.valueOf(clickType), clickValue.toString());
                        text.setHoverEvent(hoverEvent);
                        text.setClickEvent(clickEvent);
                        result.addExtra(text);
                        text = new TextComponent();
                        hover = new TextComponent();
                        clickType = null;
                        clickValue = new StringBuilder();
                    }
                    break;
                case "template":
                    switch (mode) {
                        case "text" -> text = new TextComponent(text.toLegacyText().replace(operation.value.a(), operation.value.b()));
                        case "hover" -> hover = new TextComponent(hover.toLegacyText().replace(operation.value.a(), operation.value.b()));
                        case "click" -> clickValue = new StringBuilder(clickValue.toString().replace(operation.value.a(), operation.value.b()));
                    }
                    break;
                case "text":
                    switch (mode) {
                        case "text":
                            switch (action.b()) {
                                case "raw" -> text.addExtra(operation.value.a());
                                case "colorized" -> text.addExtra(ChatColor.translateAlternateColorCodes('&', operation.value.a()));
                                case "provide" -> text.addExtra(Microbase.provideMessage(operation.value.a()));
                            }
                            break;
                        case "hover":
                            switch (action.b()) {
                                case "raw" -> hover.addExtra(operation.value.a());
                                case "colorized" -> hover.addExtra(ChatColor.translateAlternateColorCodes('&', operation.value.a()));
                                case "provide" -> hover.addExtra(Microbase.provideMessage(operation.value.a()));
                            }
                            break;
                        case "click":
                            clickValue.append(switch (action.b()) {
                                case "raw", "colorized" -> operation.value.a();
                                case "provide" -> Microbase.provideMessage(operation.value.a());
                                default -> "";
                            });
                            break;
                    }
                    break;
            }
        }

        return result;
    }

    static class Operation {
        String type;
        Pair<String, String> value;

        Operation(String type, Pair<String, String> value) {
            this.type = type;
            this.value = value;
        }
    }
}

package dev.scaraz.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Objects;

@Slf4j
@Component
public class MessageTranslator {

    private static MessageTranslator translator;

    private final MessageSource source;

    private final Locale lang_id = new Locale("id");

    public MessageTranslator(MessageSource source) {
        this.source = source;
        translator = this;
    }

    public String message(String code, @Nullable Locale locale, @Nullable Object[] args) {
        return source.getMessage(code, args, Objects.requireNonNullElse(
                locale, LocaleContextHolder.getLocale()));
    }

    public String message(String code, @Nullable Locale locale) {
        return source.getMessage(code, null, Objects.requireNonNullElse(
                locale, LocaleContextHolder.getLocale()));
    }

    public String message(String code) {
        return message(code, null);
    }

    public String toLocal(String code, Object... args) {
        return message(code, lang_id, args.length == 0 ? null : args);
    }

}

package cc.hongz.ztools.i18n;

import cc.hongz.ztools.status.AjaxStatusInterface;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化消息解析器.
 * <p>
 * 将业务状态码解析为本地化消息。消息键直接使用业务状态码（{@code DOMAIN_CODE}），
 * 对应 {@code messages_xx_XX.properties} 中的键（如 {@code COMMON_000=操作成功}）。
 * 未配置时回退 {@link AjaxStatusInterface#getDefaultMessage()}。
 * </p>
 *
 * @author hongz
 * @since 1.0.0-SNAPSHOT
 */
public class I18nMessageResolver {

    /**
     * Spring 消息源.
     */
    private final MessageSource messageSource;

    /**
     * 默认语言环境.
     */
    private Locale defaultLocale = Locale.SIMPLIFIED_CHINESE;

    /**
     * 是否启用国际化.
     */
    private boolean i18nEnabled = true;

    /**
     * 创建国际化消息解析器.
     *
     * @param messageSource Spring 消息源
     */
    public I18nMessageResolver(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 设置默认语言环境.
     *
     * @param locale 默认语言环境
     * @return this
     */
    public I18nMessageResolver setDefaultLocale(final Locale locale) {
        this.defaultLocale = locale;
        return this;
    }

    /**
     * 设置是否启用国际化.
     *
     * @param enabled 是否启用
     * @return this
     */
    public I18nMessageResolver setI18nEnabled(final boolean enabled) {
        this.i18nEnabled = enabled;
        return this;
    }

    /**
     * 按业务状态码解析国际化消息.
     *
     * @param status 业务状态码
     * @param args   消息参数
     * @return 国际化消息
     */
    public String resolve(final AjaxStatusInterface status, final Object... args) {
        if (status == null) {
            return "";
        }
        return getMessage(status.getCode(), status.getDefaultMessage(), args);
    }

    /**
     * 获取国际化消息.
     *
     * @param code           消息代码（业务状态码，对应 properties 文件中的 key）
     * @param defaultMessage 默认消息
     * @param args           消息参数
     * @return 国际化消息
     */
    public String getMessage(final String code, final String defaultMessage, final Object... args) {
        if (!i18nEnabled || messageSource == null) {
            return formatMessage(defaultMessage, args);
        }

        Locale locale = getLocale();
        try {
            return messageSource.getMessage(code, args, defaultMessage, locale);
        } catch (NoSuchMessageException e) {
            return formatMessage(defaultMessage, args);
        }
    }

    /**
     * 获取当前语言环境.
     *
     * @return 语言环境
     */
    protected Locale getLocale() {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return locale != null ? locale : defaultLocale;
        } catch (Exception e) {
            return defaultLocale;
        }
    }

    /**
     * 格式化消息（替换占位符）.
     *
     * @param message 消息模板
     * @param args    参数
     * @return 格式化后的消息
     */
    private String formatMessage(final String message, final Object... args) {
        if (message == null) {
            return "";
        }
        if (args == null || args.length == 0) {
            return message;
        }
        String result = message;
        for (int i = 0; i < args.length; i++) {
            result = result.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return result;
    }
}

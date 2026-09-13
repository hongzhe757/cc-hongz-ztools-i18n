package cc.hongz.ztools.i18n;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link I18nMessageResolver#parseAcceptLanguage(String)} 单元测试.
 */
@DisplayName("Accept-Language 解析测试")
class I18nMessageResolverTest {

    @Test
    @DisplayName("标准头取最高优先级")
    void parseAcceptLanguage_returnsFirstPriority() {
        Locale locale = I18nMessageResolver.parseAcceptLanguage("zh-CN,zh;q=0.9,en;q=0.8");
        assertEquals(Locale.forLanguageTag("zh-CN"), locale);
    }

    @Test
    @DisplayName("单语言标签")
    void parseAcceptLanguage_singleTag() {
        Locale locale = I18nMessageResolver.parseAcceptLanguage("en");
        assertEquals(Locale.forLanguageTag("en"), locale);
    }

    @Test
    @DisplayName("null 回退默认 Locale")
    void parseAcceptLanguage_null() {
        Locale locale = I18nMessageResolver.parseAcceptLanguage(null);
        assertEquals(Locale.getDefault(), locale);
    }

    @Test
    @DisplayName("空串回退默认 Locale")
    void parseAcceptLanguage_blank() {
        Locale locale = I18nMessageResolver.parseAcceptLanguage("   ");
        assertEquals(Locale.getDefault(), locale);
    }

    @Test
    @DisplayName("带通配符跳过取下一个")
    void parseAcceptLanguage_skipWildcard() {
        Locale locale = I18nMessageResolver.parseAcceptLanguage("*,en;q=0.8");
        assertEquals(Locale.forLanguageTag("en"), locale);
    }
}

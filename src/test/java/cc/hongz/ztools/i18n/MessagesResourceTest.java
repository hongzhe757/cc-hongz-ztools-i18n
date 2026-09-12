package cc.hongz.ztools.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * classpath 消息资源文件（messages_zh_CN / messages_en_US）加载与内容校验.
 */
@DisplayName("消息资源文件测试")
class MessagesResourceTest {

    private ResourceBundleMessageSource messageSource;

    @BeforeEach
    void setUp() {
        messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(false);
    }

    @Test
    @DisplayName("中文 - COMMON_000")
    void testChineseCommonSuccess() {
        String message = messageSource.getMessage("COMMON_000", null, Locale.SIMPLIFIED_CHINESE);
        assertEquals("操作成功", message);
    }

    @Test
    @DisplayName("英文 - COMMON_000")
    void testEnglishCommonSuccess() {
        String message = messageSource.getMessage("COMMON_000", null, Locale.US);
        assertEquals("Operation successful", message);
    }

    @Test
    @DisplayName("中文 - COMMON_400 参数替换")
    void testChineseParamInvalidWithArgs() {
        String message = messageSource.getMessage(
                "COMMON_400", new Object[]{"username"}, Locale.SIMPLIFIED_CHINESE);
        assertEquals("参数无效：username", message);
    }

    @Test
    @DisplayName("英文 - COMMON_400 参数替换")
    void testEnglishParamInvalidWithArgs() {
        String message = messageSource.getMessage(
                "COMMON_400", new Object[]{"username"}, Locale.US);
        assertEquals("Invalid parameter: username", message);
    }

    @Test
    @DisplayName("中文 - COMMON_404")
    void testChineseNotFound() {
        String message = messageSource.getMessage("COMMON_404", null, Locale.SIMPLIFIED_CHINESE);
        assertEquals("资源不存在", message);
    }

    @Test
    @DisplayName("英文 - COMMON_404")
    void testEnglishNotFound() {
        String message = messageSource.getMessage("COMMON_404", null, Locale.US);
        assertEquals("Resource not found", message);
    }

    @Test
    @DisplayName("中文 - COMMON_409 参数替换")
    void testChineseConflictWithArgs() {
        String message = messageSource.getMessage(
                "COMMON_409", new Object[]{"用户名已存在"}, Locale.SIMPLIFIED_CHINESE);
        assertEquals("资源冲突：用户名已存在", message);
    }

    @Test
    @DisplayName("英文 - COMMON_409 参数替换")
    void testEnglishConflictWithArgs() {
        String message = messageSource.getMessage(
                "COMMON_409", new Object[]{"username exists"}, Locale.US);
        assertEquals("Resource conflict: username exists", message);
    }
}

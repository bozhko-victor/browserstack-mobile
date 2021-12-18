package tests;

import config.CredentialsConfig;
import io.appium.java_client.MobileBy;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("selenide_android")
public class BrowserStackAndroidSelenideTests extends TestBase {

    public static CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);

    @Test
    @DisplayName("Successful search")
    void searchTest() {
        step("Type search", () -> {
            $(MobileBy.AccessibilityId("Search Wikipedia")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).val("BrowserStack");
        });
        step("Verify content found", () ->
                $$(MobileBy.id("org.wikipedia.alpha:id/page_list_item_container"))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @DisplayName("Text translation")
    void changeTextLanguageTest() {
        step("Type search", () -> {
            $(MobileBy.AccessibilityId("Search Wikipedia")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/search_src_text")).setValue("Michael Jackson");
        });

        step("Click first result of search", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/view_card_header_title")).click();
        });

        step("Select more options - change language", () -> {
            $x("//*[@content-desc='More options']").click();
            $x("//android.widget.TextView[@text='Change language']").click();
        });

        step("Select russian language", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/langlinks_filter")).setValue("russian");
            $(MobileBy.id("org.wikipedia.alpha:id/localized_language_name")).shouldHave(text("Русский"))
                    .click();
        });

        step("Check that the text has changed to Russian", () ->
                $(MobileBy.id("org.wikipedia.alpha:id/view_page_title_text")).shouldHave(text("Джексон, Майкл")
        ));
    }

    @Test
    @DisplayName("Successful login")
    void logInTest() {
        step("Open Menu", () ->
            $(MobileBy.id("org.wikipedia.alpha:id/menu_overflow_button")).click());

        step("Go to page 'Log in to Wikipedia'", () ->
                $(MobileBy.id("org.wikipedia.alpha:id/explore_overflow_account_avatar")).click());

        step("Set username", () ->
                $(MobileBy.id("org.wikipedia.alpha:id/login_username_text")).setValue(config.login()));

        step("Set password", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/login_password_input")).click();
            $$(MobileBy.className("TextInputLayout")).get(1).
                    $(MobileBy.className("android.widget.EditText")).setValue(config.password());
        });

        step("Click 'Log in'", () ->
                $(MobileBy.id("org.wikipedia.alpha:id/login_button")).click());

        step("Check that the username is displayed in the menu", () -> {
            $(MobileBy.id("org.wikipedia.alpha:id/menu_overflow_button")).click();
            $(MobileBy.id("org.wikipedia.alpha:id/explore_overflow_account_name")).
                    shouldHave(text(config.login()));
        });
    }

}
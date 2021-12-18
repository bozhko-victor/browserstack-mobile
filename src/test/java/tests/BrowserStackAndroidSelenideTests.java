package tests;

import io.appium.java_client.MobileBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("selenide_android")
public class BrowserStackAndroidSelenideTests extends TestBase {

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

        step("Check that language change", () ->
                $(MobileBy.id("org.wikipedia.alpha:id/view_page_title_text")).shouldHave(text("Джексон, Майкл")));
    }

}
package ru.netology.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VerificationPage {
    private final Page page;
    private final Locator codeField;
    private final Locator verifyButton;
    private final Locator successElement;

    public VerificationPage(Page page) {
        this.page = page;
        this.codeField = page.locator("[data-test-id=code] input");
        this.verifyButton = page.locator("[data-test-id=action-verify]");
        this.successElement = page.locator("[data-test-id=dashboard]");
    }

    public void verifyVerificationPageVisibility() {
        codeField.waitFor();
    }

    public void verifyErrorNotificationVisibility() {
        Locator errorNotification = page.locator("[data-test-id=error-notification]");
        errorNotification.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        assertThat(errorNotification).isVisible();
    }

    public void validVerify(String verificationCode) {
        codeField.fill(verificationCode);
        verifyButton.click();
    }

    public void verifySuccessLogin() {
        successElement.waitFor();
    }

    public void setEmptyField() {
        Locator errorNotification = page.locator(".input__sub:has-text('Поле обязательно для заполнения')");
        errorNotification.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        assertThat(errorNotification).isVisible();
    }
    }
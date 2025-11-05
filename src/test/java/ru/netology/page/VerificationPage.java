package ru.netology.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

public class VerificationPage {
    private final Page page;
    private final Locator codeField;
    private final Locator verifyButton;
    private final Locator errorNotification;
    private final Locator successElement;

    public VerificationPage(Page page) {
        this.page = page;
        this.codeField = page.locator("[data-test-id=code] input");
        this.verifyButton = page.locator("[data-test-id=action-verify]");
        this.errorNotification = page.locator("[data-test-id=error-notification]");
        this.successElement = page.locator("[data-test-id=dashboard]");
    }

    public void verifyVerificationPageVisibility() {
        codeField.waitFor(); // Проверяет, что поле кода видимо
    }

    public void validVerify(String verificationCode) {
        codeField.fill(verificationCode);
        verifyButton.click();
    }

    public void verifySuccessLogin() {
        // Проверяем, что мы успешно вошли в систему
        // Это может быть проверка URL или появления элемента главной страницы
        successElement.waitFor();

    }
    }
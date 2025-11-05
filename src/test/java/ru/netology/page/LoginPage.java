package ru.netology.page;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import ru.netology.data.DataHelper.AuthInfo;

public class LoginPage {
    private final Page page;
    private final Locator loginField;
    private final Locator passwordField;
    private final Locator continueButton;

    public LoginPage(Page page) {
        this.page = page;
        this.loginField = page.locator("[data-test-id=login] input");
        this.passwordField = page.locator("[data-test-id=password] input");
        this.continueButton = page.locator("[data-test-id=action-login]");
    }

    public VerificationPage validLogin(AuthInfo authInfo) {
        loginField.fill(authInfo.getLogin());
        passwordField.fill(authInfo.getPassword());
        continueButton.click();
        return new VerificationPage(page);
    }
}
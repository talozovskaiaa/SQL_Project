package ru.netology.test;

import org.junit.jupiter.api.*;
import ru.netology.core.TestBase;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static ru.netology.data.SQLHelper.cleanAuthCodes;
import static ru.netology.data.SQLHelper.cleanDatabase;

public class BankLoginTest extends TestBase {

    private LoginPage loginPage;

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(page);
        page.navigate("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        cleanAuthCodes();
    }

    @Test
    @DisplayName("Позитивный тест - успешный логин и верификация")
    void shouldSuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
        verificationPage.verifySuccessLogin();
    }

    @Test
    @DisplayName("Негативный тест - корректный логин и невалидный пароль")
    void shouldUnsuccessfulLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        loginPage.invalidPassword(authInfo, "1234");
        verificationPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Негативный тест - корректный пароль и невалидный логин")
    void shouldUnsuccessfulPassword() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        loginPage.invalidLogin(authInfo, "test");
        verificationPage.verifyErrorNotificationVisibility();
    }

    @Test
    @DisplayName("Негативный тест - корректный логин и пустой пароль")
    void shouldEmptyLogin() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        loginPage.invalidPassword(authInfo, "");
        verificationPage.setEmptyField();
    }

    @Test
    @DisplayName("Негативный тест - корректный пароль и пустой логин")
    void shouldEmptyPassword() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        loginPage.invalidLogin(authInfo, "");
        verificationPage.setEmptyField();
    }
}
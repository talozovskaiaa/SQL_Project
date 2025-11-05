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
}
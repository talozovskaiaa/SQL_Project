package ru.netology.page;

import com.microsoft.playwright.*;
import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class MainPage {

    Playwright playwright;
    Browser browser;
    Page page;

    public static void setupAllureReports() {
        System.setProperty("allure.results.directory", "build/allure-results");
    }

    public static void tearDownAllureReports() {
    }

    // Метод для сохранения HTML страницы
    public void savePageSource(String pageName) {
        if (page != null) {
            try {
                String pageContent = page.content();
                Allure.addAttachment(pageName + ".html", "text/html",
                        new ByteArrayInputStream(pageContent.getBytes()), "html");
            } catch (Exception e) {
                System.err.println("Не удалось сохранить HTML страницы: " + e.getMessage());
            }
        }
    }


    public void tearDown() {
        // Сохраняем HTML страницы перед закрытием (если нужно)
        savePageSource("final-page-state");

        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
        tearDownAllureReports();
    }
}
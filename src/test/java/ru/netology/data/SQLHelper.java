package ru.netology.data;

import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {}

    @Value
    public static class VerificationCode {
        private String code;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver not found", e);
        }
    }

    // Возвращает код
    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()) {
            String code = QUERY_RUNNER.query(conn, codeSQL, new ScalarHandler<String>());
            if (code == null) {
                throw new RuntimeException("Verification code not found in database");
            }
            System.out.println("Found verification code: " + code);
            return code;
        }
    }

    // Очищает БД
    @SneakyThrows
    public static void cleanDatabase() {
        try (var connection = getConn()) {
            QUERY_RUNNER.execute(connection, "SET FOREIGN_KEY_CHECKS = 0");
            QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
            QUERY_RUNNER.execute(connection, "DELETE FROM card_transactions");
            QUERY_RUNNER.execute(connection, "DELETE FROM cards");
            QUERY_RUNNER.execute(connection, "DELETE FROM users");
            QUERY_RUNNER.execute(connection, "SET FOREIGN_KEY_CHECKS = 1");
        }
    }

    // Очистка только кодов верификации
    @SneakyThrows
    public static void cleanAuthCodes() {
        try (var connection = getConn()) {
            QUERY_RUNNER.execute(connection, "DELETE FROM auth_codes");
        }
    }

    private static Connection getConn() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/app?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "app";
        String password = "pass";

        System.out.println("Connecting to: " + url + " as user: " + user);
        return DriverManager.getConnection(url, user, password);
    }
}
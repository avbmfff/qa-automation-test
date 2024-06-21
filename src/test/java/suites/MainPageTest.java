package suites;

import initialization.Base;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tests.InputNegativeData;
import tests.InputPositiveData;

import static com.codeborne.selenide.Selenide.open;
import static methods.GetProperty.getProperty;

/*
Данный сьют проверяет корректность работы страницы входа
 */

public class MainPageTest extends Base {

    String login = getProperty("login");
    String password = getProperty("password");

    @BeforeEach
    public void openPage() {
        open(getProperty("qa_page"));
    }

    @ParameterizedTest(name = "Проверка поля e-mail и пароль: {index} - {2}")
    @CsvSource(value = {
            "оставить_пустым, 123456789, Неверный формат E-Mail",
            "abcdefg123456789, оставить_пустым, Неверный формат E-Mail",
            "abcdefg123456789.ru, оставить_пустым, Неверный формат E-Mail",
            "@.ru, оставить_пустым, Неверный формат E-Mail",
            "@%.ru, оставить_пустым, Неверный формат E-Mail",
            "a@1@.ru, оставить_пустым, Неверный E-Mail или пароль",
            "123abc@.ru, 123abc, Неверный E-Mail или пароль"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("Проверка работоспособности страницы входа")
    @Feature("Негативный")
    public void mainPageNegative(String login, String password, String error) {

        String enterLogin = (login.equals("оставить_пустым")) ? "" : login;
        String enterPassword = (password.equals("оставить_пустым")) ? "" : password;

        new InputNegativeData().inputNegativeData(enterLogin, enterPassword, error);
    }

    @Test
    @Story("Проверка работоспособности страницы входа")
    @Feature("Позитивный")
    public void mainPagePositive() {
        new InputPositiveData().inputPositiveData(login, password);
    }

}

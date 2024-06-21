package tests;

/*
Тест направлен на проверку корректности перехода на страницу анкеты
 */

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import simple.CheckMainPageInterface;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

public class InputPositiveData {
    public void inputPositiveData(String login, String password) {
        step_1();
        step_2(login, password);
        step_3();
    }

    /*
    Описание шага: Выполнить CheckMainPageInterface
    Ожидаемый результат: CheckMainPageInterface выполнен
     */

    @Step("Step 1: Выполнить CheckMainPageInterface")
    private void step_1() {
        new CheckMainPageInterface().checkMainPageInterface();

    }

    /*
    Описание шага: В поле E-mail заполнить значением {login}, Поле пароль заполнить значением {password}
    Ожидаемый результат: Поле E-mail заполнено значением {login}, Поле пароль заполнено значением {password}
     */

    @Step("Step 2: Выполнить CheckMainPageInterface")
    private void step_2(String login, String password) {
        $(By.id("loginEmail")).setValue(login);
        $(By.id("loginPassword")).setValue(password);

        // для "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals(login, $(By.id("loginEmail")).shouldBe(visible).getValue(), "Значение в поле под заголовком \"E-Mail:\" не соответсвует ожидаемому"));

        // для "Пароль:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals(password, $(By.id("loginPassword")).shouldBe(visible).getValue(), "Значение в поле под заголовком \"Пароль:\" не соответсвует ожидаемому"));

    }


    /*
    Описание шага: Нажать на кнопку "Вход"
    Ожидаемый результат: Страница входа не отображена
     */

    @Step("Step 3: Нажать на кнопку Вход")
    private void step_3() {
        $(By.id("authButton")).click();

        assertDoesNotThrow(() -> $(By.id("authPage")).shouldNotBe(visible));
    }
}

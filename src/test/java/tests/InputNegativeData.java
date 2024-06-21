package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import simple.CheckMainPageInterface;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Тест направлен на проверку корректности высвечиваемых ошибок при заполнении полей 'E-mail' и 'Пароль'
 */
public class InputNegativeData {

    public void inputNegativeData(String login, String password, String error) {
        step_1();
        step_2(login, password);
        step_3(error);
        step_4(error);
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

    @Step("Step 2: В поле E-mail заполнить значением {login}, Поле пароль заполнить значением {password}")
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
    Ожидаемый результат: Высвечена ошибка {error}
     */

    @Step("Step 3: Нажать на кнопку 'Вход'")
    private void step_3(String error) {
        $(By.id("authButton")).click();
        assertAll(
                () -> $(By.id(getId(error))).shouldBe(visible),
                () -> assertEquals(error, $(By.id(getId(error))).getText(), "Сообщение об ошибке не соответсвует ожидаемому"));
    }

    /*
    Описание шага: Нажать на "х" рядом с сообщением об ошибке
    Ожидаемый результат: Сообщение об ошибке закрыто и не присутсвует на странице
     */
    @Step("Step 4: Нажать на \"х\" рядом с сообщением об ошибке")
    private void step_4(String error) {
        $(".uk-alert-close.uk-close").shouldBe(visible, enabled).click();

        assertAll(
                () -> $(By.id(getId(error))).shouldNotBe(exist, visible));
    }

    private String getId(String error){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Неверный формат E-Mail", "emailFormatError");
        errorMap.put("Неверный E-Mail или пароль", "invalidEmailPassword");

        return errorMap.get(error);
    }

}

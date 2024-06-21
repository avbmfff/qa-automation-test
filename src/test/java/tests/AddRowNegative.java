package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import simple.CheckFormPageInterface;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static org.junit.jupiter.api.Assertions.*;

/*
Тест направлен на проверку корректности высвечиваемых ошибок при заполнении полей 'E-mail' и 'Имя'
 */
public class AddRowNegative {

    public void addRowNegative(String email, String name, String error) {
        step_1();
        step_2(email, name);
        step_3(error);
        step_4(error);
    }

    /*
    Описание шага: Выполнить CheckFormPageInterface
    Ожидаемый результат: CheckFormPageInterface выполен
     */
    @Step("Step 1: Выполнить CheckFormPageInterface")
    public void step_1() {
        new CheckFormPageInterface().checkFormPageInterface();
    }

    /*
    Описание шага: Ввести данные. В поле E-Mail: {email}, в поле Имя: {name}
    Ожидаемый результат: Поле E-Mail: заполнено {email}, в поле Имя: заполнено {name}
     */
    @Step("Step 2: Ввести данные. В поле E-Mail: {email}, в поле Имя: {name}")
    public void step_2(String email, String name) {
        $(By.id("dataEmail")).setValue(email);
        $(By.id("dataName")).setValue(name);

        assertAll(
                () -> assertEquals(email, $(By.id("dataEmail")).getValue(), "Значение в поле E-Mail: не соответствует ожидаемому"),
                () -> assertEquals(name, $(By.id("dataName")).getValue(), "Значение в поле Имя: не соответствует ожидаемому")
        );

    }


    /*
    Описание шага: Нажать на кнопку "Добавить"
    Ожидаемый результат: Высвечено сообщение об ошибке {error}. Таблица с колонками E-Mail	Имя	Пол	Выбор 1	Выбор 2 пуста
     */
    @Step("Step 3: Нажать на кнопку \"Добавить\"")
    public void step_3(String error) {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(By.id("dataTable")).$("tbody").shouldBe(empty),
                () -> $(By.id(getId(error))).shouldBe(visible),
                () -> assertEquals(error, $(By.id(getId(error))).getText(), "Сообщение об ошибке не соответсвует ожидаемому"));
    }


    /*
    Описание шага: Перезагрузить страницу
    Ожидаемый результат: Сообщение об ошибке отсутствует. Отображена страница авторизация
     */
    @Step("Step 4: Перезагрузить страницу")
    public void step_4(String error) {
        refresh();

        assertAll(
                () -> $(By.id("authPage")).shouldBe(visible),
                () -> $(By.id(getId(error))).shouldNotBe(exist, visible));
    }

    private String getId(String error) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("Неверный формат E-Mail", "emailFormatError");
        errorMap.put("Поле имя не может быть пустым", "blankNameError");

        return errorMap.get(error);
    }
}

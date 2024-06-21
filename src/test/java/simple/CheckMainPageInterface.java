package simple;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/*
Тест направлен на проверку интерфейса страницы входа
 */
public class CheckMainPageInterface {

    public void checkMainPageInterface() {
        step_1();
        step_2();
        step_3();
    }

    /*
    Описание шага: Перейти на главную страницу
    Ожидаемый результат: Присутсвует Заголовок "E-Mail:", под ним текстовое поле ввода не заполнено, доступно для редактирования;
    Присутсвует Заголовок "Пароль:", под ним текстовое поле ввода не заполнено, доступно для редактирования;
    Присутсвует Кнопка с текстом "Вход"
     */
    @Step("Step 1: Перейти на главную страницу")
    private void step_1() {

        // для "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='loginEmail']").shouldBe(visible).getText(), "Заголовок E-mail: был переименован"),
                () -> assertFalse($(By.id("loginEmail")).is(readonly), "Поле под заголовком \"E-Mail:\" не доступно для редактирования"),
                () -> assertEquals("", $(By.id("loginEmail")).getValue(), "Поле под заголовком \"E-Mail:\" заполнено"));

        // для "Пароль:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals("Пароль:", $("label[for='loginPassword']").shouldBe(visible).getText(), "Заголовок Пароль: был переименован"),
                () -> assertFalse($(By.id("loginPassword")).is(readonly), "Поле под заголовком \"Пароль:\" не доступно для редактирования"),
                () -> assertEquals("", $(By.id("loginPassword")).getValue(), "Поле под заголовком \"Пароль:\" заполнено"));

        // для кнопки "Войти"
        assertAll(
                () -> $(By.id("authButton")).shouldBe(visible, enabled),
                () -> assertEquals("Вход", $(By.id("authButton")).getText(), "Кнопка Вход была переименована"));
    }

    /*
    Описание шага: Нажать на кнопку с текстом "Вход"
    Ожидаемый результат: Появилось сообщение об ошибке с текстом "Неверный формат E-Mail"
     */
    @Step("Step 2: Нажать на кнопку с текстом \"Вход\"")
    private void step_2() {
        $(By.id("authButton")).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldBe(visible),
                () -> assertEquals("Неверный формат E-Mail", $(By.id("emailFormatError")).getText(), "Сообщение об ошибке не соответсвует ожидаемому"));
    }

    /*
    Описание шага: Перезагрузить страницу
    Ожидаемый результат: Сообщение об ошибке с текстом "Неверный формат E-Mail" отсутсвует.
    Присутсвует Заголовок "E-Mail:", под ним текстовое поле ввода не заполнено;
    Присутсвует Заголовок "Пароль:", под ним текстовое поле ввода не заполнено;
    Присутсвует Кнопка с текстом "Вход"
     */
    @Step("Step 3: Перезагрузить страницу")
    private void step_3() {
        refresh();

        // для сообщения об ошибке
        assertAll(
                () -> $(By.id("emailFormatError")).shouldNotBe(exist));

        // для "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='loginEmail']").shouldBe(visible).getText(), "Заголовок E-mail: был переименован"),
                () -> assertEquals("", $(By.id("loginEmail")).getValue(), "Поле под заголовком \"E-Mail:\" заполнено"));

        // для "Пароль:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals("Пароль:", $("label[for='loginPassword']").shouldBe(visible).getText(), "Заголовок Пароль: был переименован"),
                () -> assertEquals("", $(By.id("loginPassword")).getValue(), "Поле под заголовком \"Пароль:\" заполнено"));

        // для кнопки "Войти"
        assertAll(
                () -> $(By.id("authButton")).shouldBe(visible, enabled),
                () -> assertEquals("Вход", $(By.id("authButton")).getText(), "Кнопка Вход была переименована"));
    }
}

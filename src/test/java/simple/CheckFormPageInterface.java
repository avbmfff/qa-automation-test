package simple;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;
import static methods.GetProperty.getProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
Тест направлен на проверку интерфейса страницы формы
 */
public class CheckFormPageInterface {

    String login = getProperty("login");
    String password = getProperty("password");

    public void checkFormPageInterface() {
        step_1();
        step_2();
        step_3();
        step_4();
        step_5();
        step_6();
    }

    /*
    Описание шага: Провести авторизацию
    Ожидаемый результат: Отображена страница формы
     */
    @Step("Step 1: Провести авторизацию")
    private void step_1() {
        $(By.id("loginEmail")).setValue(login);
        $(By.id("loginPassword")).setValue(password);
        $(By.id("authButton")).shouldBe(visible).click();

        assertDoesNotThrow(() -> $(By.id("authPage")).shouldNotBe(visible));
    }

    /*
    Описание шага: Отображена страница формы
    Ожидаемый результат: На страницы отображены следующие элементы:
    Заголовок E-Mail: под ним текстовое поле: не заполнено, доступно для редактирования
    Заголовок Имя: под ним текстовое поле: не заполнено, доступно для редактирования
    Заголовок Пол: под ним поле выбора: по умолчанию заполнено значением Мужской,
    возможен выбор из двух вариантов по нажатию на v: Мужской, Женский

    Поле с двумя вариантами чекбокса: Вариант 1.1, Вариант 1.2. Ни один из вариантов не выбран
    Поле с тремя вариантами радиокнопок: Вариант 2.1, Вариант 2.2,  Вариант 2.3

    Кнопка Добавить. Доступна для нажатия

    Под кнопкной расположена пустая таблица с колонками: E-Mail	Имя	Пол	Выбор 1	Выбор 2

     */
    @Step("Step 2: Отображена страница формы")
    private void step_2() {

        // для "E-Mail:"
        assertAll(
                () -> $(By.id("dataEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='dataEmail']").shouldBe(visible).getText(), "Заголовок E-mail: был переименован"),
                () -> assertEquals("", $(By.id("dataEmail")).getValue(), "Поле под заголовком \"E-Mail:\" заполнено"),
                () -> assertFalse($(By.id("dataEmail")).is(readonly), "Поле под заголовком \"E-Mail:\" не доступно для редактирования"));

        // для "Имя:"
        assertAll(
                () -> $(By.id("dataName")).shouldBe(visible),
                () -> assertEquals("Имя:", $("label[for='dataName']").shouldBe(visible).getText(), "Заголовок Имя: был переименован"),
                () -> assertEquals("", $(By.id("dataName")).getValue(), "Поле под заголовком \"Имя:\" заполнено"),
                () -> assertFalse($(By.id("dataName")).is(readonly), "Поле под заголовком \"Имя:\" не доступно для редактирования"));

        // для "Пол:"
        assertAll(
                () -> $(By.id("dataGender")).shouldBe(visible),
                () -> assertEquals("Пол:", $("label[for='dataGender']").shouldBe(visible).getText(), "Заголовок Пол: был переименован"),
                () -> assertEquals("Мужской", $(By.id("dataGender")).getText(), "Значение по умолчанию не соответствует ожидаемому"),
                () -> assertEquals(2, $(By.id("dataGender")).getOptions().size(), "Количество вариантов не соответствует ожидаемому"),
                () -> $(By.id("dataGender")).getOptions().first().shouldHave(text("Мужской")),
                () -> $(By.id("dataGender")).getOptions().last().shouldHave(text("Женский")));

        // для чекбокса
        assertAll(
                () -> $(By.id("dataCheck11")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("Вариант 1.1", $(By.id("dataCheck11")).parent().getText(), "Значение Вариант 1.1 не соответсвует ожидаемому"),
                () -> $(By.id("dataCheck12")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("Вариант 1.2", $(By.id("dataCheck12")).parent().getText(), "Значение Вариант 1.2 не соответсвует ожидаемому"));

        // для радиокнопок
        assertAll(
                () -> $(By.id("dataSelect21")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("Вариант 2.1", $(By.id("dataSelect21")).parent().getText(), "Значение Вариант 2.1 не соответсвует ожидаемому"),
                () -> $(By.id("dataSelect22")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("Вариант 2.2", $(By.id("dataSelect22")).parent().getText(), "Значение Вариант 2.2 не соответсвует ожидаемому"),
                () -> $(By.id("dataSelect23")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("Вариант 2.3", $(By.id("dataSelect23")).parent().getText(), "Значение Вариант 2.3 не соответсвует ожидаемому"));

        // для кнопки Добавить
        assertAll(
                () -> $(By.id("dataSend")).shouldBe(visible, enabled),
                () -> assertEquals("Добавить", $(By.id("dataSend")).getText(), "Значение Добавить не соответсвует ожидаемому"));

        // для таблицы
        assertAll(
                () -> $(By.id("dataTable")).shouldBe(visible),
                () -> $(By.id("dataTable")).$("thead").shouldBe(visible),
                () -> $(By.id("dataTable")).$("tbody").shouldBe(empty),
                () -> assertEquals("E-Mail", $(By.id("dataTable")).$("thead").$("th:nth-child(1)").getText(), "Название первой колонки не соответствует ожидаемому"),
                () -> assertEquals("Имя", $(By.id("dataTable")).$("thead").$("th:nth-child(2)").getText(), "Название второй колонки не соответствует ожидаемому"),
                () -> assertEquals("Пол", $(By.id("dataTable")).$("thead").$("th:nth-child(3)").getText(), "Название третьей колонки не соответствует ожидаемому"),
                () -> assertEquals("Выбор 1", $(By.id("dataTable")).$("thead").$("th:nth-child(4)").getText(), "Название четвертой колонки не соответствует ожидаемому"),
                () -> assertEquals("Выбор 2", $(By.id("dataTable")).$("thead").$("th:nth-child(5)").getText(), "Название пятой колонки не соответствует ожидаемому"));
    }

    /*
    Описание шага: Установить галочку напротив Вариант 1.1, Выбрать радиокнопку Вариант 2.1
    Ожидаемый результат: Напротив Вариант 1.1 установлена галочка, радиокнопка Вариант 2.1 выбрана
     */
    @Step("Step 3: Установить галочку напротив Вариант 1.1, Выбрать радиокнопку Вариант 2.1")
    private void step_3() {
        $(By.id("dataCheck11")).setSelected(true);

        assertAll(
                () -> $(By.id("dataCheck11")).shouldBe(selected)
        );
    }

    /*
   Описание шага: Убрать галочку напротив Вариант 1.1, убрать выбор радиокнопки Вариант 2.1
   Ожидаемый результат: Напротив Вариант 1.1 не установлена галочка, радиокнопка Вариант 2.1 выбрана
    */
    @Step("Step 4: Убрать галочку напротив Вариант 1.1, Ещё раз нажать на выбор радиокнопки Вариант 2.1")
    private void step_4() {

        $(By.id("dataCheck11")).setSelected(false);

        assertAll(
                () -> $(By.id("dataCheck11")).shouldNotBe(selected)
        );

    }

    /*
    Описание шага: Нажать на кнопку "Добавить"
    Ожидаемый результат: Высвечено сообщение об ошибке "Неверный формат E-Mail"
    */
    @Step("Step 5: Нажать на кнопку \"Добавить\"")
    private void step_5() {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldBe(visible),
                () -> assertEquals("Неверный формат E-Mail", $(By.id("emailFormatError")).getText(), "Сообщение об ошибке не соответсвует ожидаемому"));
    }


    /*
    Описание шага: Нажать на значок "х" рядом с сообщением об ошибке
    Ожидаемый результат: Сообщение об ошибке "Неверный формат E-Mail" закрыто
    */
    @Step("Step 6: Нажать на значок \"х\" рядом с сообщением об ошибке")
    private void step_6() {
        $(".uk-alert-close.uk-close").shouldBe(visible, enabled).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldNotBe(exist, visible));
    }


}

package suites;


import initialization.Base;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tests.AddRowNegative;
import tests.AddRowPositive;

import static com.codeborne.selenide.Selenide.open;
import static methods.GetProperty.getProperty;

/*
Данный сьют проверяет корректность работы страницы формы
 */
public class FormPageTest extends Base {

    @BeforeEach
    public void openPage() {
        open(getProperty("qa_page"));
    }

    @ParameterizedTest(name = "Проверка корректности заполнения формы: {index} - {2}")
    @CsvSource(value = {
            "оставить_пустым, 123456789, Неверный формат E-Mail",
            "abcdefg123456789, оставить_пустым, Неверный формат E-Mail",
            "abcdefg123456789.ru, оставить_пустым, Неверный формат E-Mail",
            "@.ru, оставить_пустым, Неверный формат E-Mail",
            "@%.ru, оставить_пустым, Неверный формат E-Mail",
            "@@.ru, оставить_пустым, Поле имя не может быть пустым",
            "a@1@.ru, оставить_пустым, Поле имя не может быть пустым"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("Проверка заполнения формы")
    @Feature("Негативный")
    public void addRowNegative(String email, String name, String error){
        String enterEmail = (email.equals("оставить_пустым")) ? "Нет" : email;
        String enterName = (name.equals("оставить_пустым")) ? "" : name;

        new AddRowNegative().addRowNegative(enterEmail, enterName, error);
    }

    @ParameterizedTest(name = "Проверка корректности добавления записей в таблицу: {index} - {2}")
    @CsvSource(value = {
            "@@.ru, 123456789, Не_выбирать, Не_выбирать, Не_выбирать",
            "a@1@.com, abcdef, Женский, Вариант 1.1, Вариант 2.1",
            "example@live.1, abc123, Мужской, Вариант 1.2, Вариант 2.2",
            "example@.1, ABCabc, Женский, Вариант 1.1 и Вариант 1.2, Вариант 2.3",
            "example@..., ABC123456, Мужской, Не_выбирать, Вариант 2.1",
            "example@..., !@\\#$%^&*()№;:?/|, Мужской, Вариант 1.1 и Вариант 1.2, Не_выбирать"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("Проверка заполнения формы")
    @Feature("Позитивный")
    public void addRowPositive(String email, String name, String gender, String checkbox, String radioButton){
        new AddRowPositive().addRowPositive(email, name, gender, checkbox, radioButton);
    }

}

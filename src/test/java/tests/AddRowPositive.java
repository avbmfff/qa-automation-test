package tests;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import simple.CheckFormPageInterface;

import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

/*
Тест направлен на проверку корректность добавления записей в таблицу
 */
public class AddRowPositive {

    public void addRowPositive(String email, String name, String gender, String checkbox, String radioButton) {
        step_1();
        step_2(email, name, gender, checkbox, radioButton);
        step_3();
        step_4(email, name, gender, checkbox, radioButton);
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
    Описание шага: Заполнить поля
    поле E-Mail: {email}
    поле Имя: {name}
    поле Пол: {gender}

    Поле с двумя вариантами чекбокса: {checkbox}
    Поле с тремя вариантами радиокнопок: {radioButton}

    Ожидаемый результат: Поля заполнены указанными значениями
     */
    @Step("Step 2: Заполнить поля")
    private void step_2(String email, String name, String gender, String checkbox, String radioButton) {
        $(By.id("dataEmail")).setValue(email);
        $(By.id("dataName")).setValue(name);
        selectGender(gender);
        markRadioButton(radioButton);
        markCheckbox(checkbox);


        assertAll(
                () -> assertEquals(email, $(By.id("dataEmail")).getValue(), "E-Mail не соответствует ожидаемому значению"),
                () -> assertEquals(name, $(By.id("dataName")).getValue(), "Имя не соответствует ожидаемому значению"),
                () -> assertEquals(getGenderText(gender), $(By.id("dataGender")).getSelectedOption().getText(), "Пол не соответствует ожидаемому значению"),
                () -> assertCheckbox(checkbox),
                () -> assertRadioButton(radioButton)
        );
    }

    /*
    Описание шага: Нажать кнопку Добавить
    Ожидаемый результат: Высвечено сообщение с надписью Данные добавлены c действующей кнопкой Ok.
     */
    @Step("Step 3: Нажать кнопку Добавить")
    private void step_3() {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(".uk-modal-dialog").shouldBe(visible),
                () -> assertEquals("Данные добавлены.", $(".uk-margin.uk-modal-content").shouldBe(visible).getText(), "Текст сообщения не соответствует ожидаемому"),
                () -> $(".uk-button.uk-button-primary.uk-modal-close").shouldBe(visible, enabled).shouldHave(text("Ok"))
        );
    }

    /*
    Описание шага: Нажать кнопку Ok на окне сообщения
    Ожидаемый результат: Окно сообщение закрыто. В таблице добавилась строка с введёнными значениями
     */
    @Step("Step 4: Нажать кнопку Ok на окне сообщения")
    private void step_4(String email, String name, String gender, String checkbox, String radioButton) {
        $(".uk-button.uk-button-primary.uk-modal-close").click();

        assertAll(
                () -> $(".uk-modal-dialog").shouldNotBe(visible, exist),
                () -> assertEquals(email, $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(1)").getText(), "E-Mail в таблице не соответствует ожидаемому"),
                () -> assertEquals(name, $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(2)").getText(), "Имя в таблице не соответствует ожидаемому"),
                () -> assertEquals(getGenderText(gender), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(3)").getText(), "Пол в таблице не соответствует ожидаемому"),
                () -> assertSelectedTableText(checkbox, radioButton));
    }

    private String getId(String error) {

        Map<String, String> idMap = new HashMap<>();
        idMap.put("Вариант 1.1", "dataCheck11");
        idMap.put("Вариант 1.2", "dataCheck12");
        idMap.put("Вариант 2.1", "dataSelect21");
        idMap.put("Вариант 2.2", "dataSelect22");
        idMap.put("Вариант 2.3", "dataSelect23");

        return idMap.get(error);
    }

    private void markCheckbox(String checkbox) {
        if (checkbox.equals("Вариант 1.1 и Вариант 1.2")) {
            String[] variants = checkbox.split(" и ");
            for (String var :
                    variants) {
                $(By.id(getId(var))).setSelected(true);
            }
        }
        if (checkbox.equals("Вариант 1.1") || checkbox.equals("Вариант 1.2")) {
            $(By.id(getId(checkbox))).setSelected(true);
        }
    }

    private void assertCheckbox(String checkbox) {
        if (checkbox.equals("Вариант 1.1 и Вариант 1.2")) {
            String[] variants = checkbox.split(" и ");
            for (String var :
                    variants) {
                assertTrue($(By.id(getId(var))).isSelected(), "Чекбокс " + var + "не выбран");
            }
        } else if (!checkbox.equals("Не_выбирать")) {
            assertTrue($(By.id(getId(checkbox))).isSelected(), "Чекбокс " + checkbox + "не выбран");
        }
    }

    private void markRadioButton(String radioButton) {
        if (!radioButton.equals("Не_выбирать")) {
            $(By.id(getId(radioButton))).setSelected(true);
        }
    }

    private void assertRadioButton(String radioButton) {
        if (!radioButton.equals("Не_выбирать")) {
            assertTrue($(By.id(getId(radioButton))).isSelected(), "Радиокнопка " + radioButton + "не выбран");
        }
    }

    private void selectGender(String gender) {
        if (!gender.equals("Не_выбирать")) {
            $(By.id("dataGender")).selectOption(gender);
        }
    }

    private String getGenderText(String gender) {
        return (gender.equals("Не_выбирать")) ? "Мужской" : gender;
    }

    private String getSelectedTableText(String select) {
        String[] parts = select.split("\\s+");
        return parts[1];
    }

    private void assertSelectedTableText(String checkbox, String radioButton) {

        if (checkbox.equals("Не_выбирать")) {
            assertEquals("Нет", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "Значение чекбокса в таблице не соответствует ожидаемому");
        }

        if (checkbox.equals("Вариант 1.1 и Вариант 1.2")) {
            assertEquals("1.1, 1.2", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "Значение чекбокса в таблице не соответствует ожидаемому");
        }

        if (radioButton.equals("Не_выбирать")) {
            assertEquals("", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(5)").getText(), "Значение радиокнопки в таблице не соответствует ожидаемому");
        }
        if (!checkbox.equals("Вариант 1.1 и Вариант 1.2") && !radioButton.equals("Не_выбирать") && !checkbox.equals("Не_выбирать")) {
            assertAll(
                    () -> assertEquals(getSelectedTableText(checkbox), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "Значение чекбокса в таблице не соответствует ожидаемому"),
                    () -> assertEquals(getSelectedTableText(radioButton), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(5)").getText(), "Значение радиокнопки в таблице не соответствует ожидаемому")
            );
        }
    }
}

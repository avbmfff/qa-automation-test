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
���� ��������� �� �������� ������������ ���������� ������� � �������
 */
public class AddRowPositive {

    public void addRowPositive(String email, String name, String gender, String checkbox, String radioButton) {
        step_1();
        step_2(email, name, gender, checkbox, radioButton);
        step_3();
        step_4(email, name, gender, checkbox, radioButton);
    }

    /*
    �������� ����: ��������� CheckFormPageInterface
    ��������� ���������: CheckFormPageInterface �������
     */
    @Step("Step 1: ��������� CheckFormPageInterface")
    public void step_1() {
        new CheckFormPageInterface().checkFormPageInterface();
    }


    /*
    �������� ����: ��������� ����
    ���� E-Mail: {email}
    ���� ���: {name}
    ���� ���: {gender}

    ���� � ����� ���������� ��������: {checkbox}
    ���� � ����� ���������� �����������: {radioButton}

    ��������� ���������: ���� ��������� ���������� ����������
     */
    @Step("Step 2: ��������� ����")
    private void step_2(String email, String name, String gender, String checkbox, String radioButton) {
        $(By.id("dataEmail")).setValue(email);
        $(By.id("dataName")).setValue(name);
        selectGender(gender);
        markRadioButton(radioButton);
        markCheckbox(checkbox);


        assertAll(
                () -> assertEquals(email, $(By.id("dataEmail")).getValue(), "E-Mail �� ������������� ���������� ��������"),
                () -> assertEquals(name, $(By.id("dataName")).getValue(), "��� �� ������������� ���������� ��������"),
                () -> assertEquals(getGenderText(gender), $(By.id("dataGender")).getSelectedOption().getText(), "��� �� ������������� ���������� ��������"),
                () -> assertCheckbox(checkbox),
                () -> assertRadioButton(radioButton)
        );
    }

    /*
    �������� ����: ������ ������ ��������
    ��������� ���������: ��������� ��������� � �������� ������ ��������� c ����������� ������� Ok.
     */
    @Step("Step 3: ������ ������ ��������")
    private void step_3() {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(".uk-modal-dialog").shouldBe(visible),
                () -> assertEquals("������ ���������.", $(".uk-margin.uk-modal-content").shouldBe(visible).getText(), "����� ��������� �� ������������� ����������"),
                () -> $(".uk-button.uk-button-primary.uk-modal-close").shouldBe(visible, enabled).shouldHave(text("Ok"))
        );
    }

    /*
    �������� ����: ������ ������ Ok �� ���� ���������
    ��������� ���������: ���� ��������� �������. � ������� ���������� ������ � ��������� ����������
     */
    @Step("Step 4: ������ ������ Ok �� ���� ���������")
    private void step_4(String email, String name, String gender, String checkbox, String radioButton) {
        $(".uk-button.uk-button-primary.uk-modal-close").click();

        assertAll(
                () -> $(".uk-modal-dialog").shouldNotBe(visible, exist),
                () -> assertEquals(email, $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(1)").getText(), "E-Mail � ������� �� ������������� ����������"),
                () -> assertEquals(name, $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(2)").getText(), "��� � ������� �� ������������� ����������"),
                () -> assertEquals(getGenderText(gender), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(3)").getText(), "��� � ������� �� ������������� ����������"),
                () -> assertSelectedTableText(checkbox, radioButton));
    }

    private String getId(String error) {

        Map<String, String> idMap = new HashMap<>();
        idMap.put("������� 1.1", "dataCheck11");
        idMap.put("������� 1.2", "dataCheck12");
        idMap.put("������� 2.1", "dataSelect21");
        idMap.put("������� 2.2", "dataSelect22");
        idMap.put("������� 2.3", "dataSelect23");

        return idMap.get(error);
    }

    private void markCheckbox(String checkbox) {
        if (checkbox.equals("������� 1.1 � ������� 1.2")) {
            String[] variants = checkbox.split(" � ");
            for (String var :
                    variants) {
                $(By.id(getId(var))).setSelected(true);
            }
        }
        if (checkbox.equals("������� 1.1") || checkbox.equals("������� 1.2")) {
            $(By.id(getId(checkbox))).setSelected(true);
        }
    }

    private void assertCheckbox(String checkbox) {
        if (checkbox.equals("������� 1.1 � ������� 1.2")) {
            String[] variants = checkbox.split(" � ");
            for (String var :
                    variants) {
                assertTrue($(By.id(getId(var))).isSelected(), "������� " + var + "�� ������");
            }
        } else if (!checkbox.equals("��_��������")) {
            assertTrue($(By.id(getId(checkbox))).isSelected(), "������� " + checkbox + "�� ������");
        }
    }

    private void markRadioButton(String radioButton) {
        if (!radioButton.equals("��_��������")) {
            $(By.id(getId(radioButton))).setSelected(true);
        }
    }

    private void assertRadioButton(String radioButton) {
        if (!radioButton.equals("��_��������")) {
            assertTrue($(By.id(getId(radioButton))).isSelected(), "����������� " + radioButton + "�� ������");
        }
    }

    private void selectGender(String gender) {
        if (!gender.equals("��_��������")) {
            $(By.id("dataGender")).selectOption(gender);
        }
    }

    private String getGenderText(String gender) {
        return (gender.equals("��_��������")) ? "�������" : gender;
    }

    private String getSelectedTableText(String select) {
        String[] parts = select.split("\\s+");
        return parts[1];
    }

    private void assertSelectedTableText(String checkbox, String radioButton) {

        if (checkbox.equals("��_��������")) {
            assertEquals("���", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "�������� �������� � ������� �� ������������� ����������");
        }

        if (checkbox.equals("������� 1.1 � ������� 1.2")) {
            assertEquals("1.1, 1.2", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "�������� �������� � ������� �� ������������� ����������");
        }

        if (radioButton.equals("��_��������")) {
            assertEquals("", $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(5)").getText(), "�������� ����������� � ������� �� ������������� ����������");
        }
        if (!checkbox.equals("������� 1.1 � ������� 1.2") && !radioButton.equals("��_��������") && !checkbox.equals("��_��������")) {
            assertAll(
                    () -> assertEquals(getSelectedTableText(checkbox), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(4)").getText(), "�������� �������� � ������� �� ������������� ����������"),
                    () -> assertEquals(getSelectedTableText(radioButton), $(By.id("dataTable")).$("tbody").$("tr:last-child").$("td:nth-child(5)").getText(), "�������� ����������� � ������� �� ������������� ����������")
            );
        }
    }
}

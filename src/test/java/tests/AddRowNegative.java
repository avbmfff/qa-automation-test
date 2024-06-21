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
���� ��������� �� �������� ������������ ������������� ������ ��� ���������� ����� 'E-mail' � '���'
 */
public class AddRowNegative {

    public void addRowNegative(String email, String name, String error) {
        step_1();
        step_2(email, name);
        step_3(error);
        step_4(error);
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
    �������� ����: ������ ������. � ���� E-Mail: {email}, � ���� ���: {name}
    ��������� ���������: ���� E-Mail: ��������� {email}, � ���� ���: ��������� {name}
     */
    @Step("Step 2: ������ ������. � ���� E-Mail: {email}, � ���� ���: {name}")
    public void step_2(String email, String name) {
        $(By.id("dataEmail")).setValue(email);
        $(By.id("dataName")).setValue(name);

        assertAll(
                () -> assertEquals(email, $(By.id("dataEmail")).getValue(), "�������� � ���� E-Mail: �� ������������� ����������"),
                () -> assertEquals(name, $(By.id("dataName")).getValue(), "�������� � ���� ���: �� ������������� ����������")
        );

    }


    /*
    �������� ����: ������ �� ������ "��������"
    ��������� ���������: ��������� ��������� �� ������ {error}. ������� � ��������� E-Mail	���	���	����� 1	����� 2 �����
     */
    @Step("Step 3: ������ �� ������ \"��������\"")
    public void step_3(String error) {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(By.id("dataTable")).$("tbody").shouldBe(empty),
                () -> $(By.id(getId(error))).shouldBe(visible),
                () -> assertEquals(error, $(By.id(getId(error))).getText(), "��������� �� ������ �� ������������ ����������"));
    }


    /*
    �������� ����: ������������� ��������
    ��������� ���������: ��������� �� ������ �����������. ���������� �������� �����������
     */
    @Step("Step 4: ������������� ��������")
    public void step_4(String error) {
        refresh();

        assertAll(
                () -> $(By.id("authPage")).shouldBe(visible),
                () -> $(By.id(getId(error))).shouldNotBe(exist, visible));
    }

    private String getId(String error) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("�������� ������ E-Mail", "emailFormatError");
        errorMap.put("���� ��� �� ����� ���� ������", "blankNameError");

        return errorMap.get(error);
    }
}

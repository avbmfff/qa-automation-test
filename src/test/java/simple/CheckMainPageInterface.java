package simple;

import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/*
���� ��������� �� �������� ���������� �������� �����
 */
public class CheckMainPageInterface {

    public void checkMainPageInterface() {
        step_1();
        step_2();
        step_3();
    }

    /*
    �������� ����: ������� �� ������� ��������
    ��������� ���������: ����������� ��������� "E-Mail:", ��� ��� ��������� ���� ����� �� ���������, �������� ��� ��������������;
    ����������� ��������� "������:", ��� ��� ��������� ���� ����� �� ���������, �������� ��� ��������������;
    ����������� ������ � ������� "����"
     */
    @Step("Step 1: ������� �� ������� ��������")
    private void step_1() {

        // ��� "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='loginEmail']").shouldBe(visible).getText(), "��������� E-mail: ��� ������������"),
                () -> assertFalse($(By.id("loginEmail")).is(readonly), "���� ��� ���������� \"E-Mail:\" �� �������� ��� ��������������"),
                () -> assertEquals("", $(By.id("loginEmail")).getValue(), "���� ��� ���������� \"E-Mail:\" ���������"));

        // ��� "������:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals("������:", $("label[for='loginPassword']").shouldBe(visible).getText(), "��������� ������: ��� ������������"),
                () -> assertFalse($(By.id("loginPassword")).is(readonly), "���� ��� ���������� \"������:\" �� �������� ��� ��������������"),
                () -> assertEquals("", $(By.id("loginPassword")).getValue(), "���� ��� ���������� \"������:\" ���������"));

        // ��� ������ "�����"
        assertAll(
                () -> $(By.id("authButton")).shouldBe(visible, enabled),
                () -> assertEquals("����", $(By.id("authButton")).getText(), "������ ���� ���� �������������"));
    }

    /*
    �������� ����: ������ �� ������ � ������� "����"
    ��������� ���������: ��������� ��������� �� ������ � ������� "�������� ������ E-Mail"
     */
    @Step("Step 2: ������ �� ������ � ������� \"����\"")
    private void step_2() {
        $(By.id("authButton")).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldBe(visible),
                () -> assertEquals("�������� ������ E-Mail", $(By.id("emailFormatError")).getText(), "��������� �� ������ �� ������������ ����������"));
    }

    /*
    �������� ����: ������������� ��������
    ��������� ���������: ��������� �� ������ � ������� "�������� ������ E-Mail" ����������.
    ����������� ��������� "E-Mail:", ��� ��� ��������� ���� ����� �� ���������;
    ����������� ��������� "������:", ��� ��� ��������� ���� ����� �� ���������;
    ����������� ������ � ������� "����"
     */
    @Step("Step 3: ������������� ��������")
    private void step_3() {
        refresh();

        // ��� ��������� �� ������
        assertAll(
                () -> $(By.id("emailFormatError")).shouldNotBe(exist));

        // ��� "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='loginEmail']").shouldBe(visible).getText(), "��������� E-mail: ��� ������������"),
                () -> assertEquals("", $(By.id("loginEmail")).getValue(), "���� ��� ���������� \"E-Mail:\" ���������"));

        // ��� "������:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals("������:", $("label[for='loginPassword']").shouldBe(visible).getText(), "��������� ������: ��� ������������"),
                () -> assertEquals("", $(By.id("loginPassword")).getValue(), "���� ��� ���������� \"������:\" ���������"));

        // ��� ������ "�����"
        assertAll(
                () -> $(By.id("authButton")).shouldBe(visible, enabled),
                () -> assertEquals("����", $(By.id("authButton")).getText(), "������ ���� ���� �������������"));
    }
}

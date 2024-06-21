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
���� ��������� �� �������� ������������ ������������� ������ ��� ���������� ����� 'E-mail' � '������'
 */
public class InputNegativeData {

    public void inputNegativeData(String login, String password, String error) {
        step_1();
        step_2(login, password);
        step_3(error);
        step_4(error);
    }

    /*
    �������� ����: ��������� CheckMainPageInterface
    ��������� ���������: CheckMainPageInterface ��������
     */

    @Step("Step 1: ��������� CheckMainPageInterface")
    private void step_1() {
        new CheckMainPageInterface().checkMainPageInterface();
    }

    /*
    �������� ����: � ���� E-mail ��������� ��������� {login}, ���� ������ ��������� ��������� {password}
    ��������� ���������: ���� E-mail ��������� ��������� {login}, ���� ������ ��������� ��������� {password}
     */

    @Step("Step 2: � ���� E-mail ��������� ��������� {login}, ���� ������ ��������� ��������� {password}")
    private void step_2(String login, String password) {
        $(By.id("loginEmail")).setValue(login);
        $(By.id("loginPassword")).setValue(password);

        // ��� "E-Mail:"
        assertAll(
                () -> $(By.id("loginEmail")).shouldBe(visible),
                () -> assertEquals(login, $(By.id("loginEmail")).shouldBe(visible).getValue(), "�������� � ���� ��� ���������� \"E-Mail:\" �� ������������ ����������"));

        // ��� "������:"
        assertAll(
                () -> $(By.id("loginPassword")).shouldBe(visible),
                () -> assertEquals(password, $(By.id("loginPassword")).shouldBe(visible).getValue(), "�������� � ���� ��� ���������� \"������:\" �� ������������ ����������"));

    }

    /*
    �������� ����: ������ �� ������ "����"
    ��������� ���������: ��������� ������ {error}
     */

    @Step("Step 3: ������ �� ������ '����'")
    private void step_3(String error) {
        $(By.id("authButton")).click();
        assertAll(
                () -> $(By.id(getId(error))).shouldBe(visible),
                () -> assertEquals(error, $(By.id(getId(error))).getText(), "��������� �� ������ �� ������������ ����������"));
    }

    /*
    �������� ����: ������ �� "�" ����� � ���������� �� ������
    ��������� ���������: ��������� �� ������ ������� � �� ����������� �� ��������
     */
    @Step("Step 4: ������ �� \"�\" ����� � ���������� �� ������")
    private void step_4(String error) {
        $(".uk-alert-close.uk-close").shouldBe(visible, enabled).click();

        assertAll(
                () -> $(By.id(getId(error))).shouldNotBe(exist, visible));
    }

    private String getId(String error){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("�������� ������ E-Mail", "emailFormatError");
        errorMap.put("�������� E-Mail ��� ������", "invalidEmailPassword");

        return errorMap.get(error);
    }

}

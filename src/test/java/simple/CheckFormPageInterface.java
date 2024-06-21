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
���� ��������� �� �������� ���������� �������� �����
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
    �������� ����: �������� �����������
    ��������� ���������: ���������� �������� �����
     */
    @Step("Step 1: �������� �����������")
    private void step_1() {
        $(By.id("loginEmail")).setValue(login);
        $(By.id("loginPassword")).setValue(password);
        $(By.id("authButton")).shouldBe(visible).click();

        assertDoesNotThrow(() -> $(By.id("authPage")).shouldNotBe(visible));
    }

    /*
    �������� ����: ���������� �������� �����
    ��������� ���������: �� �������� ���������� ��������� ��������:
    ��������� E-Mail: ��� ��� ��������� ����: �� ���������, �������� ��� ��������������
    ��������� ���: ��� ��� ��������� ����: �� ���������, �������� ��� ��������������
    ��������� ���: ��� ��� ���� ������: �� ��������� ��������� ��������� �������,
    �������� ����� �� ���� ��������� �� ������� �� v: �������, �������

    ���� � ����� ���������� ��������: ������� 1.1, ������� 1.2. �� ���� �� ��������� �� ������
    ���� � ����� ���������� �����������: ������� 2.1, ������� 2.2,  ������� 2.3

    ������ ��������. �������� ��� �������

    ��� �������� ����������� ������ ������� � ���������: E-Mail	���	���	����� 1	����� 2

     */
    @Step("Step 2: ���������� �������� �����")
    private void step_2() {

        // ��� "E-Mail:"
        assertAll(
                () -> $(By.id("dataEmail")).shouldBe(visible),
                () -> assertEquals("E-Mail:", $("label[for='dataEmail']").shouldBe(visible).getText(), "��������� E-mail: ��� ������������"),
                () -> assertEquals("", $(By.id("dataEmail")).getValue(), "���� ��� ���������� \"E-Mail:\" ���������"),
                () -> assertFalse($(By.id("dataEmail")).is(readonly), "���� ��� ���������� \"E-Mail:\" �� �������� ��� ��������������"));

        // ��� "���:"
        assertAll(
                () -> $(By.id("dataName")).shouldBe(visible),
                () -> assertEquals("���:", $("label[for='dataName']").shouldBe(visible).getText(), "��������� ���: ��� ������������"),
                () -> assertEquals("", $(By.id("dataName")).getValue(), "���� ��� ���������� \"���:\" ���������"),
                () -> assertFalse($(By.id("dataName")).is(readonly), "���� ��� ���������� \"���:\" �� �������� ��� ��������������"));

        // ��� "���:"
        assertAll(
                () -> $(By.id("dataGender")).shouldBe(visible),
                () -> assertEquals("���:", $("label[for='dataGender']").shouldBe(visible).getText(), "��������� ���: ��� ������������"),
                () -> assertEquals("�������", $(By.id("dataGender")).getText(), "�������� �� ��������� �� ������������� ����������"),
                () -> assertEquals(2, $(By.id("dataGender")).getOptions().size(), "���������� ��������� �� ������������� ����������"),
                () -> $(By.id("dataGender")).getOptions().first().shouldHave(text("�������")),
                () -> $(By.id("dataGender")).getOptions().last().shouldHave(text("�������")));

        // ��� ��������
        assertAll(
                () -> $(By.id("dataCheck11")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("������� 1.1", $(By.id("dataCheck11")).parent().getText(), "�������� ������� 1.1 �� ������������ ����������"),
                () -> $(By.id("dataCheck12")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("������� 1.2", $(By.id("dataCheck12")).parent().getText(), "�������� ������� 1.2 �� ������������ ����������"));

        // ��� �����������
        assertAll(
                () -> $(By.id("dataSelect21")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("������� 2.1", $(By.id("dataSelect21")).parent().getText(), "�������� ������� 2.1 �� ������������ ����������"),
                () -> $(By.id("dataSelect22")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("������� 2.2", $(By.id("dataSelect22")).parent().getText(), "�������� ������� 2.2 �� ������������ ����������"),
                () -> $(By.id("dataSelect23")).shouldBe(visible).shouldNotBe(selected),
                () -> assertEquals("������� 2.3", $(By.id("dataSelect23")).parent().getText(), "�������� ������� 2.3 �� ������������ ����������"));

        // ��� ������ ��������
        assertAll(
                () -> $(By.id("dataSend")).shouldBe(visible, enabled),
                () -> assertEquals("��������", $(By.id("dataSend")).getText(), "�������� �������� �� ������������ ����������"));

        // ��� �������
        assertAll(
                () -> $(By.id("dataTable")).shouldBe(visible),
                () -> $(By.id("dataTable")).$("thead").shouldBe(visible),
                () -> $(By.id("dataTable")).$("tbody").shouldBe(empty),
                () -> assertEquals("E-Mail", $(By.id("dataTable")).$("thead").$("th:nth-child(1)").getText(), "�������� ������ ������� �� ������������� ����������"),
                () -> assertEquals("���", $(By.id("dataTable")).$("thead").$("th:nth-child(2)").getText(), "�������� ������ ������� �� ������������� ����������"),
                () -> assertEquals("���", $(By.id("dataTable")).$("thead").$("th:nth-child(3)").getText(), "�������� ������� ������� �� ������������� ����������"),
                () -> assertEquals("����� 1", $(By.id("dataTable")).$("thead").$("th:nth-child(4)").getText(), "�������� ��������� ������� �� ������������� ����������"),
                () -> assertEquals("����� 2", $(By.id("dataTable")).$("thead").$("th:nth-child(5)").getText(), "�������� ����� ������� �� ������������� ����������"));
    }

    /*
    �������� ����: ���������� ������� �������� ������� 1.1, ������� ����������� ������� 2.1
    ��������� ���������: �������� ������� 1.1 ����������� �������, ����������� ������� 2.1 �������
     */
    @Step("Step 3: ���������� ������� �������� ������� 1.1, ������� ����������� ������� 2.1")
    private void step_3() {
        $(By.id("dataCheck11")).setSelected(true);

        assertAll(
                () -> $(By.id("dataCheck11")).shouldBe(selected)
        );
    }

    /*
   �������� ����: ������ ������� �������� ������� 1.1, ������ ����� ����������� ������� 2.1
   ��������� ���������: �������� ������� 1.1 �� ����������� �������, ����������� ������� 2.1 �������
    */
    @Step("Step 4: ������ ������� �������� ������� 1.1, ��� ��� ������ �� ����� ����������� ������� 2.1")
    private void step_4() {

        $(By.id("dataCheck11")).setSelected(false);

        assertAll(
                () -> $(By.id("dataCheck11")).shouldNotBe(selected)
        );

    }

    /*
    �������� ����: ������ �� ������ "��������"
    ��������� ���������: ��������� ��������� �� ������ "�������� ������ E-Mail"
    */
    @Step("Step 5: ������ �� ������ \"��������\"")
    private void step_5() {
        $(By.id("dataSend")).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldBe(visible),
                () -> assertEquals("�������� ������ E-Mail", $(By.id("emailFormatError")).getText(), "��������� �� ������ �� ������������ ����������"));
    }


    /*
    �������� ����: ������ �� ������ "�" ����� � ���������� �� ������
    ��������� ���������: ��������� �� ������ "�������� ������ E-Mail" �������
    */
    @Step("Step 6: ������ �� ������ \"�\" ����� � ���������� �� ������")
    private void step_6() {
        $(".uk-alert-close.uk-close").shouldBe(visible, enabled).click();

        assertAll(
                () -> $(By.id("emailFormatError")).shouldNotBe(exist, visible));
    }


}

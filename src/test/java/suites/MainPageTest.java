package suites;

import initialization.Base;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import tests.InputNegativeData;
import tests.InputPositiveData;

import static com.codeborne.selenide.Selenide.open;
import static methods.GetProperty.getProperty;

/*
������ ���� ��������� ������������ ������ �������� �����
 */

public class MainPageTest extends Base {

    String login = getProperty("login");
    String password = getProperty("password");

    @BeforeEach
    public void openPage() {
        open(getProperty("qa_page"));
    }

    @ParameterizedTest(name = "�������� ���� e-mail � ������: {index} - {2}")
    @CsvSource(value = {
            "��������_������, 123456789, �������� ������ E-Mail",
            "abcdefg123456789, ��������_������, �������� ������ E-Mail",
            "abcdefg123456789.ru, ��������_������, �������� ������ E-Mail",
            "@.ru, ��������_������, �������� ������ E-Mail",
            "@%.ru, ��������_������, �������� ������ E-Mail",
            "a@1@.ru, ��������_������, �������� E-Mail ��� ������",
            "123abc@.ru, 123abc, �������� E-Mail ��� ������"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("�������� ����������������� �������� �����")
    @Feature("����������")
    public void mainPageNegative(String login, String password, String error) {

        String enterLogin = (login.equals("��������_������")) ? "" : login;
        String enterPassword = (password.equals("��������_������")) ? "" : password;

        new InputNegativeData().inputNegativeData(enterLogin, enterPassword, error);
    }

    @Test
    @Story("�������� ����������������� �������� �����")
    @Feature("����������")
    public void mainPagePositive() {
        new InputPositiveData().inputPositiveData(login, password);
    }

}

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
������ ���� ��������� ������������ ������ �������� �����
 */
public class FormPageTest extends Base {

    @BeforeEach
    public void openPage() {
        open(getProperty("qa_page"));
    }

    @ParameterizedTest(name = "�������� ������������ ���������� �����: {index} - {2}")
    @CsvSource(value = {
            "��������_������, 123456789, �������� ������ E-Mail",
            "abcdefg123456789, ��������_������, �������� ������ E-Mail",
            "abcdefg123456789.ru, ��������_������, �������� ������ E-Mail",
            "@.ru, ��������_������, �������� ������ E-Mail",
            "@%.ru, ��������_������, �������� ������ E-Mail",
            "@@.ru, ��������_������, ���� ��� �� ����� ���� ������",
            "a@1@.ru, ��������_������, ���� ��� �� ����� ���� ������"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("�������� ���������� �����")
    @Feature("����������")
    public void addRowNegative(String email, String name, String error){
        String enterEmail = (email.equals("��������_������")) ? "���" : email;
        String enterName = (name.equals("��������_������")) ? "" : name;

        new AddRowNegative().addRowNegative(enterEmail, enterName, error);
    }

    @ParameterizedTest(name = "�������� ������������ ���������� ������� � �������: {index} - {2}")
    @CsvSource(value = {
            "@@.ru, 123456789, ��_��������, ��_��������, ��_��������",
            "a@1@.com, abcdef, �������, ������� 1.1, ������� 2.1",
            "example@live.1, abc123, �������, ������� 1.2, ������� 2.2",
            "example@.1, ABCabc, �������, ������� 1.1 � ������� 1.2, ������� 2.3",
            "example@..., ABC123456, �������, ��_��������, ������� 2.1",
            "example@..., !@\\#$%^&*()�;:?/|, �������, ������� 1.1 � ������� 1.2, ��_��������"
    }, ignoreLeadingAndTrailingWhitespace = true)
    @Story("�������� ���������� �����")
    @Feature("����������")
    public void addRowPositive(String email, String name, String gender, String checkbox, String radioButton){
        new AddRowPositive().addRowPositive(email, name, gender, checkbox, radioButton);
    }

}

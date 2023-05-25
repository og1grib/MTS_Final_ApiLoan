package mtsCredit.tests;

import api.constant.ErrorCode;
import api.models.CreateOrderRequest;
import api.models.input.ErrorError;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;

public class PostLoanApplicationTest {
    private final CreditSteps creditSteps = new CreditSteps();
    private final Random random = new Random();

    private final static int maxT = 3;
    private final static int minT = 1;
    private final int tariffIdTrue = random.nextInt(maxT - minT) + minT;

    private final static int tariffIdFalse = 6;

    private final static Long maxP = 9999999999L;
    private final static Long minP = 1L;
    private final Long userId = random.nextLong(maxP - minP) + minP;
    private final CreateOrderRequest createOrderRequestTrue = new CreateOrderRequest(userId, tariffIdTrue);
    private final CreateOrderRequest createOrderRequestFalse = new CreateOrderRequest(userId, tariffIdFalse);

    @Description("БАГ - Метод подачи заявки на кредит(негативный с отрицательным значением userId) - оправляем POST запрос и проверяем статус код")
    @Test
    public void bagUnSuccessEmptyUserIdPostLoanApplication() {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(-userId, tariffIdTrue);

        creditSteps.postUnSuccessLoanApplication(createOrderRequest);
    }

    @Description("Метод подачи заявки на кредит(позитивный) - отправляем POST параметризованный запрос одного пользователя на все тарифы и проверяем статус код")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void successPostParametrizedLoanApplication(int tariffId) {
        CreateOrderRequest createOrderRequest = new CreateOrderRequest(userId, tariffId);
        creditSteps.postSuccessLoanApplication(createOrderRequest);
    }

    @Description("Метод подачи заявки на кредит (пустое тело) - оправляем POST запрос, проверяем статус код и в ответе code message Внутренняя ошибка сервера")
    @Test
    public void checkPostEmptyInternalError() {
        ErrorError response = creditSteps.postEmptyLoanApplication();

        creditSteps.checkFields(response.getError().getCode(), ErrorCode.INTERNAL_SERVER_ERROR.toString());
        creditSteps.checkFields(response.getError().getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getError());
    }

    @Description("Метод подачи заявки на кредит(негативный с несуществующим тарифом) - оправляем POST запрос, проверяем статус код и в ответе code message Тариф не найден")
    @Test
    public void checkPostNotFoundError() {
        ErrorError responseFalseLoanApplication = creditSteps.postUnSuccessLoanApplication(createOrderRequestFalse);

        creditSteps.checkFields(responseFalseLoanApplication.getError().getCode(), ErrorCode.TARIFF_NOT_FOUND.toString());
        creditSteps.checkFields(responseFalseLoanApplication.getError().getMessage(), ErrorCode.TARIFF_NOT_FOUND.getError());
    }

    @Description("Метод подачи заявки на кредит - подаем заявку 2 раза с одними данными и проверяем в ответе code message Заявка уже на рассмотрении")
    @Test
    public void postLoanConsiderationError() {
        creditSteps.postSuccessLoanApplication(createOrderRequestTrue);
        ErrorError responseFalse = creditSteps.postUnSuccessLoanApplication(createOrderRequestTrue);

        creditSteps.checkFields(responseFalse.getError().getCode(), ErrorCode.LOAN_CONSIDERATION.toString());
        creditSteps.checkFields(responseFalse.getError().getMessage(), ErrorCode.LOAN_CONSIDERATION.getError());
    }

    //Тест с задержкой
    @Description("Метод подачи заявки на кредит - подаем заявку 2 раза с задержкой в 2 минуты с одними данными и проверяем в ответе code message Заявка уже одобрена или Прошло менее 2 минут")
    @Test
    public void postAlreadyApprovedError() throws InterruptedException {
        creditSteps.postSuccessLoanApplication(createOrderRequestTrue);
        Thread.sleep(120000);
        ErrorError responseFalse = creditSteps.postUnSuccessLoanApplication(createOrderRequestTrue);

        String responseCode = responseFalse.getError().getCode();
        String responseMessage = responseFalse.getError().getMessage();

        assertThat(responseCode, Matchers.either(Matchers.is(ErrorCode.LOAN_ALREADY_APPROVED.toString())).or(Matchers.is(ErrorCode.TRY_LATER.toString())));
        assertThat(responseMessage, Matchers.either(Matchers.is(ErrorCode.LOAN_ALREADY_APPROVED.getError())).or(Matchers.is(ErrorCode.TRY_LATER.getError())));
    }
}
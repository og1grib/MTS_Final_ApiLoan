package mtsCredit.tests;

import api.constant.ErrorCode;
import api.constant.OrderStatus;
import api.models.CreateOrderRequest;
import api.models.input.ErrorError;
import api.models.input.GetStatusOrderData;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;

public class GetStatusApplicationTest {
    private final CreditSteps creditSteps = new CreditSteps();
    private final Random random = new Random();

    private final static int maxT = 3;
    private final static int minT = 1;
    private final int tariffIdTrue = random.nextInt(maxT - minT) + minT;

    private final static Long maxP = 9999999999L;
    private final static Long minP = 1L;
    private final Long userId = random.nextLong(maxP - minP) + minP;
    private final CreateOrderRequest createOrderRequestTrue = new CreateOrderRequest(userId, tariffIdTrue);

    private final static String orderIdFalse = " ";

    @Description("Метод получения статуса заявки(позитивный) - оправляем POST(подача заявки на кредит) и GET запрос, проверяем статус код и ответ при успешном выполнении статус заявки В процессе")
    @Test
    public void checkInProgressLoanApplication() {
        String orderId = creditSteps.postSuccessLoanApplication(createOrderRequestTrue).getData().getOrderId();
        GetStatusOrderData responseStatusApplication = creditSteps.getSuccessStatusLoanApplication(orderId);

        creditSteps.checkFields(responseStatusApplication.getData().getOrderStatus(), OrderStatus.IN_PROGRESS.toString());
    }

    @Description("Метод получения статуса заявки(негативный на пустой orderId) - оправляем GET запрос, проверяем статус код и в ответе code message Заявка не найдена")
    @Test
    public void checkGetOrderNotFoundError() {
        ErrorError responseFalse = creditSteps.getUnSuccessStatusLoanApplication(orderIdFalse);

        creditSteps.checkFields(responseFalse.getError().getCode(), ErrorCode.ORDER_NOT_FOUND.toString());
        creditSteps.checkFields(responseFalse.getError().getMessage(), ErrorCode.ORDER_NOT_FOUND.getError());
    }

    //Тест с задержкой
    @Description("Метод получения статуса заявки - проверяем при успешном выполнении статус заявки Отказано или Принята")
    @Test
    public void checkApprovedOrRefusedLoanApplication() throws InterruptedException {
        String orderId = creditSteps.postSuccessLoanApplication(createOrderRequestTrue).getData().getOrderId();
        Thread.sleep(120000);
        GetStatusOrderData responseStatusApplication = creditSteps.getSuccessStatusLoanApplication(orderId);

        String orderStatus = responseStatusApplication.getData().getOrderStatus();
        assertThat(orderStatus, Matchers.either(Matchers.is(OrderStatus.APPROVED.toString())).or(Matchers.is(OrderStatus.REFUSED.toString())));
    }

}

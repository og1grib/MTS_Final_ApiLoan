package mtsCredit.tests.statusTests;

import api.models.CreateOrderRequest;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class GetStatusApplicationStatusTest {
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

    @Description("Метод получения статуса заявки(позитивный) - оправляем POST(подача заявки на кредит) и GET запрос и проверяем статус код")
    @Test
    public void successGetStatusApplication() {
        String orderId = creditSteps.postSuccessLoanApplication(createOrderRequestTrue).getData().getOrderId();
        creditSteps.getSuccessStatusLoanApplication(orderId);
    }

    @Description("Метод получения статуса заявки(негативный на пустой orderId) - оправляем GET запрос и проверяем статус код")
    @Test
    public void unSuccessGetStatusAApplication() {

        creditSteps.getUnSuccessStatusLoanApplication(orderIdFalse);
    }
}

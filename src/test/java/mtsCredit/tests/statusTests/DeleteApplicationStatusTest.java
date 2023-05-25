package mtsCredit.tests.statusTests;

import api.models.CreateOrderRequest;
import api.models.DeleteOrderRequest;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DeleteApplicationStatusTest {
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

    @Description("Метод удаления заявки(позитивный) - оправляем DELETE запрос и проверяем статус код")
    @Test
    public void successDeleteApplication() {
        String orderId = creditSteps.postSuccessLoanApplication(createOrderRequestTrue).getData().getOrderId();
        DeleteOrderRequest deleteOrderRequestTrue = new DeleteOrderRequest(userId, orderId);
        creditSteps.deleteSuccessApplication(deleteOrderRequestTrue);
    }

    @Description("Метод удаления заявки(негативный) - оправляем DELETE запрос и проверяем статус код")
    @Test
    public void unSuccessDeleteSuccessApplication() {
        DeleteOrderRequest deleteOrderRequestFalse = new DeleteOrderRequest(userId, orderIdFalse);

        creditSteps.deleteUnSuccessApplication(deleteOrderRequestFalse);
    }
}

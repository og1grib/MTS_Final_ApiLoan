package mtsCredit.tests.statusTests;

import api.models.CreateOrderRequest;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class PostLoanApplicationStatusTest {
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

    @Description("Метод подачи заявки на кредит(позитивный) - оправляем POST запрос и проверяем статус код")
    @Test
    public void successPostLoanApplication() {


        creditSteps.postSuccessLoanApplication(createOrderRequestTrue);
    }

    @Description("Метод подачи заявки на кредит(негативный с несуществующим тарифом) - оправляем POST запрос и проверяем статус код")
    @Test
    public void unSuccessPostLoanApplication() {

        creditSteps.postUnSuccessLoanApplication(createOrderRequestFalse);
    }
}

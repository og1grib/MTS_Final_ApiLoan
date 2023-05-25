package mtsCredit.tests;

import api.constant.ErrorCode;
import api.models.CreateOrderRequest;
import api.models.DeleteOrderRequest;
import api.models.input.CreateOrderResponseData;
import api.models.input.ErrorError;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DeleteApplicationTest {
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

    @Description("Метод удаления заявки(негативный с пустым телом) - оправляем негативный DELETE запрос, проверяем статус код и ответ code message Внутренняя ошибка сервера")
    @Test
    public void checkDeleteEmptyInternalError() {
        ErrorError response = creditSteps.deleteUnSuccessEmptyApplication();

        creditSteps.checkFields(response.getError().getCode(), ErrorCode.INTERNAL_SERVER_ERROR.toString());
        creditSteps.checkFields(response.getError().getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getError());
    }

    @Description("Метод удаления заявки(негативный с несуществующим тарифом) - оправляем негативный DELETE запрос, проверяем статус код и ответ code message Заявка не найдена")
    @Test
    public void checkDeleteOrderNotFoundError() {
        DeleteOrderRequest deleteOrderRequestFalse = new DeleteOrderRequest(userId, orderIdFalse);
        ErrorError responseFalse = creditSteps.deleteUnSuccessApplication(deleteOrderRequestFalse);

        creditSteps.checkFields(responseFalse.getError().getCode(), ErrorCode.ORDER_NOT_FOUND.toString());
        creditSteps.checkFields(responseFalse.getError().getMessage(), ErrorCode.ORDER_NOT_FOUND.getError());
    }

    @Description("БАГ - Метод удаления заявки(негативный с orderId не соответствующему userId) - оправляем DELETE запрос и проверяем статус код")
    @Test
    public void bagUnSuccessDeleteApplicationFalseData() {
        String orderId = creditSteps.postSuccessLoanApplication(createOrderRequestTrue).getData().getOrderId();
        DeleteOrderRequest deleteOrderRequestTrue = new DeleteOrderRequest(0L, orderId);

        creditSteps.deleteUnSuccessApplication(deleteOrderRequestTrue);
    }

    //Тест с задержкой
    @Description("Метод удаления заявки(негативный на уже одобренную заявку) - оправляем негативный DELETE запрос, проверяем статус код и ответ code message Невозможно удалить заявку")
    @Test
    public void checkDeleteImpossibleError() throws InterruptedException {
        CreateOrderResponseData responseTrue = creditSteps.postSuccessLoanApplication(createOrderRequestTrue);
        Thread.sleep(120000);
        DeleteOrderRequest deleteOrderRequestFalse = new DeleteOrderRequest(userId, responseTrue.getData().getOrderId());

        ErrorError responseFalseDeleteApplication = creditSteps.deleteUnSuccessApplication(deleteOrderRequestFalse);

        creditSteps.checkFields(responseFalseDeleteApplication.getError().getCode(), ErrorCode.ORDER_IMPOSSIBLE_TO_DELETE.toString());
        creditSteps.checkFields(responseFalseDeleteApplication.getError().getMessage(), ErrorCode.ORDER_IMPOSSIBLE_TO_DELETE.getError());
    }
}

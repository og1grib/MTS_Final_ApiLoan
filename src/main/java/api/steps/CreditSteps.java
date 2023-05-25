package api.steps;

import api.models.CreateOrderRequest;
import api.models.DeleteOrderRequest;
import api.models.input.CreateOrderResponseData;
import api.models.input.ErrorError;
import api.models.input.GetStatusOrderData;
import api.models.input.TariffsData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class CreditSteps {
    @Step("Метод получения тарифов - отправить запрос GET")
    public static TariffsData getSuccessCreditTariffs() {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .get("getTariffs")
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", TariffsData.class);
    }

    @Step("Метод подачи заявки на кредит - отправить позитивный запрос POST")
    public static CreateOrderResponseData postSuccessLoanApplication(CreateOrderRequest createOrderRequest) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .body(createOrderRequest)
                .post("order")
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", CreateOrderResponseData.class);
    }

    @Step("Метод подачи заявки на кредит - отправить негативный запрос POST")
    public static ErrorError postUnSuccessLoanApplication(CreateOrderRequest createOrderRequest) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .body(createOrderRequest)
                .post("order")
                .then()
                .spec(SpecHelper.getResponseSpec(400))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", ErrorError.class);
    }

    @Step("Метод подачи заявки на кредит - отправить негативный запрос с пустым полем POST")
    public static ErrorError postEmptyLoanApplication() {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .post("order")
                .then()
                .spec(SpecHelper.getResponseSpec(500))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", ErrorError.class);
    }

    @Step("Метод получения статуса заявки - отправить позитивный запрос GET")
    public static GetStatusOrderData getSuccessStatusLoanApplication(String orderId) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .get(String.format("getStatusOrder?orderId=%s", orderId))
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", GetStatusOrderData.class);
    }

    @Step("Метод получения статуса заявки - отправить негативный запрос GET")
    public static ErrorError getUnSuccessStatusLoanApplication(String orderId) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .get(String.format("getStatusOrder?orderId=%s", orderId))
                .then()
                .spec(SpecHelper.getResponseSpec(400))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", ErrorError.class);
    }

    @Step("Метод удаления заявки - отправить позитивный запрос DELETE")
    public static Response deleteSuccessApplication(DeleteOrderRequest deleteOrderRequest) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .body(deleteOrderRequest)
                .delete("deleteOrder")
                .then()
                .spec(SpecHelper.getResponseSpec(200))
                .extract()
                .response();
    }

    @Step("Метод удаления заявки - отправить негативный запрос DELETE")
    public static ErrorError deleteUnSuccessApplication(DeleteOrderRequest deleteOrderRequest) {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .body(deleteOrderRequest)
                .delete("deleteOrder")
                .then()
                .spec(SpecHelper.getResponseSpec(400))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", ErrorError.class);
    }

    @Step("Метод удаления заявки - отправить негативный запрос с пустым полем DELETE")
    public static ErrorError deleteUnSuccessEmptyApplication() {

        return given()
                .spec(SpecHelper.getRequestSpec())
                .when()
                .delete("deleteOrder")
                .then()
                .spec(SpecHelper.getResponseSpec(500))
                .extract()
                .body()
                .jsonPath()
                .getObject(".", ErrorError.class);
    }

    @Step("Проверка значений значений полей полученного ответа и ожидаемого ответа")
    public void checkFields(String responseField, String expectField) {
        Assertions.assertEquals(responseField, expectField);
    }

}

package mtsCredit.tests.statusTests;

import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class GetTariffsStatusTest {

    private final CreditSteps creditSteps = new CreditSteps();

    @Description("БАГ - Метод получения тарифов - оправляем GET запрос и проверяем статус код")
    @Test
    public void bagSuccessGetTariffs() {

        creditSteps.getSuccessCreditTariffs();
    }
}

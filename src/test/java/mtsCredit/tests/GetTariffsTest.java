package mtsCredit.tests;

import api.constant.TariffType;
import api.models.input.TariffsData;
import api.steps.CreditSteps;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class GetTariffsTest {
    private final CreditSteps creditSteps = new CreditSteps();

    @Description("БАГ - Метод получения тарифов - оправляем GET запрос, проверяем статус код и в ответе id, type и interest_rate тарифов")
    @Test
    public void bagCheckTariffs() {
        TariffsData responseGetTariffs = creditSteps.getSuccessCreditTariffs();

        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(0).getId().toString(), "1");
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(0).getType(), TariffType.CONSUMER.toString());
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(0).getInterestRate(), TariffType.CONSUMER.getValue().toString());
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(1).getId().toString(), "2");
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(1).getType(), TariffType.MORTGAGE.toString());
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(1).getInterestRate(), TariffType.MORTGAGE.getValue().toString());
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(2).getId().toString(), "3");
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(2).getType(), TariffType.INTRABANK.toString());
        creditSteps.checkFields(responseGetTariffs.getData().getTariffs().get(2).getInterestRate(), TariffType.INTRABANK.getValue().toString());
    }
}
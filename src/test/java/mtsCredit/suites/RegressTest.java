package mtsCredit.suites;

import mtsCredit.tests.DeleteApplicationTest;
import mtsCredit.tests.GetStatusApplicationTest;
import mtsCredit.tests.GetTariffsTest;
import mtsCredit.tests.PostLoanApplicationTest;
import mtsCredit.tests.statusTests.DeleteApplicationStatusTest;
import mtsCredit.tests.statusTests.GetStatusApplicationStatusTest;
import mtsCredit.tests.statusTests.GetTariffsStatusTest;
import mtsCredit.tests.statusTests.PostLoanApplicationStatusTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        DeleteApplicationTest.class,
        GetStatusApplicationTest.class,
        GetTariffsTest.class,
        PostLoanApplicationTest.class,
        DeleteApplicationStatusTest.class,
        GetStatusApplicationStatusTest.class,
        GetTariffsStatusTest.class,
        PostLoanApplicationStatusTest.class
})
public class RegressTest {
}

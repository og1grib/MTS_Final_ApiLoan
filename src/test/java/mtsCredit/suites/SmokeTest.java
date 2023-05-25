package mtsCredit.suites;

import mtsCredit.tests.statusTests.DeleteApplicationStatusTest;
import mtsCredit.tests.statusTests.GetStatusApplicationStatusTest;
import mtsCredit.tests.statusTests.GetTariffsStatusTest;
import mtsCredit.tests.statusTests.PostLoanApplicationStatusTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        DeleteApplicationStatusTest.class,
        GetStatusApplicationStatusTest.class,
        GetTariffsStatusTest.class,
        PostLoanApplicationStatusTest.class
})
public class SmokeTest {
}

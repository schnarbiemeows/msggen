import com.scala.classes.utilities.{DateUtilsTest, NumUtilitiesTest}
import com.scala.classes.validators._
import org.junit.runner.RunWith
import org.junit.runners.Suite

/*
 * Created 2019 by Dylan Kessler
 */
/**
  * main Junit testing class : run this to run all tests
  */
@RunWith(classOf[Suite])
@Suite.SuiteClasses(Array(classOf[DateUtilsTest],classOf[NumUtilitiesTest],classOf[StringFormatValidatorTest],
classOf[IntLongFormatValidatorTest],classOf[DateTimeFormatValidatorTest],classOf[FloatDoubleFormatValidatorTest],
classOf[MoneyFormatValidatorTest]))
class MsgGenTestSuite {

}

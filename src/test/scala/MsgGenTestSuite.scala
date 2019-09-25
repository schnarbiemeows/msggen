import com.scala.classes.generators._
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
@Suite.SuiteClasses(Array(classOf[DateUtilsTest],classOf[NumUtilitiesTest],classOf[StringQualifiersValidatorTest],
classOf[IntLongQualifiersValidatorTest],classOf[DateTimeQualifiersValidatorTest],classOf[FloatDoubleQualifiersValidatorTest],
classOf[MoneyQualifiersValidatorTest],classOf[DateTimeTypeGeneratorTest],classOf[DateTypeGeneratorTest],
classOf[DoubleTypeGeneratorTest],classOf[FloatTypeGeneratorTest],classOf[MoneyTypeGeneratorTest],
classOf[LongTypeGeneratorTest],classOf[IntTypeGeneratorTest],classOf[StringTypeGeneratorTest]))
class MsgGenTestSuite {

}

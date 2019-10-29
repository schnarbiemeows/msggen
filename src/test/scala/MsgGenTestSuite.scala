import com.scala.classes.business.{HiveTableCreatorTest, MMMtableRandomizerTest}
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
classOf[WholeNumberQualifiersValidatorTest],classOf[DateTimeQualifiersValidatorTest],classOf[DecimalNumberQualifiersValidatorTest],
classOf[MoneyQualifiersValidatorTest],classOf[DateTimeTypeGeneratorTest],classOf[DateTypeGeneratorTest],
classOf[DecimalNumberTypeGeneratorTest],classOf[MoneyTypeGeneratorTest],classOf[MMMtableRandomizerTest],
classOf[WholeNumberTypeGeneratorTest],classOf[StringTypeGeneratorTest],classOf[HiveTableCreatorTest]))
class MsgGenTestSuite {

}

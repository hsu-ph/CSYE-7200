package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

// 2) What is the survival percentage for each Ticket class? Which class has the highest survival rate?
object SurviClass {
  def surClass(df: DataFrame): Unit = {

    val surviveClass = df.groupBy("Pclass").agg(avg("Survived").alias("percentSurvived")).orderBy("Pclass")
    surviveClass.show()

    val highSurvivalClass = surviveClass.orderBy(desc("percentSurvived")).limit(1)
    highSurvivalClass.show()
  }
}


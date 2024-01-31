package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
 3) Rose DeWitt Bukater
 */
object PossibleRose {
  def possibleRose(df: DataFrame): Unit = {
    val possibleRose = df.filter("Age = 17")
      .filter("Pclass = 1").filter("Sex='female'").filter("Parch = 1")
    possibleRose.show()
  }
}

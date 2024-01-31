package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
 4) Jack Dawson
 */
object PossibleJack {
  def possibleJack(df: DataFrame): Unit = {
    val possibleJack = df.filter("Pclass = 3").filter("Age = 19 OR Age = 20")
      .filter("Sex='male'").filter("Parch = 0").filter("SibSp = 0")
    possibleJack.show()
  }
}



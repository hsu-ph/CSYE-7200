package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
 4) Jack Dawson
 */
object PossibleJack {
  def possibleJack(df: DataFrame): Unit = {
    val possibleJack = df.filter("Pclass = 3").filter("Age = 19 OR Age = 20 OR Age is Null")
      .filter("Sex='male'").filter("Parch = 0").filter("SibSp = 0")

    val countJ = possibleJack.count()
    println(s"Total possible Jack: $countJ")
    possibleJack.show()
  }
}



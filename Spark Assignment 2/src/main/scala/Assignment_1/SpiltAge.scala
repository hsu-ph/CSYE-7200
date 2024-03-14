package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
  5) Spilt age
 */
object SpiltAge {
  def spiltAge(df: DataFrame): Unit = {
    val dfAgeGroup = df.withColumn("AgeGroup"
      , when(col("Age") >= 0 && col("Age") < 1, "0-1")
        .when(col("Age") >= 1 && col("Age") < 11, "1-10")
        .when(col("Age") >= 11 && col("Age") < 21, "11-20")
        .when(col("Age") >= 21 && col("Age") < 31, "21-30")
        .when(col("Age") >= 31 && col("Age") < 41, "31-40")
        .when(col("Age") >= 41 && col("Age") < 51, "41-50")
        .when(col("Age") >= 51 && col("Age") < 61, "51-60")
        .when(col("Age") >= 61 && col("Age") < 71, "61-70")
        .when(col("Age") >= 71 && col("Age") < 81, "71-80")
        .when(col("Age") >= 81, "81+")
        .otherwise("Unknown"))

    val ageGroupFare = dfAgeGroup.groupBy("AgeGroup").agg(avg("Fare").alias("avgFare")).orderBy("AgeGroup")
    val ageGroupSurvivalRate = dfAgeGroup.groupBy("AgeGroup").agg(avg("Survived").alias("SurvivalRate")).orderBy("AgeGroup")

    ageGroupFare.show()
    ageGroupSurvivalRate.show()
    ageGroupSurvivalRate.show(1)
    ageGroupSurvivalRate.show(2)
  }
}



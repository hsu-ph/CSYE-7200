package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
  5) Spilt age
 */
object SpiltAge {
  def spiltAge(df: DataFrame): Unit = {
    val dfAgeGroup = df.withColumn("AgeGroup"
      ,when(col("Age").between(0, 1), "0-1")
        .when(col("Age").between(1, 10), "1-10")
        .when(col("Age").between(11, 20), "11-20")
        .when(col("Age").between(21, 30), "21-30")
        .when(col("Age").between(31, 40), "31-40")
        .when(col("Age").between(41, 50), "41-50")
        .when(col("Age").between(51, 60), "51-60")
        .when(col("Age") > 60, "60+")
        .otherwise("Unknown"))
   // dfAgeGroup.show()

    val ageGroupFare = dfAgeGroup.groupBy("AgeGroup").agg(avg("Fare").alias("avgFare")).orderBy("AgeGroup")
    ageGroupFare.show()
  }
}

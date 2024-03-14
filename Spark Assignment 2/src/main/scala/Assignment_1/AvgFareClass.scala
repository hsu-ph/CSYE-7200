package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
 1)What is the average ticket fare for each Ticket class?
 */
object AvgFareClass {
  def avgFareClass(df: DataFrame): Unit = {
    // 1) What is the average ticket fare for each Ticket class?
    val avgFare = df.groupBy("Pclass").agg(avg("Fare").alias("avgFare")).orderBy("Pclass").orderBy("Pclass")
    avgFare.show()
  }

}

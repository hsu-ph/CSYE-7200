package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("1-AvgFare")
      .master("local[*]")
      .getOrCreate()

    val df = spark.read
      .option("header", value = true)
      .csv("data/IMBD.csv")
    AvgRating.avgFareClass(df)
    StdRating.stdDevRating(df)
    SplitRunTime.splitRunTime(df)
  }
}

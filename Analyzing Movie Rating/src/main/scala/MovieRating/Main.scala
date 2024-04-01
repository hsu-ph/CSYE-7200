package MovieRating

import org.apache.spark.sql.{DataFrame, SparkSession}

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("MovieRating")
      .master("local[*]")
      .getOrCreate()

    //read data sourse
    val df = spark.read
      .option("header", value = true)
      .csv("data/IMBD.csv")

    AvgRating.avgFareClass(df)
    SplitRunTime.splitRunTimeAvg(df)
/*
    AvgFareClass.avgFareClass(df)
    SurviClass.surClass(df)
    PossibleRose.possibleRose(df)
    PossibleJack.possibleJack(df)
    SpiltAge.spiltAge(df)
    */
    spark.stop() // Stop the Spark session when done
  }
}
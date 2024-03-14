package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._


object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("1-AvgFare")
      .master("local[*]")
      .getOrCreate()

    //read data sourse
    val df = spark.read
      .option("header", value = true)
      .csv("data/train.csv")

    println("Original: ")
    df.show()

    println("Spark Assignment 1: ")
    AvgFareClass.avgFareClass(df)
    SurviClass.surClass(df)
    PossibleRose.possibleRose(df)
    PossibleJack.possibleJack(df)
    SpiltAge.spiltAge(df)
    println("New idea: ")
    AvgAge.avgAge(df)

    // Feature Engineering
    println("Feature Engineering: ")
    val dfFeatured = Model.featureEngineering(df)
    dfFeatured.show()


    // Training and prediction
    println("Prediction: ")
    Model.trainAndPredict(dfFeatured)

    spark.stop()
  }
}

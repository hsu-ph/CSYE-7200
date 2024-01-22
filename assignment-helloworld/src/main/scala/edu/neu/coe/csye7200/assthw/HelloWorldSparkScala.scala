package edu.neu.coe.csye7200.assthw

import org.apache.spark.{SparkConf, SparkContext}

object HelloWorldSparkScala {
  def main(args: Array[String]): Unit = { //Set up Spark configuration
    // println("my first spark") //test
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("CountingSheep")
      .set("spark.executor.memory", "1g")
    val sc = new SparkContext(conf)
    println("Hello, this is my first Spark.") //result
  }
}

package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{avg, col, when, stddev, rank}
import org.apache.spark.sql.expressions.Window

object SplitRunTime {
  def splitRunTime(df: DataFrame): Unit = {
    val dfRunTime = df.withColumn("SplitRunTime",
      when(col("runtime") >= 0 && col("runtime") < 51, "0-50")
        .when(col("runtime") >= 51 && col("runtime") < 81, "51-80")
        .when(col("runtime") >= 81 && col("runtime") < 111, "81-110")
        .when(col("runtime") >= 111 && col("runtime") < 141, "111-150")
        .when(col("runtime") >= 141 && col("runtime") < 171, "141-170")
        .when(col("runtime") >= 171 && col("runtime") < 201, "171-200")
        .when(col("runtime") >= 201 && col("runtime") < 231, "201-230")
        .when(col("runtime") >= 231 && col("runtime") < 261, "231-260")
        .when(col("runtime") >= 261, "261+")
        .otherwise("Unknown"))

    val windowSpec = Window.partitionBy("SplitRunTime").orderBy(col("avgRating").desc)

    val runtimeStats = dfRunTime.groupBy("SplitRunTime")
      .agg(avg("rating").alias("avgRating"), stddev("rating").alias("stdDevRating"))
      .withColumn("rank", rank().over(windowSpec))
      .where(col("rank") === 1)
      .drop("rank")

    runtimeStats.show()
  }
}


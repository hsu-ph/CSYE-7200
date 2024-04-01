package MovieRating

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{avg, col, when}

object SplitRunTime {
  def splitRunTimeAvg(df: DataFrame): Unit = {
    val dfRunTime = df.withColumn("SplitRunTime",
      when(col("runtime") >= 0 && col("runtime") < 51, "0-50")
        .when(col("runtime") >= 51 && col("runtime") < 81, "51-80")
        .when(col("runtime") >= 81 && col("runtime") < 111, "81-110")
        .when(col("runtime") >= 111 && col("runtime") < 141, "111-150")
        .when(col("runtime") >= 141 && col("runtime") < 171, "141-170")
        .when(col("runtime") >= 171 && col("runtime") < 201, "171-200")
        .when(col("runtime") >= 201, "201+")
        .otherwise("Unknown"))

    val runtimeAvg = dfRunTime.groupBy("SplitRunTime").agg(avg("rating").alias("avgRating")).orderBy("SplitRunTime")
    runtimeAvg.show()
  }
}

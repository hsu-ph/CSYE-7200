package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.stddev

object StdRating {
  def stdDevRating(df: DataFrame): Unit = {
    // Calculate the standard deviation of the "rating" column
    val stdDevRating = df.agg(stddev("rating").alias("stdDevRating"))
    stdDevRating.show()
  }
}

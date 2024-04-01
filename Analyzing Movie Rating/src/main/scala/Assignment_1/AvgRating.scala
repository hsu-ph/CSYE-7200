package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.avg

object AvgRating {
  def avgFareClass(df: DataFrame): Unit = {
    val avgRating = df.agg(avg("rating").alias("avgRating"))
    avgRating.show()
  }
}

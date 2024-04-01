package MovieRating
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._


object AvgRating {

  def avgFareClass(df: DataFrame): Unit = {
    // 1) What is the average ticket fare for each Ticket class?
    val avgFare = df.agg(avg("rating").alias("avgFare"))
    avgFare.show()
  }
}

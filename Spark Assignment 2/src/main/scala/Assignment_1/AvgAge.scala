package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.avg

object AvgAge {
  def avgAge(df: DataFrame): Unit = {
    val avgAge = df.agg(avg("Age").alias("avgAge"))
    avgAge.show()
  }
}

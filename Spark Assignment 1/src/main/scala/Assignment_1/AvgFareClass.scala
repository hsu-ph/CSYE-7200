package Assignment_1

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

/*
 1)What is the average ticket fare for each Ticket class?
 */
object AvgFareClass {
  def avgFareClass(df: DataFrame): Unit = {
    // 1) What is the average ticket fare for each Ticket class?
    val avgFare = df.groupBy("Pclass").agg(avg("Fare").alias("avgFare")).orderBy("Pclass").orderBy("Pclass")
    avgFare.show()
  }


  // Method 2
  /*
    // Pclass=1, 2, 3
    val filter_Pc1 = df.filter("Pclass = 1")
    val filter_Pc2 = df.filter("Pclass = 2")
    val filter_Pc3 = df.filter("Pclass = 3")
    val avgPc1 = filter_Pc1.agg(avg("Fare").alias("avgPc1"))
    val avgPc2 = filter_Pc2.agg(avg("Fare").alias("avgPc2"))
    val avgPc3 = filter_Pc3.agg(avg("Fare").alias("avgPc3"))
    avgPc1.show()
    avgPc2.show()
    avgPc3.show()
    */

}

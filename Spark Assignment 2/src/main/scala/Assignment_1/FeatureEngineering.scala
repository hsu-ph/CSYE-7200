package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions._



object FeatureEngineering {

  def featureEngineering(df: DataFrame): Unit = {
    // Creating a new feature, Combine the Parch and SibSp columns to create a new feature
    val dfWithNewFeature = df.withColumn("ParchplusSibSp", col("parch") + col("SibSp"))

    // Handling Missing Values: Use the average to fill in missing values in the Age column
    val dfFilled = dfWithNewFeature.na.fill(Map("Age" -> dfWithNewFeature.select(avg("Age")).first().getDouble(0)))

    val dfEncoded = dfFilled.withColumn("SexEncoded", when(col("Sex") === "male", 0).otherwise(1))

    // delete "Cabin"
    val dfFinal = dfEncoded.drop("Cabin", "Parch", "SibSp")

    dfFinal.show()
  }
}

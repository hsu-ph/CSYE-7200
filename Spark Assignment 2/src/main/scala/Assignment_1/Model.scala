package Assignment_1

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.{SparkSession, Dataset, functions}
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.classification.{RandomForestClassifier, RandomForestClassificationModel}
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.{Pipeline, PipelineModel}
import FeatureEngineering._
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.sql.functions._
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object Model {

  def featureEngineering(df: DataFrame): DataFrame = {
    // Convert "Age" and "Fare" columns to integer type
    val dfWithAgeInt = df.withColumn("Age", col("Age").cast("Int"))
      .withColumn("Fare", col("Fare").cast("Int"))

    // Combine Parch and SibSp columns to create a new feature
    val dfWithNewFeature = dfWithAgeInt.withColumn("ParchPlusSibSp", col("Parch") + col("SibSp"))

    // Use average to fill in missing values in the Age column
    val dfFilled = dfWithNewFeature.na.fill(Map("Age" -> dfWithNewFeature.select(avg("Age")).first().getDouble(0)))

    val dfEncoded = dfFilled.withColumn("SexEncoded", when(col("Sex") === "male", 0).otherwise(1))

    // Drop unnecessary columns
    val dfFinal = dfEncoded.drop("Cabin", "Ticket", "Name")

    // Index categorical variables if not already indexed
    val indexedCols = dfFinal.columns.filter(_.endsWith("Index"))
    val indexers = indexedCols.map(col => col -> dfFinal.columns.contains(col))
    val indexer1 = new StringIndexer()
      .setInputCol("Pclass")
      .setOutputCol("PclassIndex")
      .setHandleInvalid("skip")
    val indexer2 = new StringIndexer()
      .setInputCol("Embarked")
      .setOutputCol("EmbarkedIndex")
      .setHandleInvalid("skip")
    val survivedIndexer = new StringIndexer()
      .setInputCol("Survived")
      .setOutputCol("SurvivedIndex")
      .setHandleInvalid("skip")

    val indexersToApply: Seq[(String, StringIndexer)] = Seq(
      ("PclassIndex", indexer1),
      ("EmbarkedIndex", indexer2),
      ("SurvivedIndex", survivedIndexer)
    ).filterNot { case (col, indexer) =>
      indexedCols.contains(col)
    }

    val indexedDF = indexersToApply.foldLeft(dfFinal) { case (accDF, (col, indexer)) =>
      indexer.fit(accDF).transform(accDF)
    }

    indexedDF
  }

  def trainAndPredict(df: DataFrame): Unit = {
    val spark = SparkSession.builder()
      .appName("TitanicPrediction")
      .master("local[*]")
      .getOrCreate()

    val data = featureEngineering(df)

    // Training and testing
    val Array(trainingData, testData) = data.randomSplit(Array(0.8, 0.2), seed = 1234)

    // Training random Forest model
    val assembler = new VectorAssembler()
      .setInputCols(Array("PclassIndex", "Age", "Fare", "ParchPlusSibSp", "SexEncoded", "EmbarkedIndex"))
      .setOutputCol("features")

    val rf = new RandomForestClassifier()
      .setLabelCol("SurvivedIndex")
      .setFeaturesCol("features")

    val pipeline = new Pipeline()
      .setStages(Array(assembler, rf))

    val model = pipeline.fit(trainingData)
    val predictions = model.transform(testData).orderBy("PassengerId")
    predictions.show()

    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("SurvivedIndex")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println(s"Accuracy: $accuracy")

    spark.stop()
  }
}


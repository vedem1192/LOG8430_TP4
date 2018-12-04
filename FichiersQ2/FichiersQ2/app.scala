import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import com.datastax.spark.connector._, org.apache.spark.SparkContext, org.apache.spark.SparkContext._, org.apache.spark.SparkConf, org.apache.spark.sql.cassandra._

val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
val rdd = sc.cassandraTable("dev", "facture")


val fpg = new FPGrowth()
  .setMinSupport(0.2)
  .setNumPartitions(10)
val model = fpg.run(rdd)

model.freqItemsets.collect().foreach { itemset =>
  println(s"${itemset.items.mkString("[", ",", "]")},${itemset.freq}")
}

val minConfidence = 0.8
model.generateAssociationRules(minConfidence).collect().foreach { rule =>
  println(s"${rule.antecedent.mkString("[", ",", "]")}=> " +
    s"${rule.consequent .mkString("[", ",", "]")},${rule.confidence}")
}
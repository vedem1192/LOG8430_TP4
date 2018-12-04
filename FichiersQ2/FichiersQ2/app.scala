import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.sql.cassandra._

object Main {
        def main(args: Array[String]) {
                //val conf = new SparkConf(true).set("spark.cassandra.connection.host", "127.0.0.1")
                val conf = new SparkConf().setAppName("TP4").setMaster("local[2]").set("spark.cassandra.connection.host$                val sc = new SparkContext(conf)
                val rdd = sc.cassandraTable("dev", "facture").select("name")
                rdd.collect().foreach(println)
                val data = sc.textFile("data.txt")

                val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))
                val fpg = new FPGrowth()
                        .setMinSupport(0.2)
                        .setNumPartitions(10)
                val model = fpg.run(transactions)

                model.freqItemsets.collect().foreach { itemset =>
                        println(s"${itemset.items.mkString("[", ",", "]")},${itemset.freq}")
                }

                val minConfidence = 0.8
                model.generateAssociationRules(minConfidence).collect().foreach { rule =>
                        println(s"${rule.antecedent.mkString("[", ",", "]")}=> " +
                        s"${rule.consequent .mkString("[", ",", "]")},${rule.confidence}")
                }
                sc.stop
        }
}
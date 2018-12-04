from pyspark.sql import SparkSession
from pyspark.sql.functions import explode
from operator import add
from pyspark.mllib.fpm import FPGrowth

cluster_seeds = ['127.0.0.1']
sparkSession = SparkSession.builder.appName("frequent-products").config("spark.cassandra.connection.host", ",".join(cluster_seeds)).getOrCreate()
factures = sparkSession.read.format("org.apache.spark.sql.cassandra").options(table="facture", keyspace="dev").load()
transactions = factures.rdd.map(lambda x: list(set(x)))
model = FPGrowth.train(transactions, minSupport=0.2, numPartitions=10)
#Missing printing instruction, but connection works on spark-submit with this protocol
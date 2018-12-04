from pyspark.mllib.fpm import FPGrowth
from pyspark import SparkContext
from cassandra.cluster import Cluster


if __name__ == "__main__":
    #First connection working only works through python3 execution
    cluster = Cluster()
    session = cluster.connect()
    results = session.execute(SELECT * FROM dev.facture)
    print(results)

    #Second protocol, also working through python3 execution only
    sc = SparkContext("spark://127.0.0.1:7077", "First App")
    rdd = sc.cassandraTable("dev", "facture")
    transactions = rdd.map(lambda x: list(set(x)))
    model = FPGrowth.train(transactions, minSupport=0.2, numPartitions=10)
    result = model.freqItemsets().collect()
    for fi in result:
        print(fi)

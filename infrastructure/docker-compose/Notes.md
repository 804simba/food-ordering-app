# HOW TO START KAFKA
- start up zookeeper `docker compose -f common.yml -f zookeeper.yml up`
- check that zookeeper is running in a non-error state using ruok.
- start the kafka-cluster `docker compose -f common.yml -f kafka-cluster.yml up`
- start the init-kafka using `docker compose -f common.yml -f init_kafka.yml up`
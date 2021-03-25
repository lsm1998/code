docker 安装kafka
docker run -d --name zookeeper -p 2181:2181 -v /etc/localtime:/etc/localtime wurstmeister/zookeeper:latest
docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_ADVERTISED_HOST_NAME=119.29.117.244 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest

zookeeper
https://blog.csdn.net/u010391342/article/details/100404588

docker安装elasticsearch
https://blog.csdn.net/qq_40942490/article/details/111594267

springboot整合elasticsearch
https://blog.csdn.net/pyfysf/article/details/100810846
java -jar jars/avro-tools-1.9.2.jar tojson data/raw20150421_sample.avro | \
  kafka-avro-console-producer --broker-list localhost:9092 --topic opensky \
  --property value.schema.file=data/opensky.avsc
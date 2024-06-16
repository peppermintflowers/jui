#!/bin/bash

# Load environment variables from .envlocal file
for line in $(cat .envlocal); do
  key=$(echo $line | cut -d '=' -f 1)
  value=$(echo $line | cut -d '=' -f 2-)
  export $key=$value
done
export SPRING_DATA_MONGODB_URI=mongodb://${MONGO_DB_USERNAME}:${MONGO_DB_PASSWORD}@${MONGO_HOST}:27017/${MONGO_DB_NAME}'?authSource=admin'
export REDIS_HOST=localhost:7000,localhost:7001,localhost:7002,localhost:7003,localhost:7004,localhost:7005
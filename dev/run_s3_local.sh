docker run --rm -it -p 4566:4566 -p 4571:4571 -p 8082:8080 --env SERVICES=s3 --name=s3local localstack/localstack-full
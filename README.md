## Image Collector
###### This app created for saving pictures by tags from DeviantArt.

###### Technologies: Micronaut, AWS Lambdas, S3, DeviantArt API

---

#### Documentation:

##### 1. Micronaut 2.5.4 Documentation

- [User Guide](https://docs.micronaut.io/2.5.4/guide/index.html)
- [API Reference](https://docs.micronaut.io/2.5.4/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/2.5.4/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)

---

##### 2. Handler

[AWS Lambda Handler](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)

```Handler: io.micronaut.function.aws.proxy.MicronautLambdaHandler```

```Handler: io.micronaut.function.aws.MicronautRequestHandler```

---

##### 3. Feature aws-lambda documentation

- [Micronaut AWS Lambda Function documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#lambda)

---

##### 4. APIs

- [Instagram API](https://www.instagram.com/developer/) // TODO: add Instagram API to the project
- [DeviantArt API](https://www.deviantart.com/developers/)

---

##### 5. For interacting with S3 you can use Meet Commandeer
[MeetCommandeer](https://getcommandeer.com/)

---

##### 6. LocalStack
- Github: ```https://github.com/localstack/localstack```

- HealthCheck: ```http://localhost:4566/health?reload```

##### 7. Guides
- Running AWS locally with LocalStack: ```https://blog.jcore.com/2020/04/running-aws-locally-with-localstack/```
- Configure java AWS sdk client to write to local S3 bucket (localstack): ```https://stackoverflow.com/questions/53012762/configure-java-aws-sdk-client-to-write-to-local-s3-bucket-localstack```
- AWS S3 + Java: ```https://www.baeldung.com/aws-s3-java```

##### 8. Problems
- LocalStack doesn't support ```amazonS3Client.listBuckets()``` method





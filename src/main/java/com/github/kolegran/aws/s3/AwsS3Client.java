package com.github.kolegran.aws.s3;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.config.model.NoSuchBucketException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import io.micronaut.context.annotation.Value;

import javax.inject.Singleton;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;

@Singleton
public class AwsS3Client {

    private final AmazonS3 amazonS3;
    private final Bucket bucket;

    public AwsS3Client(
        @Value("${aws.s3.bucket.name}") String bucketName,
        @Value("${localstack.s3.url}") String localstackS3
    ) {
        final String s3Url = System.getenv().getOrDefault("S3_URL", localstackS3);
        final String region = System.getenv().getOrDefault("REGION", Regions.US_EAST_1.getName());

        this.amazonS3 = AmazonS3ClientBuilder.standard()
            .withPathStyleAccessEnabled(true)
            .withEndpointConfiguration(new EndpointConfiguration(s3Url, region))
            .build();

        this.bucket = amazonS3.createBucket(bucketName);
    }

    public Map<String, List<URL>> getImageUrlsByTag(List<String> tags) {
        final String bucketName = bucket.getName();
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            throw new NoSuchBucketException("Bucket does not exist: " + bucketName);
        }

        final Map<String, List<URL>> result = new HashMap<>(); // tag, links on images from S3
        for (String tag : tags) {
            // TODO: handle the case when there is no tag on the S3
            final ObjectListing objectListing = amazonS3.listObjects(bucketName, tag);
            final List<S3ObjectSummary> imagesByTag = objectListing.getObjectSummaries();
            result.put(tag, extractUrls(imagesByTag));
        }
        return result;
    }

    private List<URL> extractUrls(List<S3ObjectSummary> imagesByTag) {
        return imagesByTag.stream()
            .map(i -> amazonS3.getUrl(bucket.getName(), i.getKey()))
            .collect(Collectors.toList());
    }
}

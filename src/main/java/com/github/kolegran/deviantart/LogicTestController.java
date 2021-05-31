package com.github.kolegran.deviantart;

import com.github.kolegran.aws.s3.AwsS3Client;
import com.github.kolegran.deviantart.image.ImageInfo;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.net.URL;
import java.util.List;
import java.util.Map;

@Controller
// For quick manually testing >:)
public class LogicTestController {

    private final DeviantArtApiClient deviantArtApiClient;
    private final AwsS3Client awsS3Client;

    public LogicTestController(DeviantArtApiClient deviantArtApiClient, AwsS3Client awsS3Client) {
        this.deviantArtApiClient = deviantArtApiClient;
        this.awsS3Client = awsS3Client;
    }

    @Get
    public Map<String, List<ImageInfo>> getImagesInfo() {
        final List<DATag> daTags = List.of(
            new DATag("cat", 1, 10),
            new DATag("anime", 2, 10)
        );
        return deviantArtApiClient.browseTopImagesByTag(new DARequestParameters(daTags));
    }

    @Get("/s3/image-urls")
    public Map<String, List<URL>> getUrls() {
        return awsS3Client.getImageUrlsByTag(List.of("cat"));
    }
}

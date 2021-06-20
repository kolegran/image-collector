package com.github.kolegran.deviantart;

import com.github.kolegran.aws.s3.AwsS3Client;
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
    public void getImagesInfo() {
        final List<DATagInfo> daTagInfos = List.of(
            new DATagInfo("cat", 1, 10),
            new DATagInfo("anime", 2, 10)
        );
//        deviantArtApiClient.browseTopImages(new DARequestParameters(daTagInfos));
    }

    @Get("/s3/image-urls")
    public Map<String, List<URL>> getUrlsByTag() {
        return awsS3Client.getImageUrlsByFolderName(List.of("cat"));
    }
}

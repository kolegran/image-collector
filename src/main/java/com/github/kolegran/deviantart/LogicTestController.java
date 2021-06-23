package com.github.kolegran.deviantart;

import com.github.kolegran.aws.s3.AwsS3Client;
import com.github.kolegran.deviantart.image.ImageInfo;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
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
    public void getImageFiles() {
        final List<DATagInfo> daTagInfos = List.of(
            new DATagInfo("cat", 1, 10),
            new DATagInfo("anime", 2, 10)
        );
        final Map<String, List<File>> topImages = deviantArtApiClient.browseTopImages(new DARequestParameters(daTagInfos), new HashMap<>());
        System.out.println(topImages);
    }

    @Get("/browse")
    public void browseImages() {
        final List<DATagInfo> daTagInfos = List.of(
            new DATagInfo("cat", 1, 10),
            new DATagInfo("anime", 2, 10)
        );
        final Map<String, List<ImageInfo>> browse = deviantArtApiClient.browse(new DARequestParameters(daTagInfos));
        System.out.println(browse);
    }

    @Get("/s3/image-urls")
    public Map<String, List<URL>> getUrlsByTag() {
        return awsS3Client.getImageUrlsByFolderName(List.of("cat"));
    }
}

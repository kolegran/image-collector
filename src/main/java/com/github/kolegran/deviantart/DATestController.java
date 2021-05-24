package com.github.kolegran.deviantart;

import com.github.kolegran.deviantart.image.ImageInfo;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.Map;

@Controller
// For quick manually testing >:)
public class DATestController {

    private final DeviantArtApiClient deviantArtApiClient;

    public DATestController(DeviantArtApiClient deviantArtApiClient) {
        this.deviantArtApiClient = deviantArtApiClient;
    }

    @Get
    public Map<String, List<ImageInfo>> getImagesInfo() {
        final List<DATag> daTags = List.of(
            new DATag("car", 1, 10),
            new DATag("anime", 2, 10)
        );
        return deviantArtApiClient.browseTopImagesByTag(new DARequestParameters(daTags));
    }
}

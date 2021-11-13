package com.musiva.player;

import com.musiva.player.handler.ContentRangeParser;
import com.musiva.player.repository.Filename;
import com.musiva.player.repository.FilenamePolicy;
import com.musiva.player.repository.cache.ByteRange;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Controller
class TrackDataPlayerEndpoint {

    private final TrackDataResolver trackDataResolver;
    private final FilenamePolicy filenamePolicy;
    private final ContentRangeParser contentRangeParser;

    TrackDataPlayerEndpoint(TrackDataResolver trackDataResolver, FilenamePolicy filenamePolicy, ContentRangeParser contentRangeParser) {
        this.trackDataResolver = trackDataResolver;
        this.filenamePolicy = filenamePolicy;
        this.contentRangeParser = contentRangeParser;
    }

    @GetMapping("/api/player/{id}")
    Mono<TrackData> findById(@PathVariable UUID id, HttpServletRequest request) {
        final ByteRange byteRange = contentRangeParser.parseContentRange(request);
        final Filename filename = new Filename(id, filenamePolicy.currentExtension());

        final Optional<TrackData> trackData = trackDataResolver.resolveTrackData(filename, byteRange);
        return trackData.map(Mono::just).orElseGet(Mono::empty);
    }

}

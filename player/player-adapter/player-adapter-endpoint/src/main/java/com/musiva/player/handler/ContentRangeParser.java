package com.musiva.player.handler;

import com.musiva.player.repository.cache.ByteRange;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Component
public class ContentRangeParser {

    public ByteRange parseContentRange(HttpServletRequest httpServletRequest) {

        try {

            var header = httpServletRequest.getHeader("Content-Range");
            List<HttpRange> range = HttpRange.parseRanges(header);

            final Optional<HttpRange> found = range.stream().findFirst();
            return found
                    .map(httpRange -> new ByteRange(httpRange.getRangeStart(Long.MAX_VALUE), httpRange.getRangeEnd(Long.MAX_VALUE)))
                    .orElseGet(ByteRange::maximum);
        } catch (IllegalArgumentException e) {
            return ByteRange.maximum();
        }

    }

}

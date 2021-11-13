package com.musiva.player.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class TrackDataPlayerHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleByteRange(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body
                     (
                         """
                              Illegal 'Content-Range' format.
                              
                              Possible formats:               Content-Range: <unit> <range-start>-<range-end>/<size>
                                                              Content-Range: <unit> <range-start>-<range-end>/*
                                                              Content-Range: <unit> */<size>
                          """
                     );
    }
}

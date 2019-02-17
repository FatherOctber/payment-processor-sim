package com.fatheroctober.ppsim.customerservice.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class DefaultResponse<T> extends ResponseEntity<T> {
    private static HttpHeaders jsonHeaders = new HttpHeaders();

    static {
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }

    private DefaultResponse(HttpStatus status) {
        super(status);
    }

    private DefaultResponse(T body, MultiValueMap<String, String> headers, HttpStatus status) {
        super(body, headers, status);
    }

    private DefaultResponse(MultiValueMap<String, String> headers, HttpStatus status) {
        super(headers, status);
    }

    public static <T> DefaultResponse<T> successfull(T body) {
        return new DefaultResponse<T>(body, jsonHeaders, HttpStatus.OK);
    }

    public static <T> DefaultResponse<T> successfull() {
        return new DefaultResponse<T>(HttpStatus.OK);
    }

    public static DefaultResponse badRequestError() {
        return new DefaultResponse(jsonHeaders, HttpStatus.BAD_REQUEST);
    }

    public static DefaultResponse notFoundError() {
        return new DefaultResponse(jsonHeaders, HttpStatus.NOT_FOUND);
    }

    public static DefaultResponse generalError() {
        return new DefaultResponse(jsonHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

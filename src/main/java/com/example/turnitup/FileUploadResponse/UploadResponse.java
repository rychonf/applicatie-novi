package com.example.turnitup.FileUploadResponse;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadResponse {

    String fileName;
    String contentType;
    String url;

    public UploadResponse(String fileName, String contentType, String url) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.url = url;
    }
}

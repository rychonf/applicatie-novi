package com.example.turnitup.FileUploadResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UploadResponse {

    String fileName;
    String contentType;
    String url;

}

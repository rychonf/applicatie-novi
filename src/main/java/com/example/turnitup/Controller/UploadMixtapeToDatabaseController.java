package com.example.turnitup.Controller;

import com.example.turnitup.FileUploadResponse.UploadResponse;
import com.example.turnitup.Model.Mixtape;
import com.example.turnitup.Service.DatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping(value = "/mixtape")
public class UploadMixtapeToDatabaseController {

    private final DatabaseService databaseService;

    public UploadMixtapeToDatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @PostMapping(value = "/uploadToDB")
    public UploadResponse mixtapeUpload(@RequestParam("mixtape")MultipartFile mixtapeFile, @RequestParam("djId") Long id) throws IOException{
        Mixtape mixtape = databaseService.uploadFileDocument(mixtapeFile, id);
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadMixtapeFromDB/")
                .path(Objects.requireNonNull(mixtapeFile.getOriginalFilename())).toUriString();

        String contentType = mixtapeFile.getContentType();

        return new UploadResponse(mixtape.getFileName(), url, contentType);
    }

    @GetMapping(value = "/downloadFromDB/{mixtapeName}")
    ResponseEntity<byte[]> downloadMixtapeByName(@PathVariable String mixtapeName, HttpServletRequest request) {

        return databaseService.singleFileDownload(mixtapeName, request);
    }

    @GetMapping(value = "/downloadAsZip")
    public void downloadAsZip(@RequestParam("mixtapeName") String[] files, HttpServletResponse response) throws IOException{

        databaseService.getZipDownload(files, response);
    }

    @GetMapping(value = "/getAll")
    public Collection<Mixtape> getAllMixtapesFromDB() {
        return databaseService.getAllFromDB();
    }

    @PutMapping(value = "/playMixtape/{id}")
    public ResponseEntity<Mixtape> playMixtape(@PathVariable Long id){

        Mixtape doc = databaseService.playMixtape(id);

        return ResponseEntity.ok().body(doc);
    }


}

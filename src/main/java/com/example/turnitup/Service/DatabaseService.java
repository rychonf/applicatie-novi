package com.example.turnitup.Service;

import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.FileUploadResponse.UploadResponse;
import com.example.turnitup.Model.Mixtape;
import com.example.turnitup.Repository.DocumentFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class DatabaseService {
    private final DocumentFileRepository doc;


    public DatabaseService(DocumentFileRepository doc){
        this.doc = doc;
    }

    public Collection<Mixtape> getAllFromDB(){
        return doc.findAll();
    }

    public Mixtape uploadFileDocument(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Mixtape mixtape = new Mixtape();
        mixtape.setFileName(name);
        mixtape.setDocFile(file.getBytes());

        LocalDate dateUploaded = LocalDate.now();
        mixtape.setDateUploaded(dateUploaded);



//          Dit is voor de put mapping!
//        FileDocument addTimesPlayed = timesMixtapePlayed(fileDocument.getId(id));
        mixtape.setTimesPlayed(mixtape.getTimesPlayed());


        doc.save(mixtape);

        return mixtape;
    }

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request){

        Mixtape document = doc.findByFileName(fileName);

//        this mediaType decides witch type you accept if you only accept 1 type
//        MediaType contentType = MediaType.IMAGE_JPEG;
//        this is going to accept multiple types

//        String mimeType = request.getServletContext().getMimeType(document.getFileName());

//        for download attachment use next line
//        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=" + resource.getFilename()).body(resource);
//        for showing image in browser
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());

    }

    public List<UploadResponse> createMultipleUpload(MultipartFile[] files){
        List<UploadResponse> uploadResponseList = new ArrayList<>();
        Arrays.stream(files).forEach(file -> {

            String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Mixtape mixtape = new Mixtape();
            mixtape.setFileName(name);
            try {
                mixtape.setDocFile(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

            doc.save(mixtape);

//            next line makes url. example "http://localhost:8080/download/naam.jpg"
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(name).toUriString();

            String contentType = file.getContentType();

            UploadResponse response = new UploadResponse(name, contentType, url);

            uploadResponseList.add(response);
        });
        return uploadResponseList;
    }

    public void getZipDownload(String[] files, HttpServletResponse response) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            Arrays.stream(files).forEach(file -> {
                try {
                    createZipEntry(file, zos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            zos.finish();

            response.setStatus(200);
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=zipfile");
        }
    }

    public Resource downLoadFileDatabase(String fileName) {

        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFromDB/").path(fileName).toUriString();

        Resource resource;

        try {
            resource = new UrlResource(url);
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Issue in reading the file", exception);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public void createZipEntry(String file, ZipOutputStream zos) throws IOException {

        Resource resource = downLoadFileDatabase(file);
        ZipEntry zipEntry = new ZipEntry(Objects.requireNonNull(resource.getFilename()));
        try {
            zipEntry.setSize(resource.contentLength());
            zos.putNextEntry(zipEntry);

            StreamUtils.copy(resource.getInputStream(), zos);

            zos.closeEntry();
        } catch (IOException e) {
            System.out.println("some exception while zipping");
        }

    }

//    This method mimics a play button from a mixtape, everytime it is called it adds +1 to the times played count.

   public Mixtape playMixtape(Long id){
        if(doc.findById(id).isPresent()){
            Mixtape mixtape = doc.findById(id).get();

            mixtape.setTimesPlayed(mixtape.getTimesPlayed()+1);

            return doc.save(mixtape);

        } else {
            throw new RecordNotFoundException("No mixtape found with this id");
        }
   }

}

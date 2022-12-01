package com.example.turnitup.Service;

import com.example.turnitup.Exception.RecordNotFoundException;
import com.example.turnitup.FileUploadResponse.UploadResponse;
import com.example.turnitup.Model.DJ;
import com.example.turnitup.Model.Mixtape;
import com.example.turnitup.Repository.DJRepository;
import com.example.turnitup.Repository.DocumentFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @Autowired
    private DJRepository djRepository;


    public DatabaseService(DocumentFileRepository doc){
        this.doc = doc;
    }

    public Collection<Mixtape> getAllFromDB(){
        return doc.findAll();
    }

    public Mixtape uploadFileDocument(MultipartFile file, Long djId) throws IOException {

        String name = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Mixtape mixtape = new Mixtape();
        mixtape.setFileName(name);
        mixtape.setDocFile(file.getBytes());

        LocalDate dateUploaded = LocalDate.now();
        mixtape.setDateUploaded(dateUploaded);

        mixtape.setTimesPlayed(mixtape.getTimesPlayed());

        doc.save(mixtape);

        if (djRepository.findById(djId).isPresent()) {
            DJ dj = djRepository.findById(djId).get();

            dj.setMixtape(mixtape);
            djRepository.save(dj);
            return mixtape;

        } else throw new RecordNotFoundException("DJ does not exist");

    }

    public ResponseEntity<byte[]> singleFileDownload(String fileName, HttpServletRequest request){

        Mixtape document = doc.findByFileName(fileName);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + document.getFileName()).body(document.getDocFile());

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

package com.kitchensaver.backend.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import com.kitchensaver.backend.Service.FileStorageService;

@RestController
@RequestMapping("/api")
public class FileStorageController {

    @Value("${file.upload-dir}")
    private String uploadDir;
    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @GetMapping("/files/{filename}")
    public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {
        try {
            logger.info("here::: " + filename);
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(Files.readAllBytes(filePath));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('ADMIN', 'CABINET_MAKER', 'INSTALLER')")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            // Return the file access URL
            String fileDownloadUri = fileName;
            return ResponseEntity.ok(fileDownloadUri);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }
}

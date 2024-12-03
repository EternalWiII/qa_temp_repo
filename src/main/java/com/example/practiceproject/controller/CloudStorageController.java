package com.example.practiceproject.controller;

import com.example.practiceproject.service.CloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * CloudStorageController handles file upload and download requests to the cloud storage.
 * It manages file operations while ensuring the request contains a valid authorization token.
 */
@RestController
public class CloudStorageController {
    private final CloudStorageService cloudStorageService;

    @Autowired
    public CloudStorageController(CloudStorageService cloudStorageService) {
        this.cloudStorageService = cloudStorageService;
    }

    /**
     * Handles GET requests to download a file from cloud storage.
     *
     * @param filename the name of the file to download
     * @param token the Authorization token from the request header
     * @return a ResponseEntity containing the file resource, or a 404 status if the file is not found
     */
    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename,
                                             @RequestHeader("Authorization") String token) {
        // Remove "Bearer " prefix from the token
        String cleanToken = token.substring(7);

        // Pass the token to the service
        Resource file = cloudStorageService.downloadFile(filename, cleanToken);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * Handles POST requests to upload a file to cloud storage.
     *
     * @param file the file to be uploaded
     * @param token the Authorization token from the request header
     * @return a ResponseEntity with a success message if the file is uploaded successfully
     */
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file,
                                    @RequestHeader("Authorization") String token) {
        String cleanToken = token.substring(7);

        cloudStorageService.uploadFile(file, cleanToken);
        return ResponseEntity.ok(Map.of("message", "File uploaded successfully"));
    }
}

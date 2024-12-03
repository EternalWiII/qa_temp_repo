package com.example.practiceproject.service;

import com.example.practiceproject.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * CloudStorageService provides functionalities for uploading and downloading files
 * in the cloud storage. It handles file operations while ensuring the user is authenticated
 * via JWT token.
 */
@Service
public class CloudStorageService {

    private final JwtTokenUtils jwtTokenUtils;

    @Autowired
    public CloudStorageService(JwtTokenUtils jwtTokenUtils) {
        this.jwtTokenUtils = jwtTokenUtils;
    }

    /**
     * Uploads a file to the cloud storage for the authenticated user.
     *
     * @param file the MultipartFile to be uploaded
     * @param token the JWT token for authentication
     * @throws IllegalArgumentException if the file is empty or cannot be uploaded
     */
    public void uploadFile(MultipartFile file, String token) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File is empty");
            }

            String fileName = file.getOriginalFilename();

            String username = jwtTokenUtils.getUsername(token);

            try (InputStream inputStream = file.getInputStream()) {
                Path destinationFile = Paths.get("storage/" + username).resolve(
                                Paths.get(fileName))
                        .normalize().toAbsolutePath();
                Files.createDirectories(destinationFile.getParent());
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException("File could not be uploaded");
        }
    }

    /**
     * Downloads a file from the cloud storage for the authenticated user.
     *
     * @param fileName the name of the file to be downloaded
     * @param cleanToken the JWT token for authentication
     * @return a Resource representing the file if found
     * @throws IllegalArgumentException if the file is not found
     */
    public Resource downloadFile(String fileName, String cleanToken) {
        try {
            // Extract the username from the JWT token
            String username = jwtTokenUtils.getUsername(cleanToken);

            System.out.println(username);

            // Use the username in the file path
            Path filePath = Paths.get("storage/" + username).resolve(
                            Paths.get(fileName))
                    .normalize().toAbsolutePath();
            System.out.println(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new IllegalArgumentException("File not found");
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("File not found");
        }
    }
}


package com.cos.iter.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class AzureService {
    @Value("${azure.connect.string}")
    private String connectString;

    @Value("${file.path}")
    private String uploadFolder;

    public String uploadToCloudAndReturnFileName(MultipartFile file, String ContainerName) throws IOException {
        final UUID uuid = UUID.randomUUID();
        final String imageFilename = uuid + "_" + file.getOriginalFilename();
        final Path imageFilepath = Paths.get(uploadFolder + imageFilename);

        try {
            Files.write(imageFilepath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        final BlobContainerClient container = new BlobContainerClientBuilder()
                .connectionString(connectString)
                .containerName(ContainerName)
                .buildClient();

        final BlobClient blob = container.getBlobClient(imageFilename);
        blob.uploadFromFile(uploadFolder + imageFilename);

        try {
            Files.delete(imageFilepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFilename;
    }
}

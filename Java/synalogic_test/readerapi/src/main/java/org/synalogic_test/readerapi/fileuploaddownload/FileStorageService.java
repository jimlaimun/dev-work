package org.synalogic_test.readerapi.fileuploaddownload;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileLocation;
    
    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileLocation = Paths.get(fileStorageProperties.getUploadDir())
        .toAbsolutePath().normalize();

        try
        {
            Files.createDirectories(this.fileLocation);
        }catch(Exception ex)
        {
            throw new FileStorageException("Error creating file directory for uploaded file", ex);
        }
    }
    
    public String storeFile(MultipartFile file)
    {

        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            //error check for  invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Filename cotains invalid characters" + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileStorageException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException("File not found " + fileName, ex);
        }
    }
}

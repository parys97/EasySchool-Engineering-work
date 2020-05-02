package com.example.easyschool.controller;

import com.example.easyschool.domain.FileModel;
import com.example.easyschool.domain.Group;
import com.example.easyschool.domain.Views;
import com.example.easyschool.repo.FileRepository;
import com.example.easyschool.repo.ProjectRepo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {

    private final FileRepository fileRepository;

    @Value("${pathUploadFiles}")
    private String uploadPath;

    @Autowired
    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /*
     * MultipartFile Upload
     */
    @PostMapping("/upload")
    @JsonView(Views.IdName.class)
    public FileModel uploadMultipartFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                String filePath = uploadPath + "/" + resultFilename;
                file.transferTo(new File(filePath));
                FileModel fileModel = new FileModel(filePath, file.getContentType(), file.getOriginalFilename());
                return fileRepository.save(fileModel);
            }
        } catch (  Exception e) {
        }
        return null;
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws IOException {
        Optional<FileModel> fileOptional = fileRepository.findById(id);

        if(fileOptional.isPresent()) {
            File file = new File(fileOptional.get().getPath());
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(fileContent);
        }

        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("{id}")
    @JsonView(Views.IdName.class)
    public FileModel getOne(@PathVariable("id") FileModel fileModel) {
        return fileModel;
    }

}


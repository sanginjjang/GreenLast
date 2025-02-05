package com.example.greenlast.file;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;

/**
 * packageName    : com.example.greenlast.controllers.api.dongha
 * fileName       : FileController
 * author         : 이동하
 * date           : 25. 1. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 27.        이동하       최초 생성
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("fileType") String fileType,
                                             @RequestParam(value = "id", required = false) int id

    ) throws IOException {
        System.out.println(file.getOriginalFilename());
        FileEntity savedFile = fileService.saveFile(file, fileType, id);
        return ResponseEntity.ok("파일 업로드 성공! 저장된 경로: " + savedFile.getFileUrl());
    }

    //파일 넘버 얻기
    @GetMapping("/{fileNo}")
    public ResponseEntity<FileEntity> getFile(@PathVariable int fileNo) {
        FileEntity file = fileService.getFileById(fileNo);
        return ResponseEntity.ok(file);
    }

    // 모든 파일 얻기
    @GetMapping
    public ResponseEntity<List<FileEntity>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    // 삭제
    @DeleteMapping("/{fileNo}")
    public ResponseEntity<String> deleteFile(@PathVariable int fileNo) {
        fileService.deleteFile(fileNo);
        return ResponseEntity.ok("파일이 성공적으로 삭제되었습니다.");
    }

    // 다운로드
    @GetMapping("/download/{fileNo}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int fileNo) throws MalformedURLException {
        FileEntity file = fileService.getFileById(fileNo);
        Resource resource = new UrlResource(Paths.get("C:/upload-dir/" + file.getFileNewName()).toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileOldName() + "\"")
                .body(resource);
    }
}

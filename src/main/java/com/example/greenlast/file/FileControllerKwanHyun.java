package com.example.greenlast.file;

import com.example.greenlast.service.kwanhyun.CommunityService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : com.example.greenlast.controllers.api.dongha
 * fileName       : FileController
 * author         : 노관현
 * date           : 25. 2. 17.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 17.        노관현       최초 생성
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileControllerKwanHyun {
    private final FileService fileService;
    private final CommunityService communityService;

    @PostMapping("/upload/kwanhyun")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestPart("file") MultipartFile file,
                                                          @RequestParam("fileType") String fileType
                                                         ) throws IOException {

        int id = communityService.getPostId() + 1;

        FileEntity savedFile = fileService.saveFile(file, fileType, id);

        Map<String, Object> result = new HashMap<>();
        result.put("sFileURL", savedFile.getFileUrl());
        result.put("bNewLine", true);
        result.put("sFileName", savedFile.getFileOldName());

        return ResponseEntity.ok(result);
    }

}

package com.example.greenlast.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * packageName    : com.example.greenlast.service.dongha
 * fileName       : FileService
 * author         : 이동하
 * date           : 25. 1. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 27.        이동하       최초 생성
 */
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Value("${file.upload.path}")  // application.properties 에서 경로 가져오기
    private String uploadPath;

    public FileEntity saveFile(MultipartFile multipartFile, String fileGubnCode, String fileRefNo) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExt;
        String fileUrl = "/uploads/" + newFileName;  // 💡 DB에 저장되는 경로

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileGubnCode(fileGubnCode);
        fileEntity.setFileRefNo(fileRefNo);
        fileEntity.setFileOldName(originalFilename);
        fileEntity.setFileNewName(newFileName);
        fileEntity.setFileExt(fileExt);
        fileEntity.setFileSize((int) multipartFile.getSize());
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setFileSeq(1);

        // 📌 파일이 실제로 저장되는 경로 확인!
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 📌 파일 저장 경로 확인
        File file = new File(uploadPath + "/" + newFileName);
        multipartFile.transferTo(file);

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFileById(int fileNo) {
        return fileRepository.findById(fileNo).orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(int fileNo) {
        FileEntity fileEntity = getFileById(fileNo);
        File file = new File("C:/upload-dir/" + fileEntity.getFileNewName());
        if (file.exists()) {
            file.delete();
        }
        fileRepository.deleteById(fileNo);
    }

}

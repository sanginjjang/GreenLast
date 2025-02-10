package com.example.greenlast.file;

import com.example.greenlast.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileDao_Sangin fileDao;

    @Value("${file.upload.path}")
    private String uploadPath;

    public FileEntity saveFile(MultipartFile multipartFile, String fileType, int id) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileExt = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String newFileName = UUID.randomUUID().toString() + "." + fileExt;

        String subFolder = switch (fileType) {
            case "introduce" -> "introduce";
            case "post" -> "post";
            case "profile" -> "profile";
            case "thumbnail" -> "thumbnail";
            default -> "others";
        };

        String fileDir = uploadPath + "/" + subFolder;
        String fileUrl = "/uploads/" + subFolder + "/" + newFileName;

        File uploadDir = new File(fileDir);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        File file = new File(fileDir, newFileName);
        multipartFile.transferTo(file);

        // ✅ 파일 메타데이터 저장
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileType(fileType);
        fileEntity.setFileOldName(originalFilename);
        fileEntity.setFileNewName(newFileName);
        fileEntity.setFileExt(fileExt);
        fileEntity.setFileSize((int) multipartFile.getSize());
        fileEntity.setFileUrl(fileUrl);
        fileEntity.setFileSeq(1);

        FileEntity savedFile = fileRepository.save(fileEntity);
        int refNo = savedFile.getFileNo();

        fileRepository.save(savedFile);

        // ✅ Upsert 처리 (Update 실패 시 Insert 수행)
        switch (fileType) {
            case "introduce" -> {
                if (fileDao.updateIntroduce((Integer) id, refNo) == 0) {
                    fileDao.insertIntroduce((Integer) id, refNo);
                }
            }
            case "post" -> {
                if (fileDao.updatePostFile((Integer) id, refNo) == 0) {
                    fileDao.insertPostFile((Integer) id, refNo);
                }
            }
            case "thumbnail" -> {
                if (fileDao.updateThumbnail(id, refNo) == 0) {
                    fileDao.insertThumbnail(id, refNo);
                }
            }

            case "profile" -> fileDao.updateProfile(SecurityUtil.getCurrentUserId(), refNo);
        }

        return savedFile;
    }

    public FileEntity getFileById(int fileNo) {
        return fileRepository.findById(fileNo)
                .orElseThrow(() -> new IllegalArgumentException("파일이 존재하지 않습니다."));
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteFile(int fileNo) {
        FileEntity fileEntity = getFileById(fileNo);
        String fullFilePath = uploadPath + fileEntity.getFileUrl().replace("/uploads", "");

        File file = new File(fullFilePath);
        if (file.exists()) {
            file.delete();
        }

        fileRepository.deleteById(fileNo);
    }
}

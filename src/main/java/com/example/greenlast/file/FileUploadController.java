package com.example.greenlast.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.example.greenlast.file
 * fileName       : FileUploadController
 * author         : 이동하
 * date           : 25. 1. 31.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 31.        이동하       최초 생성
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    @GetMapping("/upload")
    public String upload() {
        return "/dongha/upload";
    }
}

package com.example.greenlast.controllers.api.dongha;

import com.example.greenlast.dto.ClassDTO;
import com.example.greenlast.dto.ClassMainDTO;
import com.example.greenlast.service.dongha.ClassMainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * packageName    : com.example.greenlast.controllers.api.dongha
 * fileName       : ClassMainController
 * author         : 이동하
 * date           : 25. 1. 25.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 25.        이동하       최초 생성
 */
@RestController
@RequestMapping("/AllMain-api")
@RequiredArgsConstructor
public class  ClassMainController {
    private final ClassMainService classMainService;

    @GetMapping("/class")
    public ResponseEntity<List<ClassMainDTO>> classMain() {
        List<ClassMainDTO> classMain = classMainService.getClassMain();
        return ResponseEntity.ok(classMain);
    }

}

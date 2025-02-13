package com.example.greenlast.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContentRequestDTO {
    private List<BlockData> content;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class BlockData {
        private String type; // "single" or "double"
        private List<ElementData> elements;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ElementData {
        private String type; // "text" or "image"
        private String content;

    }
}
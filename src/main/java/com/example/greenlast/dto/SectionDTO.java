package com.example.greenlast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {
    private Long sectionId;        // 섹션 ID
    private String title;          // 섹션 제목
    private List<Lesson> lessons;  // 수업 목록

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Lesson {
        private Long lessonId;     // 수업 ID
        private String title;      // 수업 제목
        private Video video;       // 비디오 정보

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Video {
            private String fileName;    // 파일 이름
            private String fileSize;    // 파일 크기
            private boolean hasVideo;   // 비디오 존재 여부
        }
    }
}
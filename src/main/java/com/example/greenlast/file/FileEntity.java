package com.example.greenlast.file;

import jakarta.persistence.*;
import lombok.*;

/**
 * packageName    : com.example.greenlast.entity
 * fileName       : FileEntity
 * author         : 이동하
 * date           : 25. 1. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 27.        이동하       최초 생성
 */
@Entity
@Data
@Table(name = "file")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private int fileNo;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_ref_no")
    private int fileRefNo;

    @Column(name = "file_old_name")
    private String fileOldName;

    @Column(name = "file_new_name")
    private String fileNewName;

    @Column(name = "file_ext")
    private String fileExt;

    @Column(name = "file_size")
    private Integer fileSize;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_seq")
    private Integer fileSeq;
}

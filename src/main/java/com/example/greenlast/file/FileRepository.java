package com.example.greenlast.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName    : com.example.greenlast.repository
 * fileName       : FileRepository
 * author         : 이동하
 * date           : 25. 1. 27.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 1. 27.        이동하       최초 생성
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}

package com.sparta.trelloproject.domain.uploadFile.repository;

import com.sparta.trelloproject.domain.uploadFile.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}

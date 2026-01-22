package com.rbc.dowjones.repository.repository;

import com.rbc.dowjones.repository.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadedFileRepository extends JpaRepository<UploadedFile,Long> {

    boolean existsByFileHash(String fileHash);
}

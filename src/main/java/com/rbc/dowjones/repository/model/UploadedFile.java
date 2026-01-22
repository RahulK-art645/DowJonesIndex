package com.rbc.dowjones.repository.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "uploaded_files", uniqueConstraints ={ @UniqueConstraint(columnNames = "fileHash")})
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_hash", unique = true)
    private String fileHash;

    private LocalDateTime uploadedAt=LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}

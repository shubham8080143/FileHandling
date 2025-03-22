package com.FileHandling.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FileHandling.Entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}

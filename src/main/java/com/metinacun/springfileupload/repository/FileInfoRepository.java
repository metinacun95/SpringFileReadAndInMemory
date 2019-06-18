package com.metinacun.springfileupload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metinacun.springfileupload.model.FileInfo;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Integer>{

}

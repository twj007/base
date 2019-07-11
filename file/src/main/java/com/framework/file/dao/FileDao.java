package com.framework.file.dao;

import com.framework.file.pojo.file.UploadFile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao {

    Long saveFile(UploadFile uploadFile);

    List<UploadFile> getFileList();
}

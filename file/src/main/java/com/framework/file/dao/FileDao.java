package com.framework.file.dao;

import com.framework.file.pojo.UploadFile;

import java.util.List;

public interface FileDao {

    Long saveFile(UploadFile uploadFile);

    List<UploadFile> getFileList();
}

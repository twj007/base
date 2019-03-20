package com.framework.file.dao;

import com.framework.file.pojo.UploadFile;

public interface FileDao {

    Long saveFile(UploadFile uploadFile);
}

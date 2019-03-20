package com.framework.file.service;

import com.framework.file.dao.FileDao;
import com.framework.file.pojo.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {

    @Autowired
    private FileDao fileDao;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Boolean saveFilePath(UploadFile uploadFile){
        Long count = fileDao.saveFile(uploadFile);
        if(count >= 1){
            return true;
        }
        return false;
    }

}

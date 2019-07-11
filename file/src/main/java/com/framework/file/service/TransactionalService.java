package com.framework.file.service;

import com.framework.file.dao.FileDao;
import com.framework.file.pojo.file.UploadFile;
import com.framework.file.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileService fileService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void insert1() {
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName("zz");
        uploadFile.setFileNewName("xx");
        uploadFile.setCreateDate(DateFormatUtil.getNow("yyyy-MM-dd"));
        Long num = fileDao.saveFile(uploadFile);
        if(num >= 1){
            fileService.insert2();

        }
    }


}

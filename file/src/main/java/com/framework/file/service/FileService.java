package com.framework.file.service;

import com.framework.file.dao.FileDao;
import com.framework.file.pojo.file.UploadFile;
import com.framework.file.util.DateFormatUtil;
import com.framework.file.util.LOGLEVEL;
import com.framework.file.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private FileDao fileDao;

    public Boolean saveFilePath(UploadFile uploadFile) throws Exception {
        Long count = fileDao.saveFile(uploadFile);
        int i = 1 / 0;
        if(count >= 1){

            return true;
        }
        return false;
    }

    public boolean upload(MultipartFile[] uploads) {

        boolean flag = (boolean) transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {
                for(MultipartFile upload : uploads) {
                    String date = DateFormatUtil.getFormateDateString("yyyy-MM-dd");
                    String uuid = UUID.randomUUID().toString() + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
                    String path = uploadPath + date;
                    File dict = new File(path);
                    if (!dict.exists()) {
                        dict.mkdir();
                    }
                    path = path + "/" + uuid;
                    File f = new File(path);
                    try {
                        upload.transferTo(f);
                        UploadFile uploadFile = new UploadFile();
                        uploadFile.setFileName(upload.getOriginalFilename());
                        uploadFile.setCreateDate(date);
                        uploadFile.setSubfolder(date);
                        uploadFile.setFilePath(path);
                        uploadFile.setFileNewName(path.substring(path.lastIndexOf("/")+1));
                        if(!saveFilePath(uploadFile)){
                            return false;
                        }

                    }  catch (Exception e){
                        LoggerUtil.log(LOGLEVEL.ERROR, "【exception】: {}", e.fillInStackTrace());
                        f.delete();
                        //如果里面做了catch操作，就必须手动回滚
                        transactionStatus.setRollbackOnly();
                        return false;
                    }
                }
                return true;
            }
        });
        return flag;
    }

    public List<UploadFile> getFileList() {

        try{
            return fileDao.getFileList();
        }catch (Exception e){
            LoggerUtil.log(LOGLEVEL.ERROR, "【exception】: {}",e.fillInStackTrace());
            return null;
        }
    }


    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void insert2(){
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName("zz2");
        uploadFile.setFileNewName("xx2");
        uploadFile.setCreateDate(DateFormatUtil.getFormateDateString("yyyy-MM-dd"));
        fileDao.saveFile(uploadFile);
        throw new RuntimeException();
    }
}

package com.framework.file.service;


import com.framework.file.dao.FileDao;
import com.framework.file.pojo.file.UploadFile;
import com.framework.file.util.DateFormatUtil;
import com.framework.file.util.LOGLEVEL;
import com.framework.file.util.LoggerUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
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
import java.io.IOException;
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

//    @Value("#{accessKey}")
    private String accessKey = "AKIDTzPeomTdcS9jEcR2zlwriU7av571ElQw";
//    @Value("#{secretKey}")
    private String secretKey = "d7Xr065udBf4I4kEXfdQgLI9pVqnsoZg";
//    @Value("#{path}")
    private String path = "https://test-1258701698.cos.ap-shanghai.myqcloud.com/";
//    @Value("#{prefix}")
    private String prefix = "demo";
//    @Value("#{bucket}")
    private String bucket = "ap-shanghai";
//    @Value("#{bucketName}")
    private String bucketName = "test-1258701698";


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
                // 1 初始化用户身份信息（secretId, secretKey）。
                COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
                // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
                // clientConfig中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者接口文档 FAQ 中说明。
                ClientConfig clientConfig = new ClientConfig(new Region(bucket));
                // 3 生成 cos 客户端。
                COSClient cosClient = new COSClient(cred, clientConfig);
                // bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式

                for(MultipartFile upload : uploads) {

                    // 指定要上传到 COS 上对象键
                    String key = prefix + "/" + UUID.randomUUID().toString() + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
                    File localFile = null;
                    PutObjectRequest putObjectRequest = null;
                    PutObjectResult putObjectResult = null;
                    try {
                        localFile = File.createTempFile("temp", null);
                        upload.transferTo(localFile);


                        putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
                        putObjectResult = cosClient.putObject(putObjectRequest);

                    } catch (IOException e) {
                        e.printStackTrace();
                        cosClient.deleteObject(bucketName, key);
                        transactionStatus.setRollbackOnly();
                        return false;
                    }
//                    String date = DateFormatUtil.getFormateDateString("yyyy-MM-dd");
//                    String uuid = UUID.randomUUID().toString() + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
//                    String path = uploadPath + date;
//                    File dict = new File(path);
//                    if (!dict.exists()) {
//                        dict.mkdir();
//                    }
//                    path = path + "/" + uuid;
//                    File f = new File(path);
//                    try {
//                        upload.transferTo(f);
//                        UploadFile uploadFile = new UploadFile();
//                        uploadFile.setFileName(upload.getOriginalFilename());
//                        uploadFile.setCreateDate(date);
//                        uploadFile.setSubfolder(date);
//                        uploadFile.setFilePath(path);
//                        uploadFile.setFileNewName(path.substring(path.lastIndexOf("/")+1));
//                        if(!saveFilePath(uploadFile)){
//                            return false;
//                        }
//
//                    }  catch (Exception e){
//                        LoggerUtil.log(LOGLEVEL.ERROR, "【exception】: {}", e.fillInStackTrace());
//                        f.delete();
//                        //如果里面做了catch操作，就必须手动回滚
//                        transactionStatus.setRollbackOnly();
//                        return false;
//                    }
                }
                cosClient.shutdown();
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

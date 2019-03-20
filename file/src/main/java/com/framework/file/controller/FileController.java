package com.framework.file.controller;

import com.framework.file.pojo.UploadFile;
import com.framework.file.service.FileService;
import com.framework.file.util.DateFormatUtil;
import com.framework.file.util.LOGLEVEL;
import com.framework.file.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Autowired
    private FileService fileService;


    @GetMapping("/")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/index");
        return mav;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(MultipartFile[] uploads){
        if(uploads == null || uploads.length == 0){
            return ResponseEntity.ok("文件不存在");
        }else{
            for(MultipartFile upload : uploads) {
                String date = DateFormatUtil.getFormateDateString("yyyy-MM-dd");
                String uuid = UUID.randomUUID().toString() + upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
                uploadPath = uploadPath + date;
                File dict = new File(uploadPath);
                if (!dict.exists()) {
                    dict.mkdir();
                }
                uploadPath = uploadPath + "/" + uuid;
                File f = new File(uploadPath);
                try {
                    upload.transferTo(f);
                    UploadFile uploadFile = new UploadFile();
                    uploadFile.setFileName(upload.getOriginalFilename());
                    uploadFile.setCreateDate(date);
                    uploadFile.setFilePath(uploadPath);
                    uploadFile.setFileNewName(uploadPath.substring(uploadPath.lastIndexOf("/")));
                    fileService.saveFilePath(uploadFile);

                } catch (IOException e) {
                    LoggerUtil.log(LOGLEVEL.ERROR, "【io exception】：{}", e.fillInStackTrace());
                    return ResponseEntity.ok("文件上传异常");
                } catch (Exception e){
                    LoggerUtil.log(LOGLEVEL.ERROR, "【exception】: {}", e.fillInStackTrace());
                    return ResponseEntity.ok("文件保存出错");
                }
            }
        }
        return ResponseEntity.ok("上传成功");
    }
}

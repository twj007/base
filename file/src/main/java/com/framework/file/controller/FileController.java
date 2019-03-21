package com.framework.file.controller;

import com.framework.file.pojo.UploadFile;
import com.framework.file.service.FileService;
import com.framework.file.service.TransactionalService;
import com.framework.file.util.LOGLEVEL;
import com.framework.file.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.*;
import java.util.List;

@RestController
public class FileController {


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

            if(fileService.upload(uploads)){
                return ResponseEntity.ok("上传成功");
            }
        }
        return ResponseEntity.ok("上传失败");
    }

    @GetMapping("/listFile")
    public ModelAndView listFile(ModelAndView modelAndView){
        modelAndView.setViewName("listFile");
        List<UploadFile> uploadFileList = fileService.getFileList();
        modelAndView.addObject(uploadFileList);
        return modelAndView;
    }

    @GetMapping("/downloadFile/_{fileName}")
    public void downloadFile(@RequestParam(value = "filePath")String filePath, @PathVariable("fileName")String fileName, HttpServletResponse response){
        File f = new File(filePath);
        if(f.exists()){
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
            try {
                int length = (int)f.length();
                byte[] bytes = new byte[length];
                FileInputStream is = new FileInputStream(f);
                BufferedInputStream fis = new BufferedInputStream(is);
                OutputStream os = response.getOutputStream();
                int i = fis.read(bytes);
                while(i != -1){
                    os.write(bytes, 0, i);
                    i = fis.read(bytes);
                }
            } catch (FileNotFoundException e) {
                LoggerUtil.log(LOGLEVEL.ERROR, "【file not found exception】:{}", e.fillInStackTrace());
            } catch (IOException e){
                LoggerUtil.log(LOGLEVEL.ERROR, "【io exception】:{}", e.fillInStackTrace());
            }

        }
    }

    @GetMapping(value = "/listFileDiff/{folder}_{fileName}")
    public ModelAndView listFileDiff(ModelAndView modelAndView, @PathVariable("folder") String folder, @PathVariable("fileName")String fileName){
        modelAndView.setViewName("listFile");
        List<UploadFile> uploadFileList = fileService.getFileList();
        modelAndView.addObject(uploadFileList);
        return modelAndView;
    }

    @Autowired
    private TransactionalService transactionalService;

    @GetMapping("/insert")
    public ResponseEntity insert(){
        transactionalService.insert1();
        return ResponseEntity.ok("");
    }
}

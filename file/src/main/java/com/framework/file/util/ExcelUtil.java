package com.framework.file.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/***
 **@project: base
 **@description: excel util
 **@Author: twj
 **@Date: 2019/04/10
 **/
public class ExcelUtil {

    public static List<T> importExcel(MultipartFile[] uploads, Object object) throws Exception {
        List<T> datas = null;
        for(MultipartFile upload : uploads) {
            String dot = upload.getOriginalFilename().substring(upload.getOriginalFilename().lastIndexOf("."));
            Workbook book = null;

            if (".xls".equals(dot)) {
                book = new HSSFWorkbook(upload.getInputStream());
            } else if (".xlsx".equals(dot)) {
                book = new XSSFWorkbook(upload.getInputStream());
            } else {
                throw new RuntimeException("your upload file must end with .xls or .xlsx");
            }


        }

        return datas;
    }
}

package com.framework.file.pojo.file;

import lombok.*;
import org.apache.ibatis.type.Alias;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//@Alias("UploadFile")
public class UploadFile {

    private Long fileId;

    private String fileName;

    private String filePath;

    private String fileNewName;

    private String createDate;

    private String subfolder;


}

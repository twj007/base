package com.framework.file.pojo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {

    private Long fileId;

    private String fileName;

    private String filePath;

    private String fileNewName;

    private String createDate;
}

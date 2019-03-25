package com.framework.file.controller;

import com.framework.file.FileApplication;
import com.framework.file.pojo.user.User;
import com.framework.file.service.user.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = FileApplication.class)
@AutoConfigureMockMvc
public class FileApplicationTest {

    @Before
    public void before(){

    }

    @After
    public void after(){

    }



    @Autowired
    MockMvc mockMvc;

    @Test
    public void testFileUpload() throws Exception {
        File file = new File("upload.txt");
        DiskFileItem fileItem = (DiskFileItem) new DiskFileItemFactory().createItem("file",
                MediaType.TEXT_PLAIN_VALUE, true, file.getName());

        try (InputStream input = new FileInputStream(file); OutputStream os = fileItem.getOutputStream()) {
            IOUtils.copy(input, os);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file: " + e, e);
        }

        MockMultipartFile multi = new MockMultipartFile("upload.txt", fileItem.get());
        MockHttpServletRequestBuilder request = null;
//        request = MockMvcRequestBuilders.fileUpload("/mapManager/onModify").file(multi);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/upload")
//                .header("content-type", "multipart/form-data")
//                .requestAttr("upload", multi))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        String result =  mockMvc.perform(
                MockMvcRequestBuilders
                        .fileUpload("/upload")
                        .file(multi)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertEquals("上传成功", result);

    }

    @Test
    public void testEncoder(){
        System.out.println();
    }

    @Autowired
    UserService userService;

    @Test
    public void testUserService(){
        User u = new User();
        u.setUsername("test");
        Assert.assertEquals(Long.valueOf(1), userService.checkUserExists(u));
        u.setPassword("ok");
        Assert.assertNotEquals(null, userService.login(u));
        Assert.assertNotEquals(null, userService.getContactList(u));
        u.setEmail("test@qq.com");
        u.setId(Long.valueOf(7));
        Assert.assertEquals(true, userService.updateUser(u));
    }
}


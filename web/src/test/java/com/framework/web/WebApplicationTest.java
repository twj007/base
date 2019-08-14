package com.framework.web;

import com.framework.web.service.WebService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class)
@AutoConfigureMockMvc
public class WebApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(WebApplicationTest.class);

    @Before
    public void before(){

    }

    @After
    public void after(){

    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebService webService;

    @Test
    public void test() throws Exception {


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/test"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(200, status);
        Assert.assertEquals("ok", result.getResponse().getContentAsString());
    }

    @Test
    public void testService() throws Exception{

        Assert.assertEquals("ok", webService.test());
    }

    @Test
    public void testDao() throws Exception{
        System.out.println(webService.test2());
    }

}

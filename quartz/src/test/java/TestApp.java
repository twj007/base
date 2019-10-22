import com.quartz.LoginAuthenticationApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import java.net.URI;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LoginAuthenticationApp.class)
@AutoConfigureMockMvc
public class TestApp {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test1() throws Exception {
        String response =
                mockMvc.perform(MockMvcRequestBuilders
                .get(URI.create("/job/edit/jordan"))
                .param("name", "jordan")
                .param("group", "DEFAULT")
                .param("description", "change description")
                .param("cronExpression", "0,1,2 * * * * ? ")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        Assert.assertNotEquals(null, response);
    }

//    @Test
//    public void test2() throws Exception {
//        String response =
//                mockMvc.perform(
//                            MockMvcRequestBuilders
//                                .get(URI.create("/"))
//                                .param("", "")
//                        )
//                        .andReturn()
//                            .getResponse()
//                                .getContentAsString();
//    }
}

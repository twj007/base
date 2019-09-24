import com.cas.LoginAuthenticationApp;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/09/10
 **/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoginAuthenticationApp.class)
@AutoConfigureMockMvc
public class Test {

    @Autowired
    MockMvc mockMvc;

    @org.junit.Test
    public void testConverter() throws Exception {
        String result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URI.create("/authentication/test")
        ).param("date", "2019-01-20 10:10:00"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
        String result2 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URI.create("/authentication/test2")
                        ).param("date", "2019-01-20 10:10:00"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result2);
        String result3 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URI.create("/authentication/test3")
                        ).param("date", "2019-01-20 10:10:00"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result3);
        String result4 = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(URI.create("/authentication/test4")
                        ).param("date", "2019-01-20 10:10:00"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result4);
        Assert.assertNotEquals(null, result4);
    }
}

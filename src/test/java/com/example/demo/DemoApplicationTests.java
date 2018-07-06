package com.example.demo;

import com.example.demo.repository.GameRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/*@EnableAutoConfiguration(exclude = {
        JpaRepositoriesAutoConfiguration.class
})*/
@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class DemoApplicationTests {


    private MockMvc mvc;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Before
    public void setup() throws Exception {
        mvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void startTest() throws Exception {
        mvc.perform(get("/start")).andExpect(status().isOk());
    }

    @Test
    public void describeTest() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

	@Test
	public void moveTest() throws Exception {
        mvc.perform(get("/move")).andExpect(status().isOk());
	}

	@Test
    public void loadTest() throws Exception {
        mvc.perform(get("/load")).andExpect(status().isOk());
    }

    @Test
    public void scoreboardTest() throws Exception {
        mvc.perform(get("/scoreboard")).andExpect(status().isOk());
    }
}

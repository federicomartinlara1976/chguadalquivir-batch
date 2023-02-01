package net.bounceme.chronos.chguadalquivir;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.bounceme.chronos.chguadalquivir.controller.JobController;
import net.bounceme.chronos.chguadalquivir.model.Status;

@SpringBootTest
@AutoConfigureMockMvc
public class CHGuadalquivirApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JobController controller;
	
	private Status status;
	
	@BeforeEach
	public void setup(){
	   status = Status.builder().version("1.8.0_202").platform("Linux").response("OK").build();
	   
	}
	   
	@AfterEach
	void tearDown() {
	   status = null;
	}


	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	public void testIndex() throws Exception {
		mockMvc.perform(get("/api/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello world")));
	}
	
	@Test
	public void testStatus() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/status").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(status))).
				andExpect(MockMvcResultMatchers.status().isOk()).
                andDo(MockMvcResultHandlers.print());
	}
	
	public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}

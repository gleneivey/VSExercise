package com.vinoshipper.exercise

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun getHelloTest() {
        val expected = "{\"msg\": \"Hello!\"}"

        mvc.perform(MockMvcRequestBuilders.get("/hello"))
	    .andExpect(MockMvcResultMatchers.status().isOk)
	    .andExpect(MockMvcResultMatchers.content()
		.json(expected))
    }

    @Test
    fun postHelloTest() {
        val postBody = "{\"name\": \"Glen\"}"
        val expected = "{\"msg\": \"Hello Glen!\"}"

        mvc.perform(MockMvcRequestBuilders
	        .post("/hello")
		.content(postBody)
		.contentType(MediaType.APPLICATION_JSON)
	    )
	    .andExpect(MockMvcResultMatchers.status().isOk)
	    .andExpect(MockMvcResultMatchers.content()
		.json(expected))
    }
}

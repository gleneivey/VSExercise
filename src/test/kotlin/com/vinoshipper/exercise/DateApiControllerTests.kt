package com.vinoshipper.exercise

import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime
import java.time.ZoneId
import java.util.Date
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
class DateApiControllerTests {

    @Autowired
    lateinit var mvc: MockMvc

    fun validateExpectingResult(postBody: String, expected: String) {
        val result = mvc.perform(MockMvcRequestBuilders
	        .post("/validate-age")
		.content(postBody)
		.contentType(MediaType.APPLICATION_JSON)
	    )
	    .andExpect(MockMvcResultMatchers.status().isOk)
	    .andExpect(MockMvcResultMatchers.content()
		.json(expected))
	    .andReturn()
    }

    @Test
    fun validateAgeObviouslyTrueTest() {
        val postBody = "{\"birthdate\": \"2000-01-01\"}"
        val expected = "{\"overTwentyone\": true}"
	validateExpectingResult(postBody, expected)
    }

    @Test
    fun validateAgeObviouslyFalseTest() {
        val postBody = "{\"birthdate\": \"2022-01-01\"}"
        val expected = "{\"overTwentyone\": false}"
	validateExpectingResult(postBody, expected)
    }

    /*
     * The following tests run without time being stubbed, so
     * they compute inputs to the code under test based on the
     * live "now".  This was done for simplicity and could be
     * changed if cases around time-zone changes, leap years, or
     * other circumstances that would require the server to run
     * with a fixed "now" for setup end up having to be written
     * to drive specific exceptions in the verifier's computations.
     */

    @Test
    fun validateAgeBirthdayTodayTest() {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
	val twentyOneYearsAgo = now.minusYears(21)
	val birthdateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
	val birthdateString = birthdateFormatter.format(twentyOneYearsAgo)

        val postBody = "{\"birthdate\": \"${birthdateString}\"}"
        val expected = "{\"overTwentyone\": true}"
	validateExpectingResult(postBody, expected)
    }

    @Test
    fun validateAgeBirthdayTomorrowTest() {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
	val almostTwentyOneYearsAgo = now.minusYears(21).plusDays(1)
	val birthdateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
	val birthdateString = birthdateFormatter.format(almostTwentyOneYearsAgo)

        val postBody = "{\"birthdate\": \"${birthdateString}\"}"
        val expected = "{\"overTwentyone\": false}"
	validateExpectingResult(postBody, expected)
    }
}

package com.vinoshipper.exercise.api

import java.time.ZonedDateTime
import java.time.ZoneId
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class ValidateAgeForm(
    val birthdate: String
)

data class Response(
    val overTwentyone: Boolean
)

@RestController
class DateApiController {

    @PostMapping("/validate-age")
    fun validateAge(@RequestBody form: ValidateAgeForm): Response {
        val birthdayInUtc = ZonedDateTime.parse(form.birthdate + "T00:00Z")

        return Response(
	    birthdayInUtc.plusYears(21) <
	    ZonedDateTime.now(ZoneId.of("UTC"))
	)
    }
}

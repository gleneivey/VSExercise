This is a simple web service that performs computations at the request
of a client.




## Running The Application
### Start the local server
```bash
./gradlew bootRun
```

### Execute the tests
```bash
./gradlew test
```

## Implemented Endpoints

### `/validate-age` Endpoint

POST requests to this path should be made with a JSON object
containing a `birthdate` string encoded in "yyyy-MM-dd" (ISO-8601
date) format.  Response will be a JSON object containing an
`overTwentyone` boolean where "true" indicates that the birthday is
_more_ than 21 years ago _with the computation done in the UTC time
zone_.

(Alternative implementations could be done in a time zone local to the
client.  If this is desirable, the client would have to inform the
server of its time zone.  This could be accomplished by sending the
birthday as a date/time with the time being midnight in the desired
local zone, or could be accomplished by adding a second field to the
request object that explicitly specifies the client's desired zone.)

WARNING:  This endpoint is not currently well-behaved with respect to
error reporting or rate limiting.  Client developers should not be
surprised by 5xx HTTP status responses in the case of request errors
rather than 4xx status codes.

`curl` can be used to make requests of a local server:

```bash
curl -X POST http://localhost:8080/validate-age \
   -H 'Content-Type: application/json' \
   -d '{"birthdate":"2000-01-01"}'
```

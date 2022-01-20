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

### `/intervening-business-hours` Endpoint

POST requests to this path should be made with a JSON object
containing two ISO-8601 datetime strings as `fromDatetime` and
`toDatetime`.  The response will be a JSON object containing a
`businessHours` integer indicating the number of _whole_ business
hours between the from and to times.  So, for example, if the from
time is 9:01am, the first 59 minutes are not counted (not a whole
hour) and counting begins from 10am.  Similarly, if the from time is
4:59pm, the last 59 minutes are not counted and counting ends at 4pm.

Business hours are 9am to 5pm _in UTC_, on the days Monday through
Friday.  The time zone information included in `fromDatetime` and
`toDatetime` is respected and the times compared to the bounds 9am UTC
and 5pm UTC.

(Alternative implementations could be done in a configurable- or
requestable-time zone.  One alternative would be to require both the
from and to times to have the same time zone information, and then to
perform the calculations using 9am and 5pm in that zone.  Or, the
"home" time zone of the business whose hours are being computed could
be supplied as a separate request parameter.)


*YEAH, I DIDN'T FINISH THE EXERCISE.*  The text above describes the
implementation I was contemplating, but didn't implement.  The best
algorithm that has occurred to me so far would provide for handling an
initial partial day, then whole days in a partial week, then whole
weeks, then whole days in an ending partial week, and an ending
partial day.  Any or all of which could be missing.  That, plus corner
cases involving starting and ending outside of business hours or days,
seems like an awful lot of tests I'd have to write to drive out that
set of cases.  And I've already spent a fair bit of time on this
exercise (would have gone quicker if y'all had provided a working
test for the example controller methods that could be used as a model
for the tests to be written).  So I'm wrapping it up at this point and
you can decide whether or not the material I've produced is an
adequate sample.  Apologies if there's a more direct algorithm that I
haven't thought of which I could have implemented in a shorter
amount of time.

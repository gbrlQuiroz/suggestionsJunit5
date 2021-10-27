# Backend Coding Challenge

### Bussines Logic for score
For obtain the score, it's calculate the distance betwen the latitude and longitude given by the user with the record in database.
The score is put if the distance is in a range each 100 kilometers like this:
- 0 to 100 the score is 1.0
- 101 to 200 the score is 0.9
- 201 to 300 the score is 0.8... and so on...

### Notes
- Java version 11
- Database in memory populate it on fly
- To run locally
> `mvn clean ; mvn -DskipTests spring-boot:run`

### Unit Tests
- To verify if records where load successfull
> `mvn clean ; mvn test -Dtest=SuggestionTest#database`
- Test without parameters of latitude and longitude
> `mvn clean ; mvn test -Dtest=SuggestionTest#getSuggestionsWithout`
- Test all querystrings
> `mvn clean ; mvn test -Dtest=SuggestionTest#getSuggestionsWithLatitudeAndLongitude`
- Test return only one data
> `mvn clean ; mvn test -Dtest=SuggestionTest#getSuggestionsOnlyOne`
- Test doesn´t provide q parameter
> `mvn clean ; mvn test -Dtest=SuggestionTest#getSuggestionsError`

### curl Test (examples)
- local
> `curl --location --request GET 'http://localhost:8080/suggestions?q=A&latitude=45.00000&longitude=-75.00000'`

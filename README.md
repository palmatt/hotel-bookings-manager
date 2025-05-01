# Getting Started
In order to run the app JAVA_HOME and PATH variable needs to be defined correctly on the machine.

go to main repo dir and run

`mvnw clean package`

then run 

```
java -jar target/hotel-bookings-manager-0.0.1-SNAPSHOT.jar --hotels hotels.json --bookings bookings.json
```

where hotels.json is a path to hotels.json file and bookings.json is a path to bookings.json

available commands are 
Availability and Search, usage is exactly the same as asked in pdf file


examples

Availability(H1, 20240901, SGL)

Availability(H1, 20240901-20240903, DBL)

Search(H1, 365, SGL)


to finish the program execution input a blank string when asked for command

# Considerations
most relevant code has been covered in unit tests

no spring or database has been used since it was asked to keep it as simple as possible

application will work for relatively small json files (for larger ones, run parameters should be used to not have out of memory exceptions)


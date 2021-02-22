# Synalogik Technical Test Application

Created by Dominic Chim 
using spring boot, java and maven

## Instructions

1. either load the code via VSCode with the spring boot extensions and hava extensions, and run the application
2. alternatively CD into the readerapi folder and run `.\mvnw spring-boot:run` which will build compile and run the application

### Rest client testing
- use any appropriate rest client, personally i used postman

3. url to test: POST `http://localhost:8080/synalogik/reader/processdoc` 
body form-data  Key: file | value: file

### Unit tests 
- are under `readerapi/src/test/ReaderapiApplicationTests`

1. Test #1 - tests the endpoint with the provided base file
2. Test #2-4 - test the counting service for valid words, find the most frequent word lengths and calculating the average word length respectively
3. Test #5 - test the storage service to check the upload file is stored in uploads folder

Note: example_text folder contains a control text document and the extract daily bible document from link provided (http://janelwashere.com/files/bible_daily.txt)

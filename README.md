# image-repo
GITHUB repo for Shopify Backend Challenge https://docs.google.com/document/d/1ZKRywXQLZWOqVOHC4JkF3LqdpO3Llpfk_CkZPR8bjak/edit# <br/>
Author: Mohan Shobana<br/>
LinkedIn: https://www.linkedin.com/in/mohan-shobana-018m93/<br/>
Contact: shobana.m@northeastern.edu<br/>

## INRODUCTION
TASK: Build an image repository.<br/>

The task was to create an Image Repository. The challenge has been tackled to provide all the basic features in the form of API endpoints to the users. Also a basic UI has been created (nothing too fancy, just to test the functionality).<br/>

## HOW-TO-RUN
1. GITHUB/ GOOGLE OAuth2 Setup<br/>
The application uses GITHUB and GOOGLE OAuth2 for authentication. Hence we need to generate the client-id and client-secret for access the APIs and run the application. <br/>
The Application.properties(src/main/resources/application.properties) file has the keys where you might need to key in your GITHUB/GOOGLE client-id and client-secret.<br/>
![image](https://user-images.githubusercontent.com/8919204/117596214-da993980-b110-11eb-858e-90e70c6d20bc.png)

2. PostGreSQL database setup 
The application uses PostgresSQL as a relational DB to store images and associated information.<br/>
The Application.properties(src/main/resources/application.properties) file has the keys where you might need to change values as per your POSTGRES setup.<br/>
![image](https://user-images.githubusercontent.com/8919204/117596209-d8cf7600-b110-11eb-8c3a-27b9d603d64b.png)

## Features Developed
1.  Authentication
2.  Upload Image
3.  Download/Search Image
4.  Encryption

### Authentication
With the use of GITHUB and GOOGLE OAuth2, I have enabled the authentication of user. User cannot access APIs without getting authorized.<br/>
Once the application is running:<br/>
![image](https://user-images.githubusercontent.com/8919204/117596281-04526080-b111-11eb-93e3-82bfec2bf6b4.png)

You can launch the website by opening the following URL(http://localhost:8080/) in any browser:<br/>
![image](https://user-images.githubusercontent.com/8919204/117596346-2f3cb480-b111-11eb-86aa-a208341aefaa.png)

You can click either the GITHUB or GOOGLE login to authorize the application to use your data and authenticate you to use the application.<br/>
GITHUB
![image](https://user-images.githubusercontent.com/8919204/117596674-d883aa80-b111-11eb-9481-788138ab5f7a.png)
GOOGLE<br/>
![image](https://user-images.githubusercontent.com/8919204/117596724-f3561f00-b111-11eb-8f05-52d2e0389e37.png)

The authenticated user would re-routed back to the homepage using the redirect-url for the application.
![image](https://user-images.githubusercontent.com/8919204/117596806-2d272580-b112-11eb-80f8-a15bd1533027.png)

Using the logged-in user's principal values we fetch the name<br/>
We add the JSESSIONID and XSRF-TOKEN with each API call.<br/>
API : /user<br/>
![image](https://user-images.githubusercontent.com/8919204/117596978-973fca80-b112-11eb-8bb1-b34db3c4e32a.png)
![image](https://user-images.githubusercontent.com/8919204/117596987-9d35ab80-b112-11eb-9e85-9d7f6ba8d753.png)











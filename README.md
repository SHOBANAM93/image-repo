# image-repo
GITHUB repo for Shopify Backend Challenge https://docs.google.com/document/d/1ZKRywXQLZWOqVOHC4JkF3LqdpO3Llpfk_CkZPR8bjak/edit#
Author: Mohan Shobana
LinkedIn: https://www.linkedin.com/in/mohan-shobana-018m93/
Contact: shobana.m@northeastern.edu

## INRODUCTION
TASK: Build an image repository.

The task was to create an Image Repository. The challenge has been tackled to provide all the basic features in the form of API endpoints to the users. Also a basic UI has been created (nothing too fancy, just to test the functionality).

## HOW-TO-RUN
1. GITHUB/ GOOGLE OAuth2 Setup
The application uses GITHUB and GOOGLE OAuth2 for authentication. Hence we need to generate the client-id and client-secret for access the APIs and run the application. 
The Application.properties(src/main/resources/application.properties) file has the keys where you might need to key in your GITHUB/GOOGLE client-id and client-secret
![image](https://user-images.githubusercontent.com/8919204/117596214-da993980-b110-11eb-858e-90e70c6d20bc.png)

2. PostGreSQL database setup 
The application uses PostgresSQL as a relational DB to store images and associated information.
The Application.properties(src/main/resources/application.properties) file has the keys where you might need to change values as per your POSTGRES setup.
![image](https://user-images.githubusercontent.com/8919204/117596209-d8cf7600-b110-11eb-8c3a-27b9d603d64b.png)

## Features Developed
1.  Authentication
2.  Upload Image
3.  Download/Search Image
4.  Encryption

### Authentication
With the use of GITHUB and GOOGLE OAuth2, I have enabled the authentication of user. User cannot access APIs without getting authorized.
Once the application is running:
![image](https://user-images.githubusercontent.com/8919204/117596281-04526080-b111-11eb-93e3-82bfec2bf6b4.png)

You can lauch the website by opening the following URL(http://localhost:8080/) in any browser:
![image](https://user-images.githubusercontent.com/8919204/117596346-2f3cb480-b111-11eb-86aa-a208341aefaa.png)






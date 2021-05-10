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
The Application.properties(https://github.com/SHOBANAM93/image-repo/blob/92d81095c051478cb5ba291cd78380e5a4da7c36/src/main/resources/application.properties) file has the keys where you might need to key in your GITHUB/GOOGLE client-id and client-secret.<br/>
![image](https://user-images.githubusercontent.com/8919204/117596214-da993980-b110-11eb-858e-90e70c6d20bc.png)

2. PostGreSQL database setup 
The application uses PostgresSQL as a relational DB to store images and associated information.<br/>
The Application.properties(https://github.com/SHOBANAM93/image-repo/blob/92d81095c051478cb5ba291cd78380e5a4da7c36/src/main/resources/application.properties) file has the keys where you might need to change values as per your POSTGRES setup.<br/>
![image](https://user-images.githubusercontent.com/8919204/117596209-d8cf7600-b110-11eb-8c3a-27b9d603d64b.png)

## Features Developed
1.  Authentication
2.  Upload Images
3.  Search/Retrieve Images
4.  Encryption

### Authentication
With the use of GITHUB and GOOGLE OAuth2, I have enabled the authentication of user. User cannot access APIs without getting authorized.<br/>
Once the application is running:<br/>
![image](https://user-images.githubusercontent.com/8919204/117596281-04526080-b111-11eb-93e3-82bfec2bf6b4.png)

You can launch the website by opening the following URL(http://localhost:8080/) in any browser:<br/>
![image](https://user-images.githubusercontent.com/8919204/117596346-2f3cb480-b111-11eb-86aa-a208341aefaa.png)

You can click either the GITHUB or GOOGLE login to authorize the application to use your data and authenticate you to use the application.<br/>
GITHUB<br/>
![image](https://user-images.githubusercontent.com/8919204/117596674-d883aa80-b111-11eb-9481-788138ab5f7a.png)<br/>
GOOGLE<br/>
![image](https://user-images.githubusercontent.com/8919204/117596724-f3561f00-b111-11eb-8f05-52d2e0389e37.png)<br/>

The authenticated user would re-routed back to the homepage using the redirect-url for the application.<br/>
![image](https://user-images.githubusercontent.com/8919204/117596806-2d272580-b112-11eb-80f8-a15bd1533027.png)

Using the logged-in user's principal values we fetch the name<br/>
We add the JSESSIONID and XSRF-TOKEN with each API call.<br/>
API : /user<br/>
![image](https://user-images.githubusercontent.com/8919204/117596978-973fca80-b112-11eb-8bb1-b34db3c4e32a.png)
![image](https://user-images.githubusercontent.com/8919204/117596987-9d35ab80-b112-11eb-9e85-9d7f6ba8d753.png)

### Upload Images
The upload API can be used to upload SINGLE image or multiple images as per user's need.<br/>
The user's image size limit (max-file-size) is mentioned in the application.properties (https://github.com/SHOBANAM93/image-repo/blob/92d81095c051478cb5ba291cd78380e5a4da7c36/src/main/resources/application.properties). Also altogether all files cannot exceed max-request-size.<br/>
Both these properties can be edited as per need.<br/>
![image](https://user-images.githubusercontent.com/8919204/117597292-44b2de00-b113-11eb-8f43-4d4512fc319c.png)<br/>

Once that is set, the application is running and user is authenticated, we can click upload button to get the upload images form.<br/>
![image](https://user-images.githubusercontent.com/8919204/117597389-79bf3080-b113-11eb-84b8-0ee14e48530b.png)<br/>
1. Using the browse button, select all images.
2. Next, we can give space-separated tagnames to the images being uploaded. Using these tags we can later retrieve them.
3. Finally we can select if these images are for private view only or for public view also.
4. Click submit.

This invokes the upload API.<br/>
API: /upload<br/>
Method: HTTP POST<br/>
RequestBody: The files, tags and privacy for the images.<br/>
JSESSIONID and XSRF-TOKEN are added.<br/>
![image](https://user-images.githubusercontent.com/8919204/117597721-2a2d3480-b114-11eb-91b7-14af84697b5f.png)<br/>

Network tab shows request<br/>
![image](https://user-images.githubusercontent.com/8919204/117597768-48933000-b114-11eb-81e3-1b51692a8fa8.png)<br/>
Respose<br/>
![image](https://user-images.githubusercontent.com/8919204/117597792-55178880-b114-11eb-8049-b92e099f6361.png)<br/>

### Search/Retrieve Images
There are two APIs which could be used to fetch images. We can fetch images by giving the tags or by giving the exact image name.<br/>
The /retrieve/{imageName} API can be used to fetch the exact images matching the imageName.<br/>
The /retrieve/tags API can be used to fetch the images using the tag name.<br/><br/>
API: /retrieve/{imageName}<br/>
Method: HTTP GET<br/>
Params: imageName - image name<br/>
JSESSIONID and XSRF-TOKEN are added.<br/>
UI<br/>
![image](https://user-images.githubusercontent.com/8919204/117598372-ad9b5580-b115-11eb-8e4d-499255afe27b.png)<br/>
Request<br/>
![image](https://user-images.githubusercontent.com/8919204/117598424-ca378d80-b115-11eb-9427-52cd47ca5042.png)
Response<br/>
{"message":"Image retrieved","imageSet":[{"email":"shobanasneha.14@gmail.com","name":"restart.gif","type":"image/gif","privacy":"public","picByte":"image in bytes","tags":null}]}<br/>
![image](https://user-images.githubusercontent.com/8919204/117598545-066aee00-b116-11eb-8ac3-102da91674da.png)<br/><br/>

API:  /retrieve/tags<br/>
Method: HTTP POST<br/>
Request Body: tags (space separated)<br/>
JSESSIONID and XSRF-TOKEN are added.<br/>
UI<br/>
![image](https://user-images.githubusercontent.com/8919204/117598816-82fdcc80-b116-11eb-82d1-5001a485613c.png)<br/>
Request<br/>
![image](https://user-images.githubusercontent.com/8919204/117598919-ca845880-b116-11eb-8e8a-765a75e88545.png)
Response<br/>
![image](https://user-images.githubusercontent.com/8919204/117598842-94df6f80-b116-11eb-8abd-59f9bd9a1e44.png)

### Encryption
In the backend I'm compressing the image bytes using java.util.zip.Inflater(https://github.com/SHOBANAM93/image-repo/blob/70d29e4db468162128b6d1b7d4e750dacfc6bbad/src/main/java/com/interview/imageRepository/utils/CompressionUtils.java) and then encrypting the image bytes further by using AES encryption (https://github.com/SHOBANAM93/image-repo/blob/70d29e4db468162128b6d1b7d4e750dacfc6bbad/src/main/java/com/interview/imageRepository/utils/CryptoUtils.java).
<br/><br/>
And while retrieving them back, I'm first decrypting the image bytes and then decompressing the image before adding it to the response.<br/>


## DATABASE
Using PostGreSQL as the relational DB to store the images in byte format. The tag table stores the tags associated to the images. There is one-to-many relationship between image and tag table. one image can have many tags associated to it.<br/>
Image Table<br/>
![image](https://user-images.githubusercontent.com/8919204/117599250-70d05e00-b117-11eb-8b38-7b18c377555e.png)<br/>
Tag table<br/>
![image](https://user-images.githubusercontent.com/8919204/117599279-804fa700-b117-11eb-9a89-d3423a885d72.png)<br/>























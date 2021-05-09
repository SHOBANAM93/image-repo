package com.interview.imageRepository.controller;

import com.interview.imageRepository.model.dto.ResponseMessage;
import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.model.entity.Tag;
import com.interview.imageRepository.repository.ImageRepository;
import com.interview.imageRepository.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.Deflater;

@RestController
@CrossOrigin("*")
public class UploadController {

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  TagRepository tagRepository;

  // compress the image bytes before storing it in the database
  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (IOException e) {
    }
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
    return outputStream.toByteArray();
  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFiles(@AuthenticationPrincipal OAuth2User principal,
                                                     @RequestParam("files") MultipartFile[] files,
                                                     @RequestParam("tags") String tags) {
    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();
    System.out.println(principal.getAttribute("name") + " uploading " + files.length + " images");
    String message = "";
    try {
      List<String> fileNames = new ArrayList<>();

      String[] tagsArr = tags.split(" ");
      List<Tag> tagList = new ArrayList<>();
      Arrays.asList(tagsArr).stream().forEach(tagname -> {
        Tag tag = new Tag(tagname);
        tagList.add(tag);
      });


      Arrays.asList(files).stream().forEach(file -> {
        try {
          System.out.println("Original Image Byte Size - " + file.getBytes().length);
          Image img = new Image(emailId,
                  file.getOriginalFilename(),
                  file.getContentType(),
                  compressBytes(file.getBytes()),
                  tagList);
          imageRepository.save(img);
          tagList.stream().forEach(tag -> {
            tag.setImage(img);
            tagRepository.save(tag);
          });
          fileNames.add(file.getOriginalFilename());
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      });

      message = "Uploaded the files successfully: " + fileNames;
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
      message = "Fail to upload files!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

}

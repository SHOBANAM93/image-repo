package com.interview.imageRepository.controller;

import com.interview.imageRepository.model.dto.ResponseMessage;
import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.model.entity.Tag;
import com.interview.imageRepository.repository.ImageRepository;
import com.interview.imageRepository.repository.TagRepository;
import com.interview.imageRepository.utils.CompressionUtils;

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



  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFiles(@AuthenticationPrincipal OAuth2User principal,
                                                     @RequestParam("files") MultipartFile[] files,
                                                     @RequestParam("tags") String tags,
                                                     @RequestParam("privacy") String privacy) {
    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();
    System.out.println(principal.getAttribute("name") + " uploading " + files.length + " images");
    String message = "";
    try {
      List<String> fileNames = new ArrayList<>();

      String[] tagsArr = tags.split(" ");

      Arrays.stream(files).forEach(file -> {
        try {
          System.out.println("Original Image Byte Size - " + file.getBytes().length);
          Image img = new Image(emailId,
                  file.getOriginalFilename(),
                  file.getContentType(),
                  CompressionUtils.compressBytes(file.getBytes()),
                  privacy);
          img = imageRepository.save(img);
          Image finalImg = img;
          List<Tag> tagList = new ArrayList<>();
          Arrays.stream(tagsArr).forEach(tagname -> {
            Tag tag = new Tag(tagname);
            tagList.add(tag);
          });
          tagList.forEach(tag -> {
            tag.setImage(finalImg);
            tag = tagRepository.save(tag);
          });
          img.setTags(tagList);
          imageRepository.save(img);
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

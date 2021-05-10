package com.interview.imageRepository.controller;

import com.interview.imageRepository.model.dto.ResponseMessage;
import com.interview.imageRepository.repository.ImageRepository;
import com.interview.imageRepository.repository.TagRepository;
import com.interview.imageRepository.service.ImageService;

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

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
public class UploadController {

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  TagRepository tagRepository;

  @Autowired
  ImageService imageService;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFiles(@AuthenticationPrincipal OAuth2User principal,
                                                     @RequestParam("files") MultipartFile[] files,
                                                     @RequestParam("tags") String tags,
                                                     @RequestParam("privacy") String privacy) {

    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();
    // System.out.println(principal.getAttribute("name") + " uploading " + files.length + " images");

    try {

      List<String> fileNames = imageService.saveImages(emailId, files, tags, privacy);


      return ResponseEntity.status(HttpStatus.OK)
              .body(new ResponseMessage("Uploaded the files successfully: " + fileNames));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseMessage("Fail to upload files!"));
    }
  }

}

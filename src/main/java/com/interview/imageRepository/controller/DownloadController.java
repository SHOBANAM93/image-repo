package com.interview.imageRepository.controller;

import com.interview.imageRepository.model.dto.ImageResponse;
import com.interview.imageRepository.model.dto.ResponseMessage;
import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class DownloadController {

  @Autowired
  ImageService imageService;

  @GetMapping(path = {"/retrieve/{imageName}"})
  public ResponseEntity<ResponseMessage> getImage(@AuthenticationPrincipal OAuth2User principal,
                                                  @PathVariable("imageName") String imageName) {
    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();

    try {
      Set<Image> imageSet = imageService.fetchImagebyName(emailId, imageName);
      return ResponseEntity.status(HttpStatus.OK).body(new ImageResponse("Image retrieved", imageSet));
    } catch (FileNotFoundException e) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
              .body(new ResponseMessage("No Images exist with the Name entered"));
    }
  }

  @PostMapping(path = {"/retrieve/tags"})
  public ResponseEntity<ResponseMessage> getImageFromTags(@AuthenticationPrincipal OAuth2User principal,
                                                          @RequestParam("tagnames") String tagnames) throws IOException {

    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();
    String[] tags = tagnames.split(" ");

    Set<Image> imageSet = imageService.fetchImagebyTags(emailId, tags);

    if (imageSet.isEmpty()) {
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("No Images exist with the Tags entered"));
    }
    return ResponseEntity.status(HttpStatus.OK).body(new ImageResponse("Images retrieved", imageSet));
  }
}

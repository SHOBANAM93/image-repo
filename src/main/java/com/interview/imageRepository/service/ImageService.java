package com.interview.imageRepository.service;

import com.interview.imageRepository.model.entity.Image;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface ImageService {

  Set<Image> fetchImagebyName(String emailId, String imageName) throws FileNotFoundException;

  Set<Image> fetchImagebyTags(String emailId, String[] tags);

  List<String> saveImages(String emailId, MultipartFile[] files, String tags, String privacy);
}

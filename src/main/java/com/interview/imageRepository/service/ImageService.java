package com.interview.imageRepository.service;

import com.interview.imageRepository.model.entity.Image;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Set;

@Service
public interface ImageService {

  Set<Image> fetchImagebyName(String emailId, String imageName) throws FileNotFoundException;

  Set<Image> fetchImagebyTags(String emailId, String[] tags);
}

package com.interview.imageRepository.service;

import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.model.entity.Tag;
import com.interview.imageRepository.repository.ImageRepository;
import com.interview.imageRepository.repository.TagRepository;
import com.interview.imageRepository.utils.CompressionUtils;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ImageServiceImpl implements ImageService {

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  TagRepository tagRepository;


  @Override
  public Set<Image> fetchImagebyName(String emailId, String imageName) throws FileNotFoundException {
    Set<Image> imageSet = new HashSet<>();
    List<Image> retrievedImage = imageRepository.findByName(imageName);
    if (retrievedImage.isEmpty()) {
      throw new FileNotFoundException("No Image exists matching the Name entered");
    }
    retrievedImage.forEach(image -> {
      if (image.getEmail().equals(emailId) || (image.getPrivacy().equals("public"))) {
        Image img = new Image(emailId,
                image.getName(),
                image.getType(),
                CompressionUtils.decompressBytes(image.getPicByte()),
                image.getPrivacy());
        imageSet.add(img);
      }
    });

    return imageSet;
  }

  @Override
  public Set<Image> fetchImagebyTags(String emailId, String[] tags) {

    Set<Image> imageSet = new HashSet<>();

    Arrays.asList(tags).forEach(tag -> {

      List<Tag> tagObj = tagRepository.findAllByName(tag);

      tagObj.forEach(obj -> {

        Image retrievedImage = obj.getImage();

        if (retrievedImage.getEmail().equals(emailId) || (retrievedImage.getPrivacy().equals("public"))){

          imageSet.add(new Image(emailId,
                  retrievedImage.getName(),
                  retrievedImage.getType(),
                  CompressionUtils.decompressBytes(retrievedImage.getPicByte()),
                  retrievedImage.getPrivacy()));
        }
      });
    });

    return imageSet;
  }
}

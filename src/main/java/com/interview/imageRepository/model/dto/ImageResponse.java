package com.interview.imageRepository.model.dto;

import com.interview.imageRepository.model.entity.Image;

import java.util.Set;

public class ImageResponse extends ResponseMessage {

  private Set<Image> imageSet;

  public ImageResponse(String message, Set<Image> imageSet) {
    super(message);
    this.imageSet = imageSet;
  }

  public Set<Image> getImageSet() {
    return imageSet;
  }

  public void setImageSet(Set<Image> imageSet) {
    this.imageSet = imageSet;
  }
}

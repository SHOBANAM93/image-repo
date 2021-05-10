package com.interview.imageRepository.service;

import com.interview.imageRepository.Constants;
import com.interview.imageRepository.exception.CryptoException;
import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.model.entity.Tag;
import com.interview.imageRepository.repository.ImageRepository;
import com.interview.imageRepository.repository.TagRepository;
import com.interview.imageRepository.utils.CompressionUtils;
import com.interview.imageRepository.utils.CryptoUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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
      if (validImageRetrievalPermission(image, emailId)) {
        try {
          imageSet.add(createNewDecompressedImage(emailId, image));
        } catch (CryptoException e) {
          e.printStackTrace();
        }
      }
    });

    if (imageSet.isEmpty()) {
      throw new FileNotFoundException("No Image exists matching the Name entered");
    }

    return imageSet;
  }

  @Override
  public Set<Image> fetchImagebyTags(String emailId, String[] tags) {

    Set<Image> imageSet = new HashSet<>();

    Arrays.asList(tags).forEach(tag -> {

      List<Tag> tagObj = tagRepository.findAllByName(tag);

      tagObj.forEach(obj -> {

        Image retrievedImage = obj.getImage();

        if (validImageRetrievalPermission(retrievedImage, emailId)) {
          try {
            imageSet.add(createNewDecompressedImage(emailId, retrievedImage));
          } catch (CryptoException e) {
            e.printStackTrace();
          }
        }
      });
    });

    return imageSet;
  }

  @Override
  public List<String> saveImages(String emailId, MultipartFile[] files, String tags, String privacy) {

    List<String> fileNames = new ArrayList<>();

    String[] tagsArr = tags.split(" ");

    Arrays.stream(files).forEach(file -> {
      try {
        // System.out.println("Original Image Byte Size - " + file.getBytes().length);
        Image img = createNewCompressedImage(emailId, file, privacy);

        img = imageRepository.save(img);
        Image finalImg = img;

        List<Tag> tagList = new ArrayList<>();
        Arrays.stream(tagsArr).forEach(tagname -> {
          Tag tag = new Tag(tagname);
          tagList.add(tag);
        });

        tagList.forEach(tag -> {
          tag.setImage(finalImg);
          tagRepository.save(tag);
        });

        img.setTags(tagList);
        imageRepository.save(img);

        fileNames.add(file.getOriginalFilename());
      } catch (IOException | CryptoException ioException) {
        ioException.printStackTrace();
      }
    });

    return fileNames;
  }

  private Image createNewCompressedImage(String emailId, MultipartFile file, String privacy)
          throws IOException, CryptoException {

    byte[] compressedPicBytes = CompressionUtils.compressBytes(file.getBytes());
    byte[] encryptedPicBytes = CryptoUtils.encrypt(Constants.CRYPTO_KEY,compressedPicBytes);

    return new Image(emailId,
            file.getOriginalFilename(),
            file.getContentType(),
            encryptedPicBytes,
            privacy);
  }

  private Image createNewDecompressedImage(String emailId, Image image) throws CryptoException {

    byte[] decryptedPicBytes = CryptoUtils.decrypt(Constants.CRYPTO_KEY,image.getPicByte());
    byte[] decompressedPicBytes = CompressionUtils.decompressBytes(decryptedPicBytes);

    return new Image(emailId,
            image.getName(),
            image.getType(),
            decompressedPicBytes,
            image.getPrivacy());
  }

  private boolean validImageRetrievalPermission(Image image, String emailId) {
    return image.getEmail().equals(emailId) || (image.getPrivacy().equals("public"));
  }
}

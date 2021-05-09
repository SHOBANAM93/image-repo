package com.interview.imageRepository.controller;

import com.interview.imageRepository.model.entity.Image;
import com.interview.imageRepository.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@Controller
@RequestMapping("/download")
public class DownloadController {

  @Autowired
  ImageRepository imageRepository;

  // uncompress the image bytes before returning it to the angular application
  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (IOException ioe) {
    } catch (DataFormatException e) {
    }
    return outputStream.toByteArray();
  }

  @GetMapping(path = {"retrieve/{imageName}"})
  public Image getImage(@AuthenticationPrincipal OAuth2User principal,
                        @PathVariable("imageName") String imageName) throws IOException {
    String emailId = Objects.requireNonNull(principal.getAttribute("email")).toString().toLowerCase();
    final Optional<Image> retrievedImage = imageRepository.findByName(imageName);
    Image img = new Image(emailId, retrievedImage.get().getName(), retrievedImage.get().getType(),
            decompressBytes(retrievedImage.get().getPicByte()), retrievedImage.get().getTags());
    return img;
  }
}

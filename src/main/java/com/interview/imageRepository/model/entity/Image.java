package com.interview.imageRepository.model.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "image")
public class Image {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "type")
  private String type;

  @Column(name = "privacy")
  private String privacy;

  @Column(name = "picByte", length = 1000)
  private byte[] picByte;

  @OneToMany(mappedBy = "image")
  @Cascade({CascadeType.ALL})
  private List<Tag> tags;

  public Image() {
    super();
  }

  public Image(String email, String name, String type, byte[] picByte, String privacy) {
    this.email = email;
    this.name = name;
    this.type = type;
    this.picByte = picByte;
    this.privacy = privacy;
  }

  public Image(String email, String name, String type, byte[] picByte, List<Tag> tags) {
    this.email = email;
    this.name = name;
    this.type = type;
    this.picByte = picByte;
    this.tags = tags;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public byte[] getPicByte() {
    return picByte;
  }

  public void setPicByte(byte[] picByte) {
    this.picByte = picByte;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPrivacy() {
    return privacy;
  }

  public void setPrivacy(String privacy) {
    this.privacy = privacy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Image)) return false;
    Image image = (Image) o;
    return Objects.equals(getName(), image.getName()) &&
            Objects.equals(getType(), image.getType()) &&
            Arrays.equals(getPicByte(), image.getPicByte());
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(getName(), getType());
    result = 31 * result + Arrays.hashCode(getPicByte());
    return result;
  }
}


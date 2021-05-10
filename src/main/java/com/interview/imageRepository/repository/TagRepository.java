package com.interview.imageRepository.repository;

import com.interview.imageRepository.model.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
  List<Tag> findAllByName(String name);
}

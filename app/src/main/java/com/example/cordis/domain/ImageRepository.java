package com.example.cordis.domain;

public interface ImageRepository {
   Boolean uploadImage(byte[] image, String imageName, String folderName);
    byte[] downloadImage(String imageName, String folderName);
    Boolean deleteImage(String imageName, String folderName);
}

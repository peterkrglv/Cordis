package com.example.cordis.data;

import com.example.cordis.domain.ImageRepository;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ImageRepositoryImpl implements ImageRepository {
    @Override
    public Boolean uploadImage(byte[] image, String imageName, String folderName) {
        try {
            StorageReference storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child(folderName + "/" + imageName + ".jpg");
            UploadTask uploadTask = storageRef.putBytes(image);
            Tasks.await(uploadTask);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public byte[] downloadImage(String imageName, String folderName) {
        try {
            StorageReference storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child(folderName + "/" + imageName + ".jpg");

            final long ONE_MEGABYTE = 1024 * 1024;
            byte[] bytes = Tasks.await(storageRef.getBytes(ONE_MEGABYTE));
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean deleteImage(String imageName, String folderName) {
        try {
            StorageReference storageRef = FirebaseStorage
                    .getInstance()
                    .getReference()
                    .child(folderName + "/" + imageName + ".jpg");
            Tasks.await(storageRef.delete());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

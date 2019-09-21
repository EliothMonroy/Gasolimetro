package com.ipn.mx.gasolimetro.usuarios;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SubirImagenUsuario  {

    FirebaseStorage storage;
    StorageReference storageRef, mountainsRef, mountainImagesRef;

    public SubirImagenUsuario(){
        storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
         storageRef = storage.getReference();

        // Create a reference to "mountains.jpg"
         mountainsRef = storageRef.child("mountains.jpg");

        // Create a reference to 'images/mountains.jpg'
        mountainImagesRef = storageRef.child("images/mountains.jpg");
    }



    public void referencia(){
        // While the file names are the same, the references point to different files

        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
    }


}

package huji.post_pc.path2pet.FoundProcess;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.core.motion.utils.Utils;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import huji.post_pc.path2pet.AppPath2Pet;
import huji.post_pc.path2pet.R;
import android.os.Bundle;
import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.net.Uri.fromFile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.camera.core.ImageAnalysis;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment_a_Camera extends Fragment {
    FoundPetProcess foundPetActivityInstance;
    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA};
    private Button camera;
    private Button gallery;
    private ImageView imageView;
    private static final int Image_Capture_Code = 102;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    private Uri imageUri;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.foundPetActivityInstance = (FoundPetProcess) getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.found_fragment_a_camera, container, false);

        // find views
        Button nextButton = view.findViewById(R.id.next);
        Button prevButton = view.findViewById(R.id.previous);
        Button galleryButton = view.findViewById(R.id.gallery_button);
        Button camera = view.findViewById(R.id.open_camera);
        ImageView imageView = view.findViewById(R.id.place_holder);
        imageView.setVisibility(View.VISIBLE);
        nextButton.setOnClickListener(v -> {
            String strImages = ""; // todo - get and parse images
            foundPetActivityInstance.sp.edit().putString(AppPath2Pet.SP_PHOTOS, strImages).apply();
            foundPetActivityInstance.progressBar.incrementProgressBy(1);
            Navigation.findNavController(view).navigate(R.id.fragment_b_Map);
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
//                cInt.putExtra(MediaStore.EXTRA_OUTPUT,
//                        Uri.fromFile(photo));
//                imageUri = Uri.fromFile(photo);
                startActivityForResult(cInt, 100);
            }
        });


        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(image);// todo OR this is where it gets stuck
            Uri selectedImage = getImageUri(getActivity(), image);
            String realPath = getRealPathFromURI(selectedImage);
            selectedImage = Uri.parse(realPath);
        } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            }


    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getActivity().getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

//    private void askCameraPermissions(){
//        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, 100);
//        }else
//        {
//            openCamera();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == 100){
//            if(grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                openCamera();
//            }else
//            {
//                Toast.makeText(getActivity(), "Camera permission is required to use camera", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    private void openCamera()
//    {
//        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cInt, 100);
//    }
}
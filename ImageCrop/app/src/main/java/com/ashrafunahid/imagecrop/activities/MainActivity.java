package com.ashrafunahid.imagecrop.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ashrafunahid.imagecrop.R;
import com.ashrafunahid.imagecrop.classes.NCrop;
import com.ashrafunahid.imagecrop.databinding.ActivityMainBinding;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private ActivityResultLauncher<Intent> galleryLauncher;
    private Uri selectedImageUri;
    private static final String TAG = "Testt";
    final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null) {
                    selectedImageUri = data.getData();
                    assert selectedImageUri != null;
                    startCrop(selectedImageUri);
                }
            }
        });

        binding.pickImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == NCrop.REQUEST_CROP) {
            handleCropResult(data);
        }
        if (resultCode == NCrop.RESULT_ERROR) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, ""+ NCrop.getError(data).getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = NCrop.getOutput(result);
        if (resultUri != null) {
            Log.d(TAG, "handleCropResult: " + resultUri);
        } else {
            Toast.makeText(MainActivity.this, "Image Crop Failed", Toast.LENGTH_SHORT).show();
        }
    }
    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .addCategory(Intent.CATEGORY_OPENABLE);

        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        galleryLauncher.launch(Intent.createChooser(intent, "Select Image"));
    }

    private void startCrop(@NonNull Uri uri) {
        String destinationFileName = "temp_crop.png";

        NCrop nCrop = NCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        nCrop = advancedConfig(nCrop);

        nCrop.start(MainActivity.this);

    }

    private NCrop advancedConfig(@NonNull NCrop nCrop) {
        NCrop.Options options = new NCrop.Options();

        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(100);

        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);


//        If you want to configure how gestures work for all NCropActivity tabs
//
//        options.setAllowedGestures(NCropActivity.SCALE, NCropActivity.ROTATE, NCropActivity.ALL);
//
//        options.setMaxBitmapSize(640);

        // Tune everything

//        options.setMaxScaleMultiplier(5);
//        options.setImageToCropBoundsAnimDuration(666);
//        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(true);
//        options.setCropGridStrokeWidth(20);
//        options.setCropGridColor(Color.GREEN);
//        options.setCropGridColumnCount(2);
//        options.setCropGridRowCount(1);
//        options.setToolbarCropDrawable(R.drawable.crop_crop);
//        options.setToolbarCancelDrawable(R.drawable.your_cancel_icon);

        // System bars appearance
        options.setStatusBarLight(true);
        options.setNavigationBarLight(false);

        // Color palette
//        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
//        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
//        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.your_color_res));
//        options.setActiveControlsWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));

        // Aspect ratio options
//        options.setAspectRatioOptions(2,
//            new AspectRatio("WOW", 1, 2),
//            new AspectRatio("MUCH", 3, 4),
//            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
//            new AspectRatio("SO", 16, 9),
//            new AspectRatio("ASPECT", 1, 1));
//        options.withAspectRatio(CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO);
//        options.useSourceImageAspectRatio();

        return nCrop.withOptions(options);
    }

}
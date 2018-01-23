package com.goldhands.porong.activity.upload;

import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.goldhands.porong.R;
import com.goldhands.porong.UI.BottomNavigation;
import com.goldhands.porong.activity.MainActivity;
import com.goldhands.porong.activity.MyPageActivity;
import com.goldhands.porong.activity.RecommendActivity;
import com.goldhands.porong.activity.SearchActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadActivity_3 extends AppCompatActivity {
    RecyclerView recyclerView;
    private ImageButton back, next;
    private Button mypictures;
    private ImageView imageView;
    String str[] = {"사진 찍기", "갤러리에서 가져오기"};
    // Variables to use in ActivityResult
    private final int PICK_CAMERA = 0;
    private final int PICK_GALLERY = 1;
    private final int IMAGE_CROP = 2;
    private boolean isAlbum = false;  // crop 수행 시에 앨범에서 가져온 사진인지 아닌지을 확인하기 위한 변수
    private String tempPhotoPath;
    private Uri imageUri;
    private Uri photoURI, albumURI, providerUri;
    private File imageFile;

    private BottomNavigation bn;


    // Interface for preference data
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;   // Editor of preference data

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload3);

        // Bottom 네비케이션
        bn = new BottomNavigation();
        bn.bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
        bn.navigationInit(bn.bnve);
        bn.bnve.setCurrentItem(2);

        back = (ImageButton) findViewById(R.id.back);
        next = (ImageButton) findViewById(R.id.next);
        imageView = (ImageView) findViewById(R.id.imageView);
        mypictures = (Button) findViewById(R.id.mypictures);

        //NOTE: Navigation Event Listener
        bn.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.i_home:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.i_search:
                        showDialog();
                        bn.bnve.setCurrentItem(2);
                        break;
                    case R.id.i_upload:
                        break;
                    case R.id.i_recommend:
                        intent = new Intent(getApplicationContext(), RecommendActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.i_mypage:
                        intent = new Intent(getApplicationContext(), MyPageActivity.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("image", imageFile.getAbsolutePath());
                editor.putString("imageUri", albumURI.toString());
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), UploadActivity_4.class);
                startActivity(intent);
            }
        });

        mypictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Dialog();
            }
        });


        // Basic Images
        ArrayList<Integer> images = new ArrayList<Integer>();
    }

    public void showDialog() {
        MaterialStyledDialog dialog = new MaterialStyledDialog.Builder(this)
                .setTitle(R.string.information)
                .setIcon(R.drawable.ic_info_black_24dp)
                .setDescription(R.string.search_description)
                .setCancelable(true)
                .setPositiveText(R.string.ok)
                .withIconAnimation(false)
                .show();
    }

    // New Dialog to make new album
    private void show_Dialog() {
        // 새 앨범 추가를 위한 Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 선택")
                .setNegativeButton("취소", null)
                .setItems(str, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "미지원", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                pick_Picture_from_gallery();
                                break;
                            default:
                                return;
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);   // Dialog 바깥 쪽을 터치 시에 없어지지 않도록 설정
        dialog.show();
    }


    // Method to take a picture
    public void take_Picture() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent1.resolveActivity(getPackageManager()) != null) {
                File photofile = null;
                try {
                    photofile = createImageFile();    // save temp file path
                } catch (Exception e) {
                }

                if (photofile != null) {
                    // getUriForFile의 두번째 인자는 Manifest provider의 authorites와 일치해야 함
                    // FileProvider는 content://로 시작 (photoURI는 file://로 시작. 이걸 노출하는 건 보안상 좋지 않음)
                    // getUriForFile에서 사용하는 기본 경로는 xml에 적혀 있는 태그에 따라 설정이 되고,
                    // 뒤에 있는 photofile를 찾아 가져오게 되는 것 그리고 이 값을 Uri 값으로 리턴
                    providerUri = FileProvider.getUriForFile(this, getPackageName(), photofile);
                    imageUri = providerUri;

                    intent1.putExtra(MediaStore.EXTRA_OUTPUT, providerUri);   // deliver uri
                    startActivityForResult(intent1, PICK_CAMERA);
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "외장 메모리 미 지원", Toast.LENGTH_SHORT).show();
            return;
        }
    }


    // Method to create File
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        imageFile = null;
        File file = new File(Environment.getExternalStorageDirectory() + "/Pictures", "Porong");

        if (!file.exists()) {
            file.mkdirs();
        }

        imageFile = new File(file, imageFileName);
        tempPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
    }


    // Method to pick a Picture from the gallery
    public void pick_Picture_from_gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);  // go to Gallery
        intent.setType("image/*")
                .setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_GALLERY);
    }


    // 카메라 전용 크랍
    public void cropImage_camera() {
        Uri contentUri;
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        if(Build.VERSION.SDK_INT >= 24){
            try{
                contentUri = FileProvider.getUriForFile(getApplicationContext(), getPackageName(), imageFile);
                getApplicationContext().grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                cropIntent.setDataAndType(contentUri, "image/*");
                cropIntent.putExtra("outputX", 900);
                cropIntent.putExtra("outputY", 900);
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("scale", true);
                cropIntent.putExtra("output", contentUri);  // 크랍된 이미지를 해당 경로에 저장

                List<ResolveInfo> lists = getApplicationContext().getPackageManager().queryIntentActivities(cropIntent, PackageManager.MATCH_DEFAULT_ONLY);
                grantUriPermission(lists.get(0).activityInfo.packageName, contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                Intent i = new Intent(cropIntent);
                ResolveInfo res = lists.get(0);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                grantUriPermission(res.activityInfo.packageName, contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            contentUri = Uri.fromFile(imageFile);

            cropIntent.setDataAndType(contentUri, "image/*");
            cropIntent.putExtra("outputX", 900);
            cropIntent.putExtra("outputY", 900);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scale", true);
            cropIntent.putExtra("output", contentUri);  // 크랍된 이미지를 해당 경로에 저장
        }

        startActivityForResult(cropIntent, IMAGE_CROP);
    }

    // 앨범 이미지 CROP
    public void cropImage_album() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("outputX", 900);
        cropIntent.putExtra("outputY", 900);
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("scale", true);
        cropIntent.putExtra("output", albumURI);  // 크랍된 이미지를 해당 경로에 저장
        startActivityForResult(cropIntent, IMAGE_CROP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PICK_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    cropImage_camera();
                } else {
                    Toast.makeText(getApplicationContext(), "사진 찍기를 취소하였습니다.", Toast.LENGTH_SHORT).show();
                }
                break;

            case PICK_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getData() != null) {
                        File albumFile = null;
                        try {
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage_album();
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "에러 발생", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;

            case IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    galleryAddPic();
                    imageView.setImageURI(data.getData());
                }
                break;

            default:
                return;
        }
    }

    // commit to save pic
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(tempPhotoPath);  // 해당 경로에 있는 파일을 객체와(새로 파일을 만ㄷ는 것이 아님)
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(getApplicationContext(), "사진이 앨범에 저장", Toast.LENGTH_SHORT).show();
    }
}

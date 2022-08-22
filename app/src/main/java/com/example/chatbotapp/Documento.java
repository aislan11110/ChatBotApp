package com.example.chatbotapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermissionActivity;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;


public class Documento extends Fragment implements View.OnClickListener {

    String path = "";
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ImageButton Novo_Documento;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private Uri arquivo2;
    private ActivityResultLauncher<String> getConteudo = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    arquivo2= result;
                    File file = new File(result.getPath());
                    path = file.getPath();
                }
            });
    ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            }
    );
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri arquivo = data.getData();
                        path= arquivo.getPath();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novodocumento, container, false);

        Novo_Documento = (ImageButton) view.findViewById(R.id.Documento_Novo);
        Button botão_enviar = (Button) view.findViewById(R.id.Documento_Enviar);
        Novo_Documento.setOnClickListener(this);
        botão_enviar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.Documento_Novo:
                if(checkPermission()) {
                    filepick();
                    Novo_Documento.setVisibility(View.INVISIBLE);
                } else {
                    requestPermission();
                }
                break;
            case R.id.Documento_Enviar:
                if (path != "" ) {
                    try {
                        if(checkPermission()) {
                            reader();
                            Toast toast = Toast.makeText(getContext(), "arquivo enviado", Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            requestPermission();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;

        }
    }

    private void requestPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try{
                Log.d(TAG, "requesitando permissão: try");
                
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getActivity().getApplicationContext().getPackageName())));
                storageActivityResultLauncher.launch(intent);

            } catch (Exception e){
                Log.e(TAG, "requisitando permissão: catch");

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),permissions,STORAGE_PERMISSION_CODE);
        }
    }

    private boolean checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            int write = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
            if(write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        }
    }

private void filepick(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.R){
           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
          // intent.addCategory(Intent.CATEGORY_OPENABLE);
           intent.setType("text/plain");
           // someActivityResultLauncher.launch(intent);
            getConteudo.launch("text/plain");
        } else {
            getConteudo.launch("text/plain");
        }
}


 
private void reader() throws IOException {

        //TODO
        String path20 = path;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            path20= arquivo2.getPath();
        } else {
            path20=path20.substring(14);
        }
        File file = new File(path20);
        Scanner scanner = new Scanner(file);
        String dados = "";
        while (scanner.hasNextLine()){
            dados += scanner.nextLine()+"\n";
        }
        File path2 = getActivity().getExternalFilesDir(null);
        File file2;
        if(file.getName().contains(".txt")) {
            file2 = new File(path2, file.getName());
        } else{
            file2 = new File(path2,file.getName()+".txt");
        }
    try {
        FileWriter fw = new FileWriter(file2);
        fw.write(dados);
        fw.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}

PermissionListener leitordepermissão = new PermissionListener() {
    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied(List<String> deniedPermissions) {

    }
};



}
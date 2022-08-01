package com.example.chatbotapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.EnvironmentalReverb;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;


public class Documento extends Fragment implements View.OnClickListener{

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_novodocumento, container, false);

        ImageButton Novo_Documento = (ImageButton) view.findViewById(R.id.Documento_Novo);
        Novo_Documento.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.Documento_Novo:
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 1000);
                    getStorage();
                }
                break;

        }

    }

    private void getStorage(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getActivity(), "Permissão Aceita", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permissão foi negada \n" +
                        "Conceda permissão, para acessar funcionalidade", Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    /*public boolean checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        else{
            int read = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

            return read == PackageManager.PERMISSION_GRANTED;
        }
    }

    private ActivityResultLauncher<String> PermissionResult = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    Toast.makeText(getContext(), "ENTROOOOOOOOOUUUUUUU", Toast.LENGTH_SHORT).show();
                    if (result) {
                        Toast.makeText(getContext(), "Permissão foi concedida", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Permissão foi negada \n" +
                                "Conceda permissão, para acessar funcionalidade", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


}
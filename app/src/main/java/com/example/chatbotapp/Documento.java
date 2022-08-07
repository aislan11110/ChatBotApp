package com.example.chatbotapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.audiofx.EnvironmentalReverb;
import android.net.Uri;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.net.URI;
import java.util.List;


public class Documento extends Fragment implements View.OnClickListener{

    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    ActivityResultLauncher<String>getConteudo = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                }
            });

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
                } else {
                    if(checkPermission()==true){
                        filepick();
                    }
                }
                break;

        }
    }

  private boolean checkPermission() {
      int resultado = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
      if (resultado == PackageManager.PERMISSION_GRANTED) {
          return true;
      } else {
          return false;
      }
}

private void filepick(){
        getConteudo.launch("text/plain");
}



}
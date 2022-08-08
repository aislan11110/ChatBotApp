package com.example.chatbotapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
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
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;


public class Documento extends Fragment implements View.OnClickListener{
    
    String path="";
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    ImageButton Novo_Documento;
    ActivityResultLauncher<String>getConteudo = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                   File file = new File(result.getPath());
                   path=file.getPath();
                }
            });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_novodocumento, container, false);

        Novo_Documento = (ImageButton) view.findViewById(R.id.Documento_Novo);
        Button botão_enviar = (Button) view.findViewById(R.id.Documento_Enviar);
        Novo_Documento.setOnClickListener(this);
        botão_enviar.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.Documento_Novo:
             /*   if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                )!= PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 1000);
                } else {
                    if(checkPermission()==true){
                        filepick();
                        Novo_Documento.setVisibility(View.INVISIBLE);
                    }
                }*/
                TedPermission.create()
                        .setPermissionListener(leitordepermissão)
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .check();
                filepick();
                Novo_Documento.setVisibility(View.INVISIBLE);
                break;
            case R.id.Documento_Enviar:
                if(path!="" && checkPermission()==true){
                    try {
                        reader();
                    } catch (IOException e) {
                        e.printStackTrace();
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

private void reader() throws IOException {
        File file = new File(path.substring(14));
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
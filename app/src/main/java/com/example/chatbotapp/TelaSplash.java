package com.example.chatbotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class TelaSplash extends AppCompatActivity {
    AssetManager assetManager;
    ArrayList<String> listadetxt ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        //Sleep que será substituído pelo dowload dos arquivos
        new Handler().postDelayed(new Runnable() {
            public void run() {
                TrocarTela();
            }
        }, 1000);
    }
    private void TrocarTela() {
        try {
            leitorpdfparatxt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, InicialActivity.class);
        intent.putStringArrayListExtra("listadetxt",listadetxt);
        startActivity(intent);
        finish();
    }

    private void leitorpdfparatxt() throws IOException {
            assetManager = getAssets();
            String text = "";
            String[] serto = getAssets().list("tipo");
            String[] serto2 = getExternalFilesDir(null).list();
            ArrayList<String> listdetxt = new ArrayList<String>();
            for (int x = 0; x < serto.length; x++) {
                if (serto[x].contains(".txt")) {
                    listdetxt.add(serto[x]);
                }
            }
            for(int x=0;x<serto2.length;x++){
                if(serto2[x].contains(".txt") && !listdetxt.contains(serto2[x])){
                    listdetxt.add(serto2[x]);
                }
            }
            this.listadetxt = listdetxt;
            for (int x = 0; x < serto.length; x++) {
                InputStream inputstream = getAssets().open("tipo/"+listdetxt.get(x));
                Scanner scanner = new Scanner(inputstream);
                while (scanner.hasNextLine()) {
                    text += scanner.nextLine() + "\n";
                }
                File path = getApplicationContext().getExternalFilesDir(null);
                File file = new File(path, listdetxt.get(x));
                FileWriter fw = new FileWriter(file);
                fw.write(text);
                fw.close();
                text= "";
            }
        }
    }

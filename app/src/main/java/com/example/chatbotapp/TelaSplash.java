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

public class TelaSplash extends AppCompatActivity {
    AssetManager assetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_splash);

        //Sleep que será substituído pelo dowload dos arquivos
        new Handler().postDelayed(new Runnable() {
            public void run() {
                TrocarTela();
            }
        }, 500);
    }
    private void TrocarTela() {
        leitorpdfparatxt();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void leitorpdfparatxt() {
        try {
            assetManager = getAssets();
            PDFBoxResourceLoader.init(getApplicationContext());
            InputStream stream = assetManager.open("bula_ibuprofeno.pdf");
            PDDocument document = PDDocument.load(stream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            File path = getApplicationContext().getExternalFilesDir(null);
            File file = new File(path, "teste");
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
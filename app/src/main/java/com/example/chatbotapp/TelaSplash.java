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
        leitorpdfparatxt();
        Intent intent = new Intent(this, InicialActivity.class);
        intent.putStringArrayListExtra("listadetxt",listadetxt);
        startActivity(intent);
        finish();
    }

    private void leitorpdfparatxt() {
        try {
            assetManager = getAssets();
        /*    PDFBoxResourceLoader.init(getApplicationContext());
            InputStream stream = assetManager.open("sol_de_glicose_5_e_10.pdf");
            PDDocument document = PDDocument.load(stream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
         */
            String text = "";

            // transformador_a_oleo
            // teste1
            String[] serto = getAssets().list("");
            ArrayList<String> listdetxt = new ArrayList<String>();
            for (int x = 0; x < serto.length; x++) {
                if (serto[x].contains(".txt")) {
                    listdetxt.add(serto[x]);
                }
            }
            this.listadetxt = listdetxt;
            for (int x = 0; x < listdetxt.size(); x++) {
                InputStream inputstream = getAssets().open(listdetxt.get(x));
                Scanner scanner = new Scanner(inputstream);
                while (scanner.hasNextLine()) {
                    text += scanner.nextLine() + "\n";
                }
                File path = getApplicationContext().getExternalFilesDir(null);
                File file = new File(path, listdetxt.get(x));
                FileWriter fw = new FileWriter(file);
                fw.write(text);
                fw.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
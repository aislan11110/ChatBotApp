package com.example.chatbotapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    AssetManager assetManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatBot()).commit();
            navigationView.setCheckedItem(R.id.nav_chatbot);

        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_chatbot:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatBot()).commit();
                break;

            case R.id.nav_perfil:
                Toast.makeText(MainActivity.this, "Fazer tela do perfil", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_config:
                Toast.makeText(MainActivity.this, "Fazer tela da configuração", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_ajuda:
                Toast.makeText(MainActivity.this, "Fazer tela da ajuda", Toast.LENGTH_SHORT).show();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void leitorarquivo(){
         /*  try {
            assetManager = getAssets();
            PDFBoxResourceLoader.init(getApplicationContext());
          InputStream stream =  assetManager.open("bula_ibuprofeno.pdf");
            PDDocument document = PDDocument.load(stream);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            File path = getApplicationContext().getExternalFilesDir(null);
            File file = new File(path,"teste");
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
       */
    }

}
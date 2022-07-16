package com.example.chatbotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.chatbotapp.datastructure.ChatBotIA;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        leitortxt();
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

    private void leitortxt(){
        try {
            ChatBotIA bot = new ChatBotIA(getExternalFilesDir(null),"teste.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Pergunta se o arquivo existe
    boolean hasExternalStoragePrivateFile(String s) {
        //pergunta existencia de arquivo com dado nome s
        File file = new File(
                getExternalFilesDir(null), s
        );
        return file.exists();
    }

}
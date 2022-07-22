package com.example.chatbotapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.chatbotapp.datastructure.ChatBotIA;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private ChatBotIA bot;
    private AlertDialog.Builder PopUpAjuda;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bot = leitortxt();
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open
                , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
       /*     Fragment frag = new ChatBot();
            Bundle bundle = new Bundle();
            bundle.putSerializable("path",bot);
            frag.setArguments(bundle);
            FragmentManager fragManager = getSupportFragmentManager();
            fragManager.beginTransaction().replace(R.id.fragment_container,
                    frag).commit();

        */
          getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ChatBot(bot)).commit();


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
                        new ChatBot(bot)).commit();
                createNewPopUpAjuda();
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

    public void createNewPopUpAjuda(){
        PopUpAjuda = new AlertDialog.Builder(this);
        final View PopUpView = getLayoutInflater().inflate(R.layout.popup, null);

        PopUpAjuda.setView(PopUpView);
        dialog = PopUpAjuda.create();
        dialog.show();
    }




    private ChatBotIA leitortxt(){
        try {
            ChatBotIA bot = new ChatBotIA(getExternalFilesDir(null),"teste.txt");
            return bot;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
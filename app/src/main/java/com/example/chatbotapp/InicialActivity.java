package com.example.chatbotapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class InicialActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ArrayList<String> listadetxt;
    String txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        Button iniciar = (Button) findViewById(R.id.botao_iniciar);
        Bundle extras = getIntent().getExtras();
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        if(extras!=null){
           this.listadetxt= extras.getStringArrayList("listadetxt");

        }
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                listadetxt);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
                iniciar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(txtName!=null) {
                    Intent intent = new Intent(InicialActivity.this, MainActivity.class);
                    intent.putExtra("txtname",txtName);
                    startActivity(intent);
                } else {

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        this.txtName = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


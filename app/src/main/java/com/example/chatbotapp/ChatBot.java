package com.example.chatbotapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbotapp.datastructure.ChatBotIA;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChatBot extends Fragment {

    private RecyclerView chat;
    private EditText MensagemUsuario;
    private FloatingActionButton MensagemEnviar;
    private final String USER_KEY = "user";
    private final String BOT_KEY = "bot";
    private ArrayList<ChatModal>chatModalArrayList;
    private ChatRV chatRV;
    private ChatBotIA bot;

    public ChatBot(ChatBotIA bot) {
        this.bot = bot;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_chatbot, container, false);

        chat = view.findViewById(R.id.RecV_ChatBot);
        MensagemUsuario = view.findViewById(R.id.mensagem);
        MensagemEnviar = view.findViewById(R.id.mensagem_enviar);
        chatModalArrayList = new ArrayList<>();
        chatRV = new ChatRV(chatModalArrayList, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        chat.setLayoutManager(manager);
        chat.setAdapter(chatRV);
        chatModalArrayList.add(new ChatModal("olá usuario\n\n"+mostrarOpções(),BOT_KEY));
        MensagemEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MensagemUsuario.getText().toString().isEmpty()){
                    return;
                }
                getResponse(MensagemUsuario.getText().toString());
                MensagemUsuario.setText("");
            }
        });

        return view;
    }

    private void getResponse(String mensagem) {
        chatModalArrayList.add(new ChatModal(mensagem, USER_KEY));
        chatModalArrayList.add(new ChatModal("Programar resposta do bot", BOT_KEY));
        chat.scrollToPosition(chatModalArrayList.size() - 1);
    }

    private String mostrarOpções(){
        String concatenador="";
        for(int x=0;x<bot.getMapa().size();x++){
            concatenador+=bot.getMapa().get(x).getChave()+"\n";
        }
        return concatenador;
    }


}


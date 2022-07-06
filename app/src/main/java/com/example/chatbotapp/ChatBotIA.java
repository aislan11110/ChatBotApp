package com.example.chatbotapp;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatBotIA {

    Map<String,String> mapa = new HashMap<String,String>();

    ChatBotIA(File bula, String s) throws Exception {
        leitor(bula.getPath()+"/"+s);
    }
    private Map<String,String> leitor(String path) throws Exception{
        File arquivo = new File(path);
        Scanner sc = new Scanner (arquivo);
        while (sc.hasNext()){
             String str = sc.nextLine();
        }
        return null;
    }
    public String resposta(@NonNull String user){



        return "";
    }


}

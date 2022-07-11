package com.example.chatbotapp;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChatBotIA {

    Map<String,String> mapa = new HashMap<String,String>();

    ChatBotIA(File bula, String s) throws Exception {
        leitor(bula.getPath()+"/"+s);
    }
    private Map<String,String> leitor(String path) throws Exception{

        Scanner scanner = new Scanner(new File(path));
        String line;
        String chave="";
        String concatenador="";
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.length()>1) {
                if (allUpperCase(line) && chave == "") {
                    chave = line;
                } else if (allUpperCase(line) && chave != "") {
                    if(concatenador.length()>1) {
                        mapa.put(chave, concatenador);
                    }
                    chave = line;
                    concatenador = "";
                } else if (chave != "") {
                    concatenador += line+"\n";
                }
            }
        }

        return null;
    }
    public String resposta(@NonNull String user){



        return "";
    }

    private Boolean allUpperCase(String s){
        for (int x=0;x<s.length();x++){
            if(Character.isWhitespace(s.charAt(x))){
                continue;
            } else if(Character.isLowerCase(s.charAt(x))){
                return false;
            }
        }
        return true;
    }

}

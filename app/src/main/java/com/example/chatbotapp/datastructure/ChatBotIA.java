package com.example.chatbotapp.datastructure;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatBotIA implements Serializable {

    ArrayList<Mapa> mapa = new ArrayList<Mapa>();
    String pathfile;
    private String opçãouser = "";

    public ChatBotIA(File bula, String s) throws Exception {
        pathfile = bula.getPath()+"/"+s;
        leitor(pathfile);
    }
    private void leitor(String path) throws Exception{

        Scanner scanner = new Scanner(new File(path));
        String line;
        int x =-1;
        String concatenador="";
        String chave ="";
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            if(line.length()>1) {
                if(isCase(line)){
                    if(chave!="" && concatenador!=""){
                        if(isSubcase(chave)){
                            int tamanho = mapa.size()-1;
                            mapa.get(tamanho).getSubseções().add(new Mapa (chave));
                            int tamanho2 = mapa.get(tamanho).getSubseções().size()-1;
                            mapa.get(tamanho).getSubseções().get(tamanho2).setInfo(concatenador);
                        } else {
                            mapa.add(new Mapa(chave));
                            int tamanho =mapa.size()-1;
                            mapa.get(tamanho).setInfo(concatenador);
                        }
                    } else if (chave!="" && isCase(chave) && concatenador==""){
                        mapa.add(new Mapa(chave));
                        int tamanho =mapa.size()-1;
                        mapa.get(tamanho).setInfo(concatenador);
                    }
                    chave = line;
                    concatenador="";
                } else if(chave!=""){
                    concatenador+=line+"\n";
                }
            }
        }
        if(chave!="" && concatenador!=""){
            if(isSubcase(chave)){
                int tamanho = mapa.size()-1;
                mapa.get(tamanho).getSubseções().add(new Mapa (chave));
                int tamanho2 = mapa.get(tamanho).getSubseções().size()-1;
                mapa.get(tamanho).getSubseções().get(tamanho2).setInfo(concatenador);
            } else {
                mapa.add(new Mapa(chave));
                int tamanho =mapa.size()-1;
                mapa.get(tamanho).setInfo(concatenador);
            }
        }
        }

    public String resposta(@NonNull String user){
        Integer opção = Integer.parseInt(user);
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(opção==-1){
            opçãouser="";
            return "resetado";
        } else {
            if (opçãouser == "") {
                opçãouser = opção.toString() + "-";
            } else {
                opçãouser += opção.toString() + "-";
            }
        }
        String concatenador="";
        for(int x=0;x<opçãouser.length();x++){
            if(Character.isDigit(opçãouser.charAt(x))){
                concatenador+=Character.toString(opçãouser.charAt(x));
            } else if(opçãouser.charAt(x)=='-'){
                list.add(Integer.parseInt(concatenador)-1);
                concatenador="";
            }
        }
        Mapa ponteiro = null;
        for(int x=0;x<list.size();x++){
            if(x==0) {
                ponteiro = mapa.get(list.get(x));
            } else {
                ponteiro = ponteiro.getSubseções().get(list.get(x));
            }
        }
        String last ="";
        if(ponteiro!=null) {
             last = ponteiro.getInfo() + "\n\n";
             if(ponteiro.getSubseções().size()>0){
                 for(int x=0;x<ponteiro.getSubseções().size();x++){
                     last+=ponteiro.getSubseções().get(x).getChave()+"\n";
                 }
             }
        }
        return last;
    }

    private Boolean isCase(String s){
        int tamanho = s.length();
        if(s.length()<2){
            return false;
        } else if(Character.isDigit(s.charAt(0)) && s.charAt(1)=='.'){
            return true;
        } else {
            return false;
        }
    }
    private Boolean isSubcase(String s){
        if(s.length()<4){
            return false;
        } else if(Character.isDigit(s.charAt(2)) && s.charAt(3)=='.'){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Mapa> getMapa() {
        return mapa;
    }
}

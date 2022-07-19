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
        if(user.length()==0){
            return mostrarOpções();
        }
        else if(isValidMessage(user)) {
            if(user.equals("0") && opçãouser.equals("")){
                return mostrarOpções();
            }
            Integer opção = Integer.parseInt(user);
            String opçãouser2 = opçãouser;
            if (opção == -1) {
                opçãouser = "";
                return "resetado";
            } else if(opção == 0 && opçãouser2.length()>0){
                String concatenador ="";
                String [] op = opçãouser2.split("-");
                for(int x=0;x<op.length-1;x++){
                    concatenador+=op[x]+"-";
                }
                opçãouser2=concatenador;
            } else {
                if (opçãouser2 == "") {
                    opçãouser2 = opção.toString() + "-";
                } else {
                    opçãouser2 += opção.toString() + "-";
                }
            }
            if(opçãouser2==""){
                opçãouser=opçãouser2;
               return mostrarOpções();
            }
            ArrayList<Integer> list = userOptionstr(opçãouser2);
            Mapa ponteiro = null;
            for (int x = 0; x < list.size(); x++) {
                if (x == 0 && list.get(x)<=mapa.size()) {
                        ponteiro = mapa.get(list.get(x));

                } else if(list.get(x)<=ponteiro.getSubseções().size()){
                    ponteiro = ponteiro.getSubseções().get(list.get(x));
                } else {
                    return isInvalido();
                }
            }
            String last = "";
            if (ponteiro != null) {
                last = ponteiro.getInfo() + "\n\n";
                if (ponteiro.getSubseções().size() > 0) {
                    for (int x = 0; x < ponteiro.getSubseções().size(); x++) {
                        last += ponteiro.getSubseções().get(x).getChave() + "\n";
                    }
                }
            }
            opçãouser=opçãouser2;
            return last;
        } else {
            return isInvalido();
        }
    }

    private ArrayList<Integer> userOptionstr(String opçãouser2){
        ArrayList<Integer> list = new ArrayList<Integer>();
        String concatenador = "";
        for (int x = 0; x < opçãouser2.length(); x++) {
            if (Character.isDigit(opçãouser2.charAt(x))) {
                concatenador += Character.toString(opçãouser2.charAt(x));
            } else if (opçãouser2.charAt(x) == '-') {
                list.add(Integer.parseInt(concatenador) - 1);
                concatenador = "";
            }
        }
        return list;
    }

    private boolean isValidMessage(String user){
        for(int x=0;x<user.length();x++){
            if(user.charAt(x)=='-' && x==0) {
                continue;
            } else if(Character.isDigit(user.charAt(x))){
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private String isInvalido(){
        return "Invalido";
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
    private String mostrarOpções(){
        String concatenador="-1 reseta\n\n" +
                "0 volta\n\n";
        for(int x=0;x<mapa.size();x++){
            concatenador+=mapa.get(x).getChave()+"\n";
        }
        return concatenador;
    }

    public ArrayList<Mapa> getMapa() {
        return mapa;
    }
}

package com.example.hogwartsPoints.utils;

import com.example.hogwartsPoints.exception.InvalidCpfException;

public class AppUtils {
    public static boolean  validateCpf (String cpf){
        String rawCpf = cpf.replaceAll("[.\\-]", "");

        String verifierResult = "" + checkVerifier(rawCpf.substring(0,9), 0) + checkVerifier(rawCpf.substring(0,10), 0) ;

        if (!rawCpf.substring(9).equals(verifierResult)) throw new InvalidCpfException();
        return true;
    }

    private static int checkVerifier (String cpf, int result){
        if(cpf.isEmpty()){
            return (result * 10) % 11;
        }else{
            int newResult = (Integer.parseInt(cpf.charAt(0) + "") * (cpf.length()+1)) + result;
            return checkVerifier(cpf.substring(1), newResult );
        }
    }

    public static String trySubstringProblem(String mensagemErro) {
        int indiceInicio = mensagemErro.indexOf("problem: ");
        int indiceFim = mensagemErro.indexOf("; nested exception");

        if (indiceInicio != -1 && indiceFim != -1) {
            return mensagemErro.substring(indiceInicio + "problem: ".length(), indiceFim).trim();
        } else {
            return mensagemErro;
        }
    }
}

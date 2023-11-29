package com.example.hogwartsPoints.utils;

import com.example.hogwartsPoints.exception.InvalidCpfException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class AppUtils {
    public static String validateCpf (String cpf){
        String rawCpf = cpf.replaceAll("[.\\-]", "");

        String verifierResult = "" + checkVerifier(rawCpf.substring(0,9), 0) + checkVerifier(rawCpf.substring(0,10), 0) ;

        if (!rawCpf.substring(9).equals(verifierResult)) throw new InvalidCpfException();
        return rawCpf;
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

    public static void copyNonNullProperties (Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (Objects.isNull(srcValue)) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private static String getRandomAlphanumeric(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}

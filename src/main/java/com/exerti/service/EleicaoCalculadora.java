package com.exerti.service;

public class EleicaoCalculadora {
    
    public double calculaVotosValidos(int totalEleitores, int votosValidos) {
        return (double) votosValidos / totalEleitores * 100;
    }
    
    public double calculaVotosBrancos(int totalEleitores, int votosBrancos) {
        return (double) votosBrancos / totalEleitores * 100;
    }
    
    public double calculaVotosNulos(int totalEleitores, int votosNulos) {
        return (double) votosNulos / totalEleitores * 100;
    }

    public static void main(String[] args) {
        EleicaoCalculadora calculadora = new EleicaoCalculadora();
        
        int totalEleitores = 1000;
        int votosValidos = 800;
        int votosBrancos = 150;
        int votosNulos = 50;
        
        double percentualValidos = calculadora.calculaVotosValidos(totalEleitores, votosValidos);
        double percentualBrancos = calculadora.calculaVotosBrancos(totalEleitores, votosBrancos);
        double percentualNulos   = calculadora.calculaVotosNulos(totalEleitores, votosNulos);
        
        System.out.println("Percentual de votos válidos: " + percentualValidos);
        System.out.println("Percentual de votos brancos: " + percentualBrancos);
        System.out.println("Percentual de votos nulos: " + percentualNulos);
    }
}

package com.exerti.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarcaVeiculo {
    
    
    VOLKSWAGEN("Volkswagen"),
    FORD("Ford"),
    CHEVROLET("Chevrolet"),
    FIAT("Fiat"),
    RENAULT("Renault"),
    PEUGEOT("Peugeot"),
    CITROEN("Citroen"),
    TOYOTA("Toyota"),
    HONDA("Honda"),
    NISSAN("Nissan"),
    MAZDA("Mazda"),
    SUBARU("Subaru"),
    MITSUBISHI("Mitsubishi"),
    SUZUKI("Suzuki"),
    BMW("BMW"),
    MERCEDES_BENZ("Mercedes-Benz"),
    AUDI("Audi"),
    PORSCHE("Porsche"),
    FERRARI("Ferrari"),
    LAMBORGHINI("Lamborghini"),
    MASERATI("Maserati"),
    ALFA_ROMEO("Alfa Romeo"),
    DODGE("Dodge"),
    JEEP("Jeep"),
    CHRYSLER("Chrysler"),
    CADILLAC("Cadillac"),
    LINCOLN("Lincoln"),
    HYUNDAI("Hyundai"),
    KIA("Kia"),
    DS("DS"),
    JAGUAR("Jaguar"),
    LAND_ROVER("Land Rover"),
    MINI("Mini"),
    ROLLS_ROYCE("Rolls-Royce"),
    BENTLEY("Bentley"),
    VOLVO("Volvo"),
    BYD("BYD"),
    GWM("GWM"),
    CHERY("Chery"),
    JAC("JAC");
    
    private final String nome;
    
    MarcaVeiculo(String nome) {
        this.nome = nome;
    }
    
    @JsonValue
    public String getNome() {
        return nome;
    }
    
    @JsonCreator
    public static MarcaVeiculo fromString(String marca) {
        if (marca == null) {
            return null;
        }
        
        // Busca por nome exato
        for (MarcaVeiculo marcaEnum : values()) {
            if (marcaEnum.nome.equalsIgnoreCase(marca.trim())) {
                return marcaEnum;
            }
        }
        
        // Busca por nome sem acentos e espaços
        String marcaNormalizada = marca.trim()
            .replace("ë", "e")
            .replace("é", "e")
            .replace("è", "e")
            .replace("ê", "e")
            .replace("ç", "c")
            .replace("-", " ")
            .toLowerCase();
            
        for (MarcaVeiculo marcaEnum : values()) {
            String nomeNormalizado = marcaEnum.nome
                .replace("ë", "e")
                .replace("é", "e")
                .replace("è", "e")
                .replace("ê", "e")
                .replace("ç", "c")
                .replace("-", " ")
                .toLowerCase();
                
            if (nomeNormalizado.equals(marcaNormalizada)) {
                return marcaEnum;
            }
        }
        
        throw new IllegalArgumentException("Marca não reconhecida: " + marca);
    }
    
    public static boolean isValidMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            return false;
        }
        try {
            fromString(marca);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

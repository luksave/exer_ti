package com.exerti.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarcaVeiculoTest {

    @Test
    void deve_converter_string_para_enum() {
        assertEquals(MarcaVeiculo.HONDA, MarcaVeiculo.fromString("Honda"));
        assertEquals(MarcaVeiculo.TOYOTA, MarcaVeiculo.fromString("Toyota"));
        assertEquals(MarcaVeiculo.BMW, MarcaVeiculo.fromString("BMW"));
        assertEquals(MarcaVeiculo.MERCEDES_BENZ, MarcaVeiculo.fromString("Mercedes-Benz"));
        assertEquals(MarcaVeiculo.VOLKSWAGEN, MarcaVeiculo.fromString("Volkswagen"));
    }

    @Test
    void deve_ignorar_maiusculas_minusculas() {
        assertEquals(MarcaVeiculo.HONDA, MarcaVeiculo.fromString("honda"));
        assertEquals(MarcaVeiculo.HONDA, MarcaVeiculo.fromString("HONDA"));
        assertEquals(MarcaVeiculo.HONDA, MarcaVeiculo.fromString("  Honda  "));
    }

    @Test
    void deve_ignorar_acentos() {
        assertEquals(MarcaVeiculo.CITROEN, MarcaVeiculo.fromString("Citroën"));
        assertEquals(MarcaVeiculo.CITROEN, MarcaVeiculo.fromString("Citroen"));
    }

    @Test
    void deve_ignorar_hifens() {
        assertEquals(MarcaVeiculo.MERCEDES_BENZ, MarcaVeiculo.fromString("Mercedes-Benz"));
        assertEquals(MarcaVeiculo.MERCEDES_BENZ, MarcaVeiculo.fromString("Mercedes Benz"));
        assertEquals(MarcaVeiculo.ROLLS_ROYCE, MarcaVeiculo.fromString("Rolls-Royce"));
        assertEquals(MarcaVeiculo.ROLLS_ROYCE, MarcaVeiculo.fromString("Rolls Royce"));
    }

    @Test
    void deve_lancar_excecao_para_marca_invalida() {
        assertThrows(IllegalArgumentException.class, () -> MarcaVeiculo.fromString("MarcaInvalida"));
        assertThrows(IllegalArgumentException.class, () -> MarcaVeiculo.fromString("Hondaa"));
        assertThrows(IllegalArgumentException.class, () -> MarcaVeiculo.fromString("Toyta"));
    }

    @Test
    void deve_retornar_null_para_string_nula() {
        assertNull(MarcaVeiculo.fromString(null));
    }

    @Test
    void deve_validar_marca_correta() {
        assertTrue(MarcaVeiculo.isValidMarca("Honda"));
        assertTrue(MarcaVeiculo.isValidMarca("Toyota"));
        assertTrue(MarcaVeiculo.isValidMarca("BMW"));
        assertTrue(MarcaVeiculo.isValidMarca("Mercedes-Benz"));
        assertTrue(MarcaVeiculo.isValidMarca("honda"));
        assertTrue(MarcaVeiculo.isValidMarca("HONDA"));
    }

    @Test
    void deve_rejeitar_marca_invalida() {
        assertFalse(MarcaVeiculo.isValidMarca("MarcaInvalida"));
        assertFalse(MarcaVeiculo.isValidMarca("Hondaa"));
        assertFalse(MarcaVeiculo.isValidMarca("Toyta"));
        assertFalse(MarcaVeiculo.isValidMarca("BMWX"));
    }

    @Test
    void deve_rejeitar_marca_nula() {
        assertFalse(MarcaVeiculo.isValidMarca(null));
    }

    @Test
    void deve_retornar_nome_da_marca() {
        assertEquals("Honda", MarcaVeiculo.HONDA.getNome());
        assertEquals("Toyota", MarcaVeiculo.TOYOTA.getNome());
        assertEquals("BMW", MarcaVeiculo.BMW.getNome());
        assertEquals("Mercedes-Benz", MarcaVeiculo.MERCEDES_BENZ.getNome());
        assertEquals("Volkswagen", MarcaVeiculo.VOLKSWAGEN.getNome());
    }

    @Test
    void deve_conter_todas_as_marcas() {
        MarcaVeiculo[] marcas = MarcaVeiculo.values();
        
        assertTrue(marcas.length > 0);
        
        // Verificar se algumas marcas importantes estão presentes
        boolean temHonda = false;
        boolean temToyota = false;
        boolean temBMW = false;
        
        for (MarcaVeiculo marca : marcas) {
            if (marca == MarcaVeiculo.HONDA) temHonda = true;
            if (marca == MarcaVeiculo.TOYOTA) temToyota = true;
            if (marca == MarcaVeiculo.BMW) temBMW = true;
        }
        
        assertTrue(temHonda);
        assertTrue(temToyota);
        assertTrue(temBMW);
    }
}

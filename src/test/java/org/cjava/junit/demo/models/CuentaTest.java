package org.cjava.junit.demo.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {


    @Test
    void testNombreCuenta(){

        Cuenta cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        //cuenta.setPersona("Andres");

        String esperado = "Julio";
        String real = cuenta.getPersona();

        assertEquals(esperado, real );
        assertTrue(esperado.equals(real));

    }
}
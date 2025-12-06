package org.cjava.junit.demo.models;

import org.cjava.junit.demo.exceptions.SaldoInsuficienteException;
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

    @Test
    void testCuenta() {
        Cuenta cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        assertEquals( 1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse( cuenta.getSaldo().compareTo(BigDecimal.ZERO)  < 0 );
    }

    @Test
    void testReferenciaCuenta() {
        Cuenta cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        Cuenta cuenta2 = new Cuenta("Julio", new BigDecimal("1000.12345"));
        assertEquals(cuenta, cuenta2);
    }

    @Test
    void testDebitoCuenta() {
        Cuenta cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        cuenta.debitoCuenta(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
    }

    @Test
    void testDineroInsuficienteException() {
        Cuenta cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        Exception e = assertThrows(SaldoInsuficienteException.class, () -> {
            cuenta.debitoCuenta(new BigDecimal(1100.12345));
        });
        String actual = e.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

}
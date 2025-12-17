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
        assertNotNull(real, "La cuenta no puede ser nula");
        assertEquals(esperado, real, "El nombre de la cuenta no es el que se esperaba" );
        assertTrue(esperado.equals(real), "El nombre de la cuenta  esperada debe ser igual a la real");
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

    @Test
    void testTransferirDineroCuenta() {

        Cuenta cuenta1 = new Cuenta("Julio", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.transferirDineroCuenta(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuentas() {

        Cuenta cuenta1 = new Cuenta("Julio", new BigDecimal("2500"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("1500.8989"));

        Banco banco = new Banco();
        banco.setNombre("Banco del Estado");
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.transferirDineroCuenta(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(
                ()-> { assertEquals(2, banco.getCuentas().size());},
                ()-> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString() ),
                ()-> assertEquals("3000", cuenta1.getSaldo().toPlainString() ),
                ()-> assertEquals("Banco del Estado", cuenta1.getBanco().getNombre() ),
                ()-> assertEquals("Julio", banco.getCuentas()
                        .stream()
                        .filter( c -> c.getPersona().equals("Julio") )
                        .findFirst()
                        .get().getPersona() ),
                ()-> assertTrue( banco.getCuentas()
                        .stream()
                        .anyMatch( c -> c.getPersona().equals("Jhon Doe") ) )
        );
    }


}
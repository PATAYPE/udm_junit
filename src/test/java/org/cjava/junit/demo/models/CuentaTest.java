package org.cjava.junit.demo.models;

import org.cjava.junit.demo.exceptions.SaldoInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {


    Cuenta cuenta ;

    @BeforeAll
    static void beforeAll() {
        System.out.printf("inicializando el test");
    }

    @AfterAll
    static void afterAll() {
        System.out.printf("Finalizando el test");
    }

    @BeforeEach
    public void initMetodoTest(){
        this.cuenta = new Cuenta("Julio", new BigDecimal("1000.12345"));
        System.out.println("Inicializando el test metodo");
    }

    @AfterEach
    void tearDown() {
        System.out.printf("Finalizando el metodo de prueba %n");
    }

    @Test
    @DisplayName("probando el nombre de la cuenta")
    void testCuenta_1() {
        String esperado = "Andres";
        //Assertions.assertEquals(cuenta.getPersona(), esperado);
        assertNotEquals(cuenta.getPersona(), esperado);
    }

    @Test
    @DisplayName("Probando el nombre de la cuenta, usando assertAll")
    void testNombreCuenta(){
        //cuenta.setPersona("Andres");
        String esperado = "Julio";
        String real = cuenta.getPersona();
        assertNotNull(real, "La cuenta no puede ser nula");
        assertEquals(esperado, real, "El nombre de la cuenta no es el que se esperaba" );
        assertTrue(esperado.equals(real), "El nombre de la cuenta  esperada debe ser igual a la real");
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta, usando assertAll")
    void testCuenta() {
        assertEquals( 1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse( cuenta.getSaldo().compareTo(BigDecimal.ZERO)  < 0 );
    }

    @Test
    @DisplayName("Probando la referencia de la cuenta")
    void testReferenciaCuenta() {
        Cuenta cuenta2 = new Cuenta("Julio", new BigDecimal("1000.12345"));
        assertEquals(cuenta, cuenta2);
    }

    @Test
    @DisplayName("Probando el debito de la cuenta")
    void testDebitoCuenta() {
        cuenta.debitoCuenta(new BigDecimal(100));

        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
    }

    @Test
    @DisplayName("Probando el debito de la cuenta, con dinero insuficiente")
    void testDineroInsuficienteException() {

        Exception e = assertThrows(SaldoInsuficienteException.class, () -> {
            this.cuenta.debitoCuenta(new BigDecimal(1100.12345));
        });
        String actual = e.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Probando el el saldo de la cuenta, usando assertAll")
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
    @DisplayName("Probando la relacion entre el banco y las cuentas, usando assertAll")
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
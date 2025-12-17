package org.cjava.junit.demo.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Banco {

    private String nombre;

    private List<Cuenta> cuentas;

    public Banco() {
        cuentas = new ArrayList<Cuenta>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public void addCuenta(Cuenta cuenta){
        this.cuentas.add(cuenta);
        cuenta.setBanco(this);
    }

    public void transferirDineroCuenta(Cuenta origen, Cuenta destino, BigDecimal valor){
        origen.debitoCuenta(valor);
        destino.creditoCuenta(valor);
    }
}

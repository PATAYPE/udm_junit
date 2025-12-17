package org.cjava.junit.demo.models;

import org.cjava.junit.demo.exceptions.SaldoInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {

    private String persona;

    private BigDecimal saldo;

    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public Cuenta() {
    }

    public String getPersona() {
        return persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void debitoCuenta(BigDecimal monto){
        BigDecimal montoInsuficiente = this.saldo.subtract(monto);
        if ( montoInsuficiente.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new SaldoInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = this.saldo.subtract(monto);
    }

    public void creditoCuenta(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {
       if ( obj == null ||   !(obj instanceof Cuenta) ) {
           return false;
       }
       Cuenta c = (Cuenta) obj;
       if ( this.persona == null || this.saldo == null ) {
           return false;
       }
       return this.persona.equals(c.getPersona()) &&
              this.saldo.compareTo( c.getSaldo() ) == 0;
    }
}

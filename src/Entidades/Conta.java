package Entidades;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Conta {

	private Integer agenciaBancaria;
	private Integer numeroDaConta;
	private Integer senha;
	private Double saldo;

	public Conta(Integer agenciaBancaria, Integer numeroDaConta, Integer senha, Double saldo) {
		super();
		this.agenciaBancaria = agenciaBancaria;
		this.numeroDaConta = numeroDaConta;
		this.senha = senha;
		this.saldo = saldo;
	}

	public Integer getAgenciaBancaria() {
		return agenciaBancaria;
	}

	public Integer getNumeroDaConta() {
		return numeroDaConta;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Integer getSenha() {
		return senha;
	}

	public void deposito(Double montante) {

		this.saldo = saldo += montante;
	}

	public void saque(Double montante) {

		this.saldo = saldo -= montante;
	}

	public void gravacaoOperacao(String caminho, Double montante, String nomeOperacao) {

		try (BufferedWriter brr = new BufferedWriter(new FileWriter(caminho, true))) {
			brr.write(nomeOperacao + ": R$ " + String.format("%.2f", montante));
			brr.newLine();

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

}

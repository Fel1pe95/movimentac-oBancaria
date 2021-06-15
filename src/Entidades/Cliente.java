package Entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Cliente {

	private String nome;
	private Integer cpf;

	Conta conta;

	public Cliente(String nome, Integer cpf, Conta conta) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.conta = conta;
	}

	public Conta getConta() {
		return conta;
	}

	public String getNome() {
		return nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void leituraCliente(String caminho, Map<Integer, Cliente> map) {

		try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {

			String linha = br.readLine();
			while (linha != null) {

				String[] campos = linha.split(",");
				String nome = campos[0];
				Integer agencia000 = Integer.parseInt(campos[1]);
				Integer conta000 = Integer.parseInt(campos[2]);
				Integer cpf000 = Integer.parseInt(campos[3]);
				Integer senha000 = Integer.parseInt(campos[4]);
				Double saldo000 = Double.parseDouble(campos[5]);
				map.put(senha000, new Cliente(nome, cpf000, new Conta(agencia000, conta000, senha000, saldo000)));
				linha = br.readLine();

			}

		} catch (IOException e) {

			System.out.println("Erro: " + e.getMessage());
		}
	}

	public void gravacaoCliente(String caminho, Map<?, ?> map) {

		try (BufferedWriter brr = new BufferedWriter(new FileWriter(caminho))) {

			for (Object obj : map.values()) {
				brr.write(obj.toString());
				brr.newLine();
			}

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((conta == null) ? 0 : conta.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (conta == null) {
			if (other.conta != null)
				return false;
		} else if (!conta.equals(other.conta))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome + "," + conta.getAgenciaBancaria() + "," + conta.getNumeroDaConta() + "," + cpf + ","
				+ conta.getSenha() + "," + String.format("%.2f", conta.getSaldo());
	}

}

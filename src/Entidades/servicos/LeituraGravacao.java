package Entidades.servicos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import Entidades.Cliente;
import Entidades.Conta;

public class LeituraGravacao {

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

	public void leituraExtrato(String caminho) {

		try (BufferedReader brr = new BufferedReader(new FileReader(caminho))) {

			String linha = brr.readLine();
			while(linha != null) {
				System.out.println(linha);
				linha = brr.readLine();
			}
			
		}catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

	}

}

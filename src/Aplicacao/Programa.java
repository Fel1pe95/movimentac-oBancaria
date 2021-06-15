package Aplicacao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import Entidades.Cliente;
import Entidades.Compra;
import Entidades.servicos.LeituraGravacao;

public class Programa {

	public static void main(String[] args) throws ParseException {

		Locale.setDefault(Locale.US);
		Map<Integer, Cliente> agencia = new LinkedHashMap<>();
		Map<Integer, Compra> fatura = new LinkedHashMap<>();
		List<Double> valores = new ArrayList<>();
		Compra compra = null;
		Integer resp;
		LeituraGravacao leitura = new LeituraGravacao();
		Scanner sc = new Scanner(System.in);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String caminhoArqCliente = "C:\\Users\\Andressa\\Desktop\\Programação\\Projeto movientacao bancaria\\Clientes\\Clientes.csv";
		leitura.leituraCliente(caminhoArqCliente, agencia);
		//PEDE AO USUARIO AGENCIA E CONTA
		System.out.print("Agencia: ");
		Integer agencia00 = sc.nextInt();
		System.out.print("Conta: ");
		Integer conta00 = sc.nextInt();
		System.out.print("Senha: ");
		Integer senha = sc.nextInt();
		// VERIFICA SE EXISTEM A CONTA E A AGENCIA NO DOCUMENTO, SE HOUVER, VERIFICA SE
		// A SENHA É A MESMA
		if (agencia.get(senha).getConta().getAgenciaBancaria().equals(agencia00)
				&& agencia.get(senha).getConta().getNumeroDaConta().equals(conta00)) {
			do {
				
				String caminhoArqExtrato = "C:\\Users\\Andressa\\Desktop\\Programação\\Projeto movientacao bancaria\\Clientes\\Extrato "
						+ agencia.get(senha).getNome() + ".csv";
				String caminhoArqFatura =  "C:\\Users\\Andressa\\Desktop\\Programação\\Projeto movientacao bancaria\\Clientes\\Fatura"
						+ agencia.get(senha).getNome() + ".csv";
				System.out.println("----------------------------------");
				System.out.println("[1] - Compra\n[2] - pagamento fatura\n[3] - Deposito\n[4] - Saque\n[5] - Extrato");
				System.out.println("----------------------------------");
				resp = sc.nextInt();
				switch (resp) {
				// REGISTRA A COMPRA E GRAVA NO DOCUMENTO DE FATURA
				case 1:
					System.out.print("Produto: ");
					String produto = sc.next();
					System.out.print("Valor: ");
					Double preco = sc.nextDouble();
					System.out.print("Data: ");
					Date data = sdf.parse(sc.next());
					Random random = new Random();
					Integer codCompra = random.nextInt(500) + 100;
					compra = new Compra(produto, preco, data);
					fatura.put(codCompra, compra);
					fatura.get(codCompra).gravacaoCompra(caminhoArqFatura, fatura, codCompra);
					System.out.println("Compra efetuada com sucesso!");
					break;
				// REALIZA O PAGAMENTO DA FATURA E GRAVA O SALDO NO DOCUMENTO, SENDO ELE INTEGRAL OU NÃO, MOSTRA O SALDO FINAL DA CONTA AOÓS O PAGAMENTO
				case 2:
					String operacao = "Pagamento fatura";
					try (BufferedReader brrr = new BufferedReader(new FileReader(caminhoArqFatura))) {
						String linhaa = brrr.readLine();
						while (linhaa != null) {
							String[] camposs = linhaa.split(",");
							Double valor = Double.parseDouble(camposs[1]);
							valores.add(valor);
							linhaa = brrr.readLine();
						}
						double total = 0;
						for (Double val : valores) {
							total += val.doubleValue();
						}
						System.out.println("Valor da fatura: " + String.format("%.2f", total));
						System.out.println("Pagar valor total? (S/N)");
						char resposta = sc.next().charAt(0);
						if (resposta == 's' || resposta == 'S') {
							agencia.get(senha).getConta().saque(total);
							agencia.get(senha).getConta().gravacaoOperacao(caminhoArqExtrato, total, operacao);
							System.out.println("Valor total pago: " + String.format("%.2f", total));
							System.out.println(
									"Saldo: " + String.format("%.2f", agencia.get(senha).getConta().getSaldo()));
						} else {
							System.out.print("Valor a ser pago: ");
							Double valor = sc.nextDouble();
							agencia.get(senha).getConta().saque(valor);
							agencia.get(senha).getConta().gravacaoOperacao(caminhoArqExtrato, valor, operacao);
							System.out.println("Valor pago: " + String.format("%.2f", valor));
							System.out.println(
									"saldo: " + String.format("%.2f", agencia.get(senha).getConta().getSaldo()));
						}
					} catch (IOException e) {
						System.out.println("Erro: " + e.getMessage());
					}
					break;
				// REALIZA A OPERACAO DE DEPOSITO E GRAVA NO DOCUMENTO
				case 3:
					String operacao0 = "Deposito";
					System.out.println("Valor do deposito: ");
					Double montante = sc.nextDouble();
					agencia.get(senha).getConta().deposito(montante);
					agencia.get(senha).getConta().gravacaoOperacao(caminhoArqExtrato,montante,operacao0);
					System.out.println("Deposito realizado com sucesso!");
					break;
				// REALIZA A OPERACAO DE SAQUE E GRAVA NO DOCUMENTO
				case 4:
					String operacao00 = "saque";
					System.out.println("Valor do saque: ");
					Double montante00 = sc.nextDouble();
					agencia.get(senha).getConta().saque(montante00);
					agencia.get(senha).getConta().gravacaoOperacao(caminhoArqExtrato, montante00, operacao00);
					System.out.println("saque realizado com sucesso!");
					break;
					// REALIZA LEITURA DO EXTRATO
				case 5:
					leitura.leituraExtrato(caminhoArqExtrato);
					break;
				}
			} while (resp != 0);
		} else {
			System.out.println("Conta inexistnte!");
		}
		// GRAVA NO DOCUMENTO O ESTADO ATUAL DA CONTA
		agencia.get(senha).gravacaoCliente(caminhoArqCliente, agencia);
		sc.close();
	}
}

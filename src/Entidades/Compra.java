package Entidades;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Compra {

	private String produto;
	private Double preco;
	private Date data;

	Conta conta;

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Compra(String produto, Double preco, Date data) {
		super();
		this.produto = produto;
		this.preco = preco;
		this.data = data;
	}

	public String getProduto() {
		return produto;
	}

	public Double getPreco() {
		return preco;
	}

	public Date getData() {
		return data;
	}

	public void gravacaoCompra(String caminho, Map<?, ?> map, Integer num) {

		try (BufferedWriter brr = new BufferedWriter(new FileWriter(caminho, true))) {
			brr.write(map.get(num).toString());
			brr.newLine();

		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return produto + "," + String.format("%.2f", preco) + "," + sdf.format(data);
	}

}

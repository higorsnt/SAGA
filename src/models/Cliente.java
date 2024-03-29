package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que molda um objeto do tipo Cliente.
 * 
 * @author Higor Santos - 118110808.
 *
 */
public class Cliente implements Comparable<Cliente>{
	
	/** CPF do cliente. */
	private String cpf;
	/** Nome do cliente. */
	private String nome;
	/** Email do cliente. */
	private String email;
	/** Localização onde o cliente trabalha. */
	private String localizacao;
	/** Coleção com todas as contas de um cliente. */
	private Map <String, Conta> contas;
	
	/**
	 * Constrói um cliente.
	 * 
	 * @param cpf é o identificador do cliente.
	 * @param nome é o nome do cliente.
	 * @param email é o email do cliente.
	 * @param localizacao é o local onde o cliente trabalha.
	 */
	public Cliente(String cpf, String nome, String email, String localizacao) {
		if (cpf == null || cpf.length() != 11 || cpf.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro do cliente: cpf invalido.");
		}else if (nome == null || (nome.trim().equals(""))) {
			throw new IllegalArgumentException("Erro no cadastro do cliente: nome nao pode ser vazio ou nulo.");
		} else if (email == null || (email.trim().equals(""))) {
			throw new IllegalArgumentException("Erro no cadastro do cliente: email nao pode ser vazio ou nulo.");
		}else if (localizacao == null || (localizacao.trim().equals(""))) {
			throw new IllegalArgumentException("Erro no cadastro do cliente: localizacao nao pode ser vazia ou nula.");
		}
		
		this.cpf = cpf.trim();
		this.nome = nome.trim();
		this.email = email.trim();
		this.localizacao = localizacao.trim();
		this.contas = new HashMap<>();
	}
	
	/**
	 * Informa o nome do cliente.
	 * 
	 * @return O nome do cliente.
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Atualiza o nome do cliente.
	 * 
	 * @param nome é o novo nome do cliente.
	 */
	public void setNome(String nome) {
		if (nome == null || (nome.trim().equals(""))) {
			throw new IllegalArgumentException("Erro na edicao do cliente: novo valor nao pode ser vazio ou nulo.");
		}
		
		this.nome = nome;
	}

	/**
	 * Informa o email do cliente.
	 * 
	 * @return O email do cliente.
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Atualiza o email do cliente.
	 * 
	 * @param email é o novo email do cliente.
	 */
	public void setEmail(String email) {
		if (email == null || (email.trim().equals(""))) {
			throw new IllegalArgumentException("Erro na edicao do cliente: novo valor nao pode ser vazio ou nulo.");
		}
		
		this.email = email;
	}

	/**
	 * Informa a localização do cliente.
	 * 
	 * @return A localização do cliente.
	 */
	public String getLocalizacao() {
		return this.localizacao;
	}

	/**
	 * Atualiza a localização do cliente.
	 * 
	 * @param localizacao é a nova localização do cliente.
	 */
	public void setLocalizacao(String localizacao) {
		if (localizacao == null || localizacao.trim().equals("")) {
			throw new IllegalArgumentException("Erro na edicao do cliente: novo valor nao pode ser vazia ou nula.");
		}
		
		this.localizacao = localizacao;
	}
	
	/**
	 * Adiciona uma compra pra um cliente.
	 * 
	 * @param fornecedor é o identificador do fornecedor.
	 * @param data é a data onde a compra foi realizada.
	 * @param nomeProd é o nome do produto.
	 * @param descProd é a descrição do produto.
	 * @param preco é o preço da compra.
	 * 
	 * @return Retorna o fornecedor.
	 */
	public String adicionaCompra(String fornecedor, String data, String nomeProd, String descProd, double preco) {
		if (this.contas.containsKey(fornecedor)) {
			this.contas.get(fornecedor).adicionaCompra(fornecedor, this.getNome(), nomeProd, descProd, data, preco);
		} else {
			this.contas.put(fornecedor, new Conta(fornecedor));
			this.contas.get(fornecedor).adicionaCompra(fornecedor, this.getNome(), nomeProd, descProd, data, preco);
		}
		return fornecedor;
	}
	
	/**
	 * Totaliza da conta de um fornecedor para um dado cliente.
	 * 
	 * @param fornecedor é o identificador do fornecedor.
	 * 
	 * @return O total da conta de um fornecedor com o cliente.
	 */
	public double getDebito(String fornecedor) {
		if(!this.contas.containsKey(fornecedor)) {
			throw new IllegalArgumentException("Erro ao recuperar debito: cliente nao tem debito com fornecedor.");
		}
		
		return this.contas.get(fornecedor).getDebito();
	}
	
	/**
	 * Informa o CPF do cliente.
	 * 
	 * @return O CPF do cliente.
	 */
	public String getCpf() {
		return this.cpf;
	}
	
	/**
	 * Exibe as contas de um cliente com um fornecedor.
	 * 
	 * @param fornecedor é o identificador do fornecedor.
	 * 
	 * @return Retorna a representação textual da conta do cliente com um fornecedor.
	 */
	public String exibeContas(String fornecedor) {
		if (!this.contas.containsKey(fornecedor)) {
			throw new IllegalArgumentException("Erro ao exibir conta do cliente: cliente nao tem nenhuma conta com o fornecedor.");
		}
		
		return "Cliente: " + this.nome + " | " + this.contas.get(fornecedor).toString();
	}
	
	/**
	 * Exibe a representação textual de todas as contas do cliente.
	 * 
	 * @return Retorna a representação textual de todas as contas do cliente.
	 */
	public String exibeContasClientes() {
		if (this.contas.size() == 0) {
			throw new IllegalArgumentException("Erro ao exibir contas do cliente: cliente nao tem nenhuma conta.");
		}
		List<Conta> lista = new ArrayList<>(this.contas.values());
		Collections.sort(lista);
		return "Cliente: " + this.nome + " | " + lista.stream().map(p -> p.toString()).collect(Collectors.joining(" | "));
	}
	
	/**
	 * Realiza a quitação do débito do cliente com um fornecedor.
	 * 
	 * @param fornecedor é o identificador do fornecedor.
	 */
	public void realizaPagamento(String fornecedor) {
		if (!this.contas.containsKey(fornecedor)) {
			throw new IllegalArgumentException("Erro no pagamento de conta: nao ha debito do cliente associado a este fornecedor.");
		}
		this.contas.remove(fornecedor);
	}
	
	/**
	 * Lista todas as compras do cliente, idependente do fornecedor.
	 * 
	 * @return Uma lista com todas as compras do cliente.
	 */
	public List<Compra> listarCompras() {
		List<Compra> lista = new ArrayList<>();
		
		for (Conta c : this.contas.values()) {
			lista.addAll(c.listarCompras());
		}
		return lista;
	}

	@Override
	public int compareTo(Cliente c) {
		return this.nome.compareTo(c.getNome());
	}

	@Override
	public String toString() {
		return this.nome + " - " + this.localizacao + " - " + this.email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
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
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
	
}
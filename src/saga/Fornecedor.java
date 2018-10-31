package saga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe que molda um objeto do tipo Fornecedor.
 * 
 * @author higor
 *
 */
public class Fornecedor implements Comparable<Fornecedor>{
	
	/** Nome do fornecedor. */
	private String nome;
	/** Email do fornecedor. */
	private String email;
	/** Telefone do fornecedor. */
	private String telefone;
	/** Produtos que são comercializados pelo fornecedor. */
	private Map <String, Produto> produtos;
	
	/**
	 * Constrói um Fornecedor.
	 * 
	 * @param nome é o nome do fornecedor.
	 * @param email é o email do fornecedor.
	 * @param telefone é o telefone do fornecedor.
	 */
	public Fornecedor(String nome, String email, String telefone) {
		if (nome.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro do fornecedor: nome nao pode ser vazio ou nulo.");
		} else if (nome.equals(null)) {
			throw new NullPointerException("Erro no cadastro do fornecedor: nome nao pode ser vazio ou nulo.");
		}
		if (email.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro do fornecedor: email nao pode ser vazio ou nulo.");
		} else if(email.equals(null)) {
			throw new NullPointerException("Erro no cadastro do fornecedor: email nao pode ser vazio ou nulo.");
		}
		if (telefone.trim().equals("")) {
			throw new IllegalArgumentException("Erro no cadastro do fornecedor: telefone nao pode ser vazio ou nulo.");
		} else if (telefone.equals(null)) {
			throw new NullPointerException("Erro no cadastro do fornecedor: telefone nao pode ser vazio ou nulo.");
		}
		
		nome = nome.trim();
		email = email.trim();
		telefone = telefone.trim();
		
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.produtos = new HashMap<>();
	}
	
	/**
	 * Informa o nome do fornecedor.
	 * 
	 * @return O nome do fornecedor.
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * Informa o email do fornecedor.
	 * 
	 * @return O email do fornecedor.
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Atualiza o email do fornecedor.
	 * 
	 * @param email é o novo email do fornecedor.
	 */
	public void setEmail(String email) {
		if(email.trim().equals("")) {
			throw new IllegalArgumentException("Erro na edicao do fornecedor: novo valor nao pode ser vazio ou nulo.");
		} else if (email.equals(null)) {
			throw new IllegalArgumentException("Erro na edicao do fornecedor: novo valor nao pode ser vazio ou nulo.");
		}
		
		this.email = email;
	}

	/**
	 * Informa o telefone do fornecedor.
	 * 
	 * @return O telefone do fornecedor.
	 */
	public String getTelefone() {
		return this.telefone;
	}

	/**
	 * Atualiza o telefone do fornecedor.
	 * 
	 * @param telefone é o novo telefone do fornecedor.
	 */
	public void setTelefone(String telefone) {
		if (telefone.trim().equals("") || telefone.equals(null)) {
			throw new IllegalArgumentException("Erro na edicao do fornecedor: novo valors nao pode ser vazio ou nulo.");
		}
		
		this.telefone = telefone;
	}
	
	public void editaPrecoProduto(String key, double preco) {
		if (!this.produtos.containsKey(key)) {
			throw new IllegalArgumentException("Erro na edicao de produto: produto nao existe.");
		}
		
		this.produtos.get(key).setPreco(preco);
	}

	/**
	 * Cadastra um produto comercializado pelo fornecedor 
	 * 
	 * @param nome é o nome do produto.
	 * @param descricao é a descrição do produto.
	 * @param preco é o preço do produto.
	 * 
	 * @return Um boolean informando se foi ou não bem sucedido o cadastro.
	 */
	public void cadastraProduto(String nome, String descricao, double preco) {
		if (nome.trim().equals("") || nome.equals(null)) {
			throw new IllegalArgumentException("Erro no cadastro de produto: nome nao pode ser vazio ou nulo.");
		} else if (descricao.trim().equals("") || descricao.trim().equals(null)) {
			throw new IllegalArgumentException("Erro no cadastro de produto: descricao nao pode ser vazia ou nula.");
		}
		if (preco <= 0) {
			throw new IllegalArgumentException("Erro no cadastro de produto: preco invalido.");
		}
		
		nome = nome.trim();
		descricao = descricao.trim();
		
		String key = nome + " " + descricao;
		if (this.produtos.containsKey(key)) {
			throw new IllegalArgumentException("Erro no cadastro de produto: produto ja existe.");
		}
		
		this.produtos.put(key, new Produto(nome, descricao, preco));
	}
	
	/**
	 * Dado o nome e uma descrição é retornado um produto de um fornecedor.
	 * 
	 * @param key é o nome e a descrição do produto.
	 * 
	 * @return Uma string com a representação do produto.
	 */
	public String retornaProduto(String key) {
		if (!this.produtos.containsKey(key)) {
			throw new IllegalArgumentException("Erro na exibicao de produto: produto nao existe.");
		}
		
		return this.produtos.get(key).toString();
	}
	
	/**
	 * Método que informa todos os produtos comercializados pelo fornecedor.
	 * 
	 * @return Uma string com todos os produtos comercializados pelo fornecedor.
	 */
	public String exibeProdutosFornecedor() {
		String saida = "";
		int cont = 0;
		
		List<Produto> lista = new ArrayList<>(this.produtos.values());
		Collections.sort(lista);
		return lista.stream().map(p -> this.nome + " - " + p.toString()).collect(Collectors.joining(" | "));		
	}
	
	public boolean removeProduto(String key) {
		if (!this.produtos.containsKey(key)) {
			return false;
		}
		
		this.produtos.remove(key);
		return true;
	}
	
	@Override
	public int compareTo(Fornecedor o) {
		return this.getNome().compareTo(o.getNome());
	}

	@Override
	public String toString() {
		return this.nome + " - " + this.email + " - " + this.telefone;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Fornecedor other = (Fornecedor) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}
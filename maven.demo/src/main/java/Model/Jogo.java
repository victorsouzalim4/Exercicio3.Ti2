package Model;

public class Jogo {
	private int id;
	private String nome;
	private String tipo;
	private float preco;
	private int ano;
	
	public Jogo() {
		id = -1;
		nome = "";
		preco = 0.00F;
		tipo = " ";
	}

	public Jogo(int id, String nome, String tipo, float preco, int ano) {
		setId(id);
		setNome(nome);
		setPreco(preco);
		setTipo(tipo);
		setAno(ano);
	}		
	
	public int getID() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public float getPreco() {
		return preco;
	}
	
	public int getAno() {
		return ano;
	}
	
	
	
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	
	@Override
	public String toString() {
		return "Id: " + id + "Jogo: " + nome +   "Tipo: " + tipo + "Preço: R$" + preco + "   Ano: " + ano ;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Jogo) obj).getID());
	}	
}
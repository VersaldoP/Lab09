package it.polito.tdp.borders.model;

public class Country {
	private int id;
	private String abb;
	private String nome;
	
	public Country(int id, String abb, String nome) {
		super();
		this.id = id;
		this.abb = abb;
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbb() {
		return abb;
	}

	public void setAbb(String abb) {
		this.abb = abb;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome+"\n";
	}
	
	
	
}

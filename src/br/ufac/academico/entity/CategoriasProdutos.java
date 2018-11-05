package br.ufac.academico.entity;

public class CategoriasProdutos {
	
	
	private int catCodigo;
	private int catIdentificador;
	private String catDescricao;

public CategoriasProdutos(){
		
	}
	
	public CategoriasProdutos(int catCodigo, int catIdentificador, String catDescricao) {
		super();
		this.catCodigo = catCodigo;
		this.catIdentificador = catIdentificador;
		this.catDescricao = catDescricao;
	}
	
	
	
	
	public int getCatCodigo() {
		return catCodigo;
	}
	public void setCatCodigo(int catCodigo) {
		this.catCodigo = catCodigo;
	}
	public int getCatIdentificador() {
		return catIdentificador;
	}
	public void setCatIdentificador(int catIdentificador) {
		this.catIdentificador = catIdentificador;
	}
	public String getCatDescricao() {
		return catDescricao;
	}
	public void setCatDescricao(String catDescricao) {
		this.catDescricao = catDescricao;
	}
	
	
	
	
	

}

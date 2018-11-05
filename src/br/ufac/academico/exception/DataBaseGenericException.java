package br.ufac.academico.exception;

public class DataBaseGenericException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataBaseGenericException(int codigo, String mensagem){
		super("Exce��o generica do servidor: \n\t[" 
				+ codigo+ "] -> "
				+ mensagem+"!");
	}
}

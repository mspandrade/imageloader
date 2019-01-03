package com.mspaulo.imageloader.service.exception;

public class FileNotFound extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public FileNotFound(String id) {
		super("ARQUIVO NAO ENCONTRADO PARA O ID: " + id);
	}

}

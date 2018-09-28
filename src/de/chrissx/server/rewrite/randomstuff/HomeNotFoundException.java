package de.chrissx.server.rewrite.randomstuff;

public class HomeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5872747852756581910L;
	
	public HomeNotFoundException(String message) {
		super(message);
	}
}
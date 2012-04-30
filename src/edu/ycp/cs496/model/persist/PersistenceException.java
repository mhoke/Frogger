package edu.ycp.cs496.model.persist;

/**
 * Exception used to indicate an error loading or
 * storing a model object.
 */
public class PersistenceException extends Exception {
	private static final long serialVersionUID = 1L;

	public PersistenceException(String msg) {
		super(msg);
	}
	
	public PersistenceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

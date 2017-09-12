package org.fran.cloud.mq.aws.exceptions;

public class SQSInitializationException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public SQSInitializationException(){
		super();
	}
	public SQSInitializationException(String message){
		super(message);
	}
	public SQSInitializationException(Throwable throwable){
		super(throwable);
	}
	public SQSInitializationException(String message, Throwable throwable){
		super(message, throwable);
	}
}

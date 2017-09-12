package org.fran.cloud.mq.aws.exceptions;

public class SNSInitializationException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public SNSInitializationException(){
		super();
	}
	public SNSInitializationException(String message){
		super(message);
	}
	public SNSInitializationException(Throwable throwable){
		super(throwable);
	}
	public SNSInitializationException(String message, Throwable throwable){
		super(message, throwable);
	}
}

package org.fran.cloud.mq.aws.exceptions;

public class SQSMessageReceiveException extends Exception{
	
	private static final long serialVersionUID = 1L;
	public SQSMessageReceiveException(){
		super();
	}
	public SQSMessageReceiveException(String message){
		super(message);
	}
	public SQSMessageReceiveException(Throwable throwable){
		super(throwable);
	}
	public SQSMessageReceiveException(String message, Throwable throwable){
		super(message, throwable);
	}
}

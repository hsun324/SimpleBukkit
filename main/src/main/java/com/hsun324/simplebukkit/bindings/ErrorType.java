package com.hsun324.simplebukkit.bindings;

public class ErrorType
{
	public static final ErrorType OK = new ErrorType(0, "Success", true, false);
	public static final ErrorType SYNTAXERROR = new ErrorType(1, "Invalid Syntax", false, true);
	public static final ErrorType INVALIDFLAG = new ErrorType(2, "Invalid Flag", false, false);
	public static final ErrorType INVALIDPLAYER = new ErrorType(3, "Player is not online.", false, false);
	public static final ErrorType INTEGERERROR = new ErrorType(4, "Expected number, received string.", false, false);
	
	private int errorID = 0;
	private String message = "Error";
	private boolean success = false;
	private boolean isSyntaxError = false;
	
	public ErrorType(int errorID, String message, boolean success, boolean isSyntaxError)
	{
		this.errorID = errorID;
		this.message = message;
		this.success = success;
		this.isSyntaxError = isSyntaxError;
	}
	
	public int getErrorID()
	{
		return errorID;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public boolean isSuccessful()
	{
		return success;
	}
	
	public boolean isSyntaxError()
	{
		return isSyntaxError;
	}
}
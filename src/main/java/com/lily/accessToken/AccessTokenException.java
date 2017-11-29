package com.lily.accessToken;

@SuppressWarnings("serial")
public class AccessTokenException extends Exception {

	public static final int OK = 0;
	public static final int SystemBusyError = -1;
	public static final int ValidateAppSecretError = 40001;
	public static final int ValidateGrantTypeError = 40002;
	public static final int ValidateIPError = 40164;
	
	private int code;
	
	private static String getMessage(int code) {
		switch (code) {
		case SystemBusyError:
			return "系统繁忙";
		case ValidateAppSecretError:
			return "AppSecret错误";
		case ValidateGrantTypeError:
			return "请确保grant_type字段值为client_credential";
		case ValidateIPError:
			return "IP不在白名单";
		default:
			return null; 
		}
	}

	public int getCode() {
		return code;
	}

	AccessTokenException(int code) {
		super(getMessage(code));
		this.code = code;
	}
}

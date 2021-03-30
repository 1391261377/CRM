package cn.crm.exception;

/**
 * @program: cn.crm
 * @description
 * @author: aoligei
 * @create: 2021-01-26 18:07
 **/
public class LoginException extends Exception {
    public LoginException() {
    }

    public LoginException(String msg){
        super(msg);
    }

}

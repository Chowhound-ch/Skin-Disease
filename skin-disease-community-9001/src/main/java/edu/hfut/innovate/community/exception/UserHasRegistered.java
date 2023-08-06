package edu.hfut.innovate.community.exception;

/**
 * @author : Chowhound
 * @since : 2023/8/4 - 19:06
 */
public class UserHasRegistered extends RuntimeException{
    public UserHasRegistered(String msg) {
        super(msg);
    }
}

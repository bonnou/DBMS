package net.in.ahr.dbms.others.exceptions;

/**
 * Created by str2653z on 2016/04/09.
 */
public class DbmsSystemException extends RuntimeException {

    public DbmsSystemException(String errCd, String errStep, String message) {
        super(errCd + ":" + errStep + ":" + message);
    }

}

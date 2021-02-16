package io.webapp.common.exception;

/**
 * ADMIN系统内部异常
 *
 * @author ADMIN
 */
public class AdminException extends RuntimeException {

    private static final long serialVersionUID = -994962710559017255L;

    public AdminException(String message) {
        super(message);
    }
}

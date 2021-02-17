package io.webapp.common.exception;

/**
 * 限流异常
 *
 * @author ADMIN
 */
public class LimitAccessException extends AdminException {

    private static final long serialVersionUID = -3608667856397125671L;

    public LimitAccessException(String message) {
        super(message);
    }
}

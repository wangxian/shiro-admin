package io.webapp.common.exception;

/**
 * Redis 连接异常
 *
 * @author ADMIN
 */
public class RedisConnectException extends AdminException {

    private static final long serialVersionUID = 1639374111871115063L;

    public RedisConnectException(String message) {
        super(message);
    }
}

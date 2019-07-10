package io.company.project.common.exception;

/**
 * FEBS系统内部异常
 *
 * @author MrBird
 */
public class FebsException extends Exception {

    private static final long serialVersionUID = -994962710559017255L;

    public FebsException(String message) {
        super(message);
    }
}

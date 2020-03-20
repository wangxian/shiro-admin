package io.webapp.common.entity;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * @author ADMIN
 */
public class AdminResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public AdminResponse code(HttpStatus status) {
        this.put("code", status.value());
        return this;
    }

    public AdminResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public AdminResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    public AdminResponse success() {
        this.code(HttpStatus.OK);
        return this;
    }

    public AdminResponse fail() {
        this.code(HttpStatus.INTERNAL_SERVER_ERROR);
        return this;
    }

    @Override
    public AdminResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

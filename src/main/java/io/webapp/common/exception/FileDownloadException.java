package io.webapp.common.exception;

/**
 * 文件下载异常
 *
 * @author ADMIN
 */
public class FileDownloadException extends AdminException {
    private static final long serialVersionUID = -4353976687870027960L;

    public FileDownloadException(String message) {
        super(message);
    }
}

package ru.javawebinar.topjava.util.exception;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class ErrorInfo {
    public final String url;
    public final String cause;
    public final String[] detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this(url, ex.getClass().getSimpleName(), ex.getLocalizedMessage());
    }

    public ErrorInfo(CharSequence requestURL, String validationException, String... details) {
        this.url = requestURL.toString();
        this.cause = validationException.getClass().getSimpleName();
        this.detail = details;
    }
}

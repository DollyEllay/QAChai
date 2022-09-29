package utils;

public enum StatusCode {
    OK(200),
    CREATED(201),
    NOT_FOUND(404);

    public final int value;

    StatusCode(int value) {
        this.value = value;
    }
}

package utils;

public enum StatusCode {
    OK(200),
    CREATED(201),
    NOT_FOUND(404);

    public final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public static StatusCode getByValue(int value) {
        for (StatusCode code : values()) {
            if (code.value == value) {
                return code;
            }
        }
        throw new UnsupportedOperationException("This status code is not implemented and should not come up " + value);
    }
}

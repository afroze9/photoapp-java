package common;

import java.io.Serial;

public class UsersServiceException extends Exception{
    @Serial
    private static final long serialVersionUID = 7782704409577523097L;

    public UsersServiceException(String message) {
        super(message);
    }
}

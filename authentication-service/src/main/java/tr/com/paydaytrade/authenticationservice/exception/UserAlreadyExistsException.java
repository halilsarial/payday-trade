package tr.com.paydaytrade.authenticationservice.exception;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

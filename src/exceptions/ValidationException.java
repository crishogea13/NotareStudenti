package exceptions;

/**
 * Clasa proprie de exceptii pentru tratarea situațiilor speciale
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructorul fara parametri al clasei
     */
    public ValidationException() {}

    /**
     * Constructorul cu parametri al clasei
     * @param message - String
     *                - mesajul cu erori
     */
    public ValidationException(String message) {
        super(message);
    }
}

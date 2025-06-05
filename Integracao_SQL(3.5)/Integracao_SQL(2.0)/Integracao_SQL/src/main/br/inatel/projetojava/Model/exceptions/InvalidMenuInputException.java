package main.br.inatel.projetojava.Model.exceptions;

public class InvalidMenuInputException extends RuntimeException {
    public InvalidMenuInputException(String message) {
        super(message);
    }
}

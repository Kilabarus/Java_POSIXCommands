package com.company.Matrix.Exceptions;

/**
 * Исключение при выполнении операции с матрицей(ами)
 */
public class MatrixOperationException extends MatrixException {
    private static final String prefix = "Ошибка операции с матрицей(ами): ";

    public MatrixOperationException(String message) {
        super(prefix + message);
    }
}

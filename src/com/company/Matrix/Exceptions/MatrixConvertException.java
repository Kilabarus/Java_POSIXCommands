package com.company.Matrix.Exceptions;

/**
 * Исключение при преобразовании матрицы
 */
public class MatrixConvertException extends MatrixException {
    private static final String prefix = "Ошибка преобразования матрицы: ";

    public MatrixConvertException(String message) {
        super(prefix + message);
    }
}

package com.company.Matrix.Exceptions;

/**
 * Базовый класс исключений при работе с матрицами
 */
public class MatrixException extends Exception {
    public MatrixException(String message) {
        super(message);
    }
}
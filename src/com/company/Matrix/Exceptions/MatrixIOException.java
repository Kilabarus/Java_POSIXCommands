package com.company.Matrix.Exceptions;

/**
 * Исключение при вводе/выводе матрицы
 */
public class MatrixIOException extends MatrixException {
    private static final String prefix = "Ошибка ввода/вывода матрицы: ";
    private String filePath;

    public String getFilePath() {
        return filePath == null || filePath.isBlank()
                ? "Файл не указан"
                : filePath;
    }

    public MatrixIOException(String message) {
        super(prefix + message);
    }

    public MatrixIOException(String message, String filePath) {
        super(prefix + message);
        this.filePath = filePath;
    }
}

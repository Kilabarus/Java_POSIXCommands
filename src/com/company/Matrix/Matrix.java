package com.company.Matrix;

import com.company.Matrix.Exceptions.MatrixConvertException;
import com.company.Matrix.Exceptions.MatrixOperationException;

/**
 * Класс для представления матрицы
 */
public class Matrix {
    double[][] matrix;

    public Matrix(int numOfRows, int numOfColumns) {
        if (numOfRows < 1 || numOfColumns < 1) {
            throw new IllegalArgumentException("Кол-во строк и столбцов инииализируемой матрицы должно быть натуральным числом (больше нуля)");
        }

        matrix = new double[numOfRows][numOfColumns];
    }

    public int getNumOfRows() {
        return matrix.length;
    }

    public int getNumOfColumns() {
        return matrix[0].length;
    }

    /**
     * Получение матрицы известного размера из строки
     *
     * @param strWithMatrix строка с матрицей
     * @param numOfRows     кол-во строк
     * @param numOfColumns  кол-во столбцов
     * @return              матрица размером [numOfRows x numOfColumns]
     * @throws MatrixConvertException
     */
    public static Matrix stringToMatrix(String strWithMatrix, int numOfRows, int numOfColumns) throws MatrixConvertException {
        if (numOfRows < 1 || numOfColumns < 1) {
            throw new IllegalArgumentException("Кол-во строк и столбцов преобразующейся матрицы должно быть натуральным числом (больше нуля)");
        } else if (strWithMatrix == null) {
            throw new NullPointerException("Параметр strWithMatrix был null");
        } else if (strWithMatrix.isBlank()) {
            throw new IllegalArgumentException("Нельзя создать матрицу из пустой строки");
        }

        String[] strArrayWithMatrix = strWithMatrix.split(" ");

        if (strArrayWithMatrix.length != numOfRows * numOfColumns) {
            throw new MatrixConvertException("Число элементов в строке strWithMatrix (" + strArrayWithMatrix.length + ") меньше нужного для матрицы размера [numOfRows x numOfColumns] (" + (numOfRows * numOfColumns) + ")");
        }

        Matrix resMatr = new Matrix(numOfRows, numOfColumns);

        for (int i = 0; i < numOfRows; ++i) {
            for (int j = 0; j < numOfColumns; ++j) {
                resMatr.matrix[i][j] = Double.parseDouble(strArrayWithMatrix[numOfColumns * i + j]);
            }
        }

        return resMatr;
    }

    /**
     * Конвертация матрицы в строку
     *
     * @return  строка с матрицей
     */
    public String matrixToString() {
        StringBuilder sB = new StringBuilder();
        int numOfRows = getNumOfRows(),
                numOfColumns = getNumOfColumns();

        for (int i = 0; i < numOfRows; ++i) {
            for (int j = 0; j < numOfColumns - 1; ++j) {
                sB.append(matrix[i][j]).append(" ");
            }

            sB.append(matrix[i][numOfColumns - 1]).append("\n");
        }

        return sB.toString();
    }

    /**
     * Умножение матрицы на матрицу
     *
     * @param m правая матрица
     * @return  результирующая матрица
     * @throws MatrixOperationException
     */
    public Matrix multByMatrix(Matrix m) throws MatrixOperationException {
        if (getNumOfColumns() != m.getNumOfRows()) {
            throw new MatrixOperationException("Кол-во столбцов левой матрицы и кол-во строк правой матрицы должно быть идентично");
        }

        Matrix resMatrix = new Matrix(getNumOfRows(), m.getNumOfColumns());
        double s = 0;

        for (int i = 0; i < getNumOfRows(); ++i) {
            for (int j = 0; j < m.getNumOfColumns(); ++j, s = 0) {
                for (int k = 0; k < m.getNumOfRows(); ++k) {
                    s += matrix[i][k] * m.matrix[k][j];
                }

                resMatrix.matrix[i][j] = s;
            }
        }

        return resMatrix;
    }
}

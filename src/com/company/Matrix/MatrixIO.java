package com.company.Matrix;

import com.company.Matrix.Exceptions.MatrixConvertException;
import com.company.Matrix.Exceptions.MatrixIOException;

import java.io.*;

public class MatrixIO {
    /**
     * Считывает n матриц из файла filePath
     *
     * @param filePath  файл с матрицами
     * @param n         кол-во матриц для считывания
     * @return          массив размера n со считанными матрицами
     * @throws IOException
     * @throws MatrixIOException
     * @throws MatrixConvertException
     */
    public static Matrix[] deserializeNMatrsFromFile(String filePath, int n) throws IOException, MatrixIOException, MatrixConvertException {
        if (n < 1) {
            throw new MatrixIOException("Кол-во считываемых из файла матриц не может быть меньше 1");
        } else if (filePath == null) {
            throw new NullPointerException("Параметр filePath был null");
        }

        Matrix[] resMatrs = new Matrix[n];

        try (BufferedReader bR = new BufferedReader(new FileReader(filePath))) {
            StringBuilder sB;
            String[] rowsAndColumns;
            String line;
            int i, j, numOfRows, numOfColumns;

            line = bR.readLine();
            for (i = 0; line != null && i < n; ++i) {
                rowsAndColumns = line.split(" ");

                if (rowsAndColumns.length < 2) {
                    throw new MatrixIOException("Размеры (кол-во строк и столбцов) матрицы не были указаны перед ней в файле", filePath);
                }

                // Узнаем размеры матрицы
                numOfRows = Integer.parseInt(rowsAndColumns[0]);
                numOfColumns = Integer.parseInt(rowsAndColumns[1]);

                // Считываем матрицу
                sB = new StringBuilder();
                line = bR.readLine();
                for (j = 0; line != null && j < numOfRows; ++j) {
                    sB.append(line).append(" ");
                    line = bR.readLine();
                }

                if (line == null && j != numOfRows) {
                    throw new MatrixIOException("Кол-во строк, указанное для " + (i + 1) + "-ой матрицы не совпадает с фактическим", filePath);
                }

                // Получаем матрицу из строки
                resMatrs[i] = Matrix.stringToMatrix(sB.toString(), numOfRows, numOfColumns);
            }

            if (line == null && i != n) {
                throw new MatrixIOException("Кол-во матриц (" + (i + 1) + "), записанных в файле, не совпадает с кол-вом матриц для чтения (" + n + ")", filePath);
            }
        }

        return resMatrs;
    }

    /**
     * Записывает матрицу matrix в файл filePath
     *
     * @param matrix    матрица, которую необходимо записать в файл
     * @param filePath  файл, в которую необходимо записать матрицу
     * @throws IOException
     */
    public static void serializeMatrixToFile(Matrix matrix, String filePath) throws IOException {
        if (matrix == null) {
            throw new NullPointerException("Параметр matrix был null");
        } else if (filePath == null || filePath.isBlank()) {
            throw new NullPointerException("Параметр filePath был null или пустым");
        }

        File newFile = new File(filePath);

        FileWriter fileWriter = new FileWriter(newFile, false);
        fileWriter.write(matrix.getNumOfRows() + " " + matrix.getNumOfColumns() + "\n" + matrix.matrixToString());
        fileWriter.close();
    }
}

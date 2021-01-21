package com.company.CommandLine;

import com.company.Matrix.Exceptions.MatrixConvertException;
import com.company.Matrix.Exceptions.MatrixIOException;
import com.company.Matrix.Exceptions.MatrixOperationException;
import com.company.Matrix.MatrixIO;
import com.company.Matrix.Matrix;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class CommandsImplementations {
    /**
     * Выводит названия директорий и файлов в текущей директории,
     *      отсортированных по имени, по возрастанию
     *
     * @param reverseSort   сортировка в обратном порядке
     * @param file          файл текущей дериктории
     *                          null, если рекурсивный вывод не нужен
     *                          java.io.File(System.getProperty("user.dir")), если нужен
     */
    public void ls(boolean reverseSort, File file) {
        File[] filesAndDirsInCurDir = (file == null)
                ? (new File(System.getProperty("user.dir"))).listFiles()
                : file.listFiles();

        if (filesAndDirsInCurDir == null) {
            System.err.println("Не удалось получить информацию о директории");
            return;
        }

        String currentPath = (file == null)
                ? System.getProperty("user.dir")
                : file.getAbsolutePath();

        if (filesAndDirsInCurDir.length == 0) {
            System.out.println("Директория \"" + currentPath + "\" пуста");
        } else {
            // Сортировка полученного массива с файлами и директориями текущей директории, зависящая от reversSort
            Arrays.sort(filesAndDirsInCurDir, (a, b) -> reverseSort
                    ? b.getName().compareToIgnoreCase(a.getName())
                    : a.getName().compareToIgnoreCase(b.getName()));

            System.out.println("Содержимое директории \"" + currentPath + "\":");
            for (File fileOrDir : filesAndDirsInCurDir) {
                System.out.println(fileOrDir.getName());
            }

            if (file != null) {
                // Рекурсивный вывод содержимого всех вложенных директорий
                for (File fileOrDir : filesAndDirsInCurDir) {
                    if (fileOrDir.isDirectory()) {
                        ls(reverseSort, fileOrDir);
                    }
                }
            }
        }
    }

    /**
     * Создает в текущей директории новую директорию с указанным именем
     *  В случае, если директория уже существует,
     *       печатает ошибку в stderr и возвращается с ненулевым статус кодом
     *
     * @param dirName   имя новой директории
     * @return          статус код, который равен
     *                      0, если директория успешно создана
     *                      1, если директория с таким именем уже существует
     *                     -1, если директорию нельзя создать из-за политики безопасности
     */
    public int mkdir(String dirName) {
        try {
            File curDir = new File(System.getProperty("user.dir") + "/" + dirName);

            if (curDir.mkdir()) {
                System.out.println("Директория успешно создана");
                return 0;
            }

            System.err.println("Не удалось создать директорию (директория с таким именем уже существует)");
            return 1;
        } catch (SecurityException e) {
            System.err.println("Директория не может быть создана из-за конфликта с политикой безопасности");
            return -1;
        }
    }

    /**
     * Создает в текущей директории текстовый файл с указанным именем и содержимым
     * В случае, если файл уже существует,
     *      печатает ошибку в stderr и возвращается с ненулевым статус кодом
     *
     * @param fileContent   содержимое файла (текст)
     * @param fileName      имя файла
     * @return              статус код, который равен
     *                      0, если файл успешно создан
     *                     -1, если файл нельзя создать из-за политики безопасности
     *                     -2, если произошла ошибка воода/вывода
     */
    public int echo(String fileContent, String fileName) {
        try {
            File newFile = new File(System.getProperty("user.dir") + "/" + fileName);

            if (!newFile.createNewFile()) {
                System.err.println("Не удалось создать файл (файл с таким именем уже существует)");
                return 1;
            } else {
                OutputStreamWriter oSW = new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8);
                oSW.write(fileContent);
                oSW.close();

                System.out.println("Файл успешно создан");
                return 0;
            }
        } catch (SecurityException e) {
            System.err.println("Файл не может быть создан из-за конфликта с политикой безопасности");
            return -1;
        } catch (IOException e) {
            System.err.println("Произошла ошибка ввода/вывода");
            return -2;
        }
    }

    /**
     * Выводит содержимое указанного текстового файла (или нескольких файлов)
     * В случае вывода нескольких файлов их содержимое показывается последовательно
     * Если один или несколько файлов не существуют, выводит в stderr сообщение об этом файле
     *
     * @param putDollarsAtTheEnd    показ символа $ в конце каждой строки
     * @param numEveryLine          нумерация строк
     * @param fileNames             имена файлов
     */
    public void cat(boolean putDollarsAtTheEnd, boolean numEveryLine, String[] fileNames) {
        Path filePath;
        String fileContent;
        BufferedReader bR;
        String line;

        int i;
        String lineEnd = putDollarsAtTheEnd
                ? "$"
                : "";

        for (String fileName : fileNames) {
            try {
                filePath = Paths.get(System.getProperty("user.dir") + "/" + fileName);

                if (!Files.isRegularFile(filePath)) {
                    System.err.println("Файла с именем \"" + fileName + "\" не существует/он не доступен для чтения");
                } else {
                    if (!putDollarsAtTheEnd && !numEveryLine) {
                        // Если нам не нужно выводить доллары или нумеровать строки,
                        //      то мы можем сразу целиком считывать содержимое файлов и сразу же выводить его на экран
                        fileContent = Files.readString(filePath);

                        if (fileContent.length() == 0) {
                            System.out.println("Файл \"" + fileName + "\" пуст");
                        } else {
                            System.out.println("Содержимое \"" + fileName + "\":\n" + fileContent);
                        }
                    } else {
                        // Иначе нам нужно считывать содержимое файла построчно,
                        //      добавляя при необходимости номера строк и доллары
                        bR = new BufferedReader(new FileReader(fileName));

                        try {
                            line = bR.readLine();

                            if (line == null) {
                                System.out.println("Файл \"" + fileName + "\" пуст");
                            } else {
                                System.out.println("Содержимое \"" + fileName + "\":");
                                i = 1;

                                do {
                                    if (numEveryLine) {
                                        System.out.print(i++ + " ");
                                    }

                                    System.out.println(line + lineEnd);
                                    line = bR.readLine();
                                } while (line != null);
                            }
                        } finally {
                            bR.close();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Произошла ошибка ввода/вывода файла \"" + fileName + "\"");
            }
        }
    }

    public void matrmult(String inputFileName, String outputFileName, int numOfMatrs) {
        try {
            Matrix[] matrs = MatrixIO.deserializeNMatrsFromFile(inputFileName, numOfMatrs);
            Matrix resMatr = matrs[0];

            for (int i = 1; i < numOfMatrs; ++i) {
                resMatr = resMatr.multByMatrix(matrs[i]);
            }

            MatrixIO.serializeMatrixToFile(resMatr, outputFileName);
            System.out.println("Результирующая матрица была записана в файл \"" + outputFileName + "\"");
        } catch (MatrixIOException | MatrixConvertException | MatrixOperationException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Произошла ошибка ввода/вывода файла");
        }
    }
}

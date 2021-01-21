package com.company.CommandLine.ConcreteCommands;

import com.company.CommandLine.CommandsImplementations;
import com.company.CommandLine.ICommand;

import java.util.Arrays;

/**
 * Выводит содержимое указанного текстового файла (или нескольких файлов)
 * В случае вывода нескольких файлов их содержимое показывается последовательно
 * Если один или несколько файлов не существуют, выводит в stderr сообщение об этом файле
 */
public class catCommand implements ICommand {
    CommandsImplementations cmdsImpls;
    // Имена файлов
    String[] fileNames = null;
            // Показ символа $ в конце каждой строки
    boolean putDollarsAtTheEnd = false,
            // Нумерация строк
            numEveryLine = false;

    public catCommand(CommandsImplementations comdsImpls) {
        this.cmdsImpls = comdsImpls;
    }

    /**
     * Получение названия конкретной команды
     *
     * @return название конкретной команды
     */
    @Override
    public String getCmdName() {
        return "cat";
    }

    /**
     * Установка дополнительных опций конкретной команде, исходя из ключей в options
     *
     * @param options   опциональные ключи
     */
    @Override
    public void setOptions(String[] options) {
        int firstFileNameIndex = 0;
        if (options.length > 0) {
            switch (options[0]) {
                case "-En", "-nE" -> {
                    putDollarsAtTheEnd = numEveryLine = true;
                    firstFileNameIndex = 1;
                }
                case "-E" -> {
                    putDollarsAtTheEnd = true;
                    firstFileNameIndex = 1;
                    if (options.length > 1 && options[1].equals("-n")) {
                        numEveryLine = true;
                        firstFileNameIndex = 2;
                    }
                }
                case "-n" -> {
                    numEveryLine = true;
                    firstFileNameIndex = 1;
                    if (options.length > 1 && options[1].equals("-E")) {
                        putDollarsAtTheEnd = true;
                        firstFileNameIndex = 2;
                    }
                }
            }
        }

        fileNames = Arrays.copyOfRange(options, firstFileNameIndex, options.length);
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        if (fileNames == null || fileNames.length == 0) {
            System.err.println("Не заданы файлы для чтения");
            return;
        }

        cmdsImpls.cat(putDollarsAtTheEnd, numEveryLine, fileNames);
    }
}

package com.company.CommandLine.ConcreteCommands;

import com.company.CommandLine.CommandsImplementations;
import com.company.CommandLine.ICommand;

/**
 * Выводит названия директорий и файлов в текущей директории,
 *      отсортированных по имени, по возрастанию
 */
public class lsCommand implements ICommand {
    CommandsImplementations cmdsImpls;
            // Сортировка в обратном порядке
    boolean reverseSort = false,
            // Рекурсивный вывод содержимого всех вложенных директорий
            showChildDirs = false;

    public lsCommand(CommandsImplementations cmdsImpls) {
        this.cmdsImpls = cmdsImpls;
    }

    /**
     * Получение названия конкретной команды
     *
     * @return название конкретной команды
     */
    @Override
    public String getCmdName() {
        return "ls";
    }

    /**
     * Установка дополнительных опций конкретной команде, исходя из ключей в options
     *
     * @param options   опциональные ключи
     */
    @Override
    public void setOptions(String[] options) {
        for (String option : options) {
            switch (option) {
                case "-r" -> reverseSort = true;
                case "-R" -> showChildDirs = true;
                case "-Rr", "-rR" -> reverseSort = showChildDirs = true;
            }
        }
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        if (showChildDirs) {
            cmdsImpls.ls(reverseSort, new java.io.File(System.getProperty("user.dir")));
        } else {
            cmdsImpls.ls(reverseSort, null);
        }
    }
}

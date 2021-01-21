package com.company.CommandLine.ConcreteCommands;

import com.company.CommandLine.CommandsImplementations;
import com.company.CommandLine.ICommand;

/**
 * Создает в текущей директории новую директорию с указанным именем
 * В случае, если директория уже существует,
 *      печатает ошибку в stderr и возвращается с ненулевым статус кодом
 */
public class mkdirCommand implements ICommand {
    CommandsImplementations cmdsImpls;
    // Имя новой директории
    String dirName = null;

    public mkdirCommand(CommandsImplementations cmdsImpls) {
        this.cmdsImpls = cmdsImpls;
    }

    /**
     * Получение названия конкретной команды
     *
     * @return название конкретной команды
     */
    @Override
    public String getCmdName() {
        return "mkdir";
    }

    /**
     * Установка дополнительных опций конкретной команде, исходя из ключей в options
     *
     * @param options   опциональные ключи
     */
    @Override
    public void setOptions(String[] options) {
        dirName = options[0];
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        if (dirName == null || dirName.isBlank()) {
            System.err.println("Не введено имя директории/имя директории пустое");
            return;
        }

        cmdsImpls.mkdir(dirName);
    }
}

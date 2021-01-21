package com.company.CommandLine.ConcreteCommands;

import com.company.CommandLine.CommandsImplementations;
import com.company.CommandLine.ICommand;

/**
 * Создает в текущей директории текстовый файл с указанным именем и содержимым
 * В случае, если файл уже существует,
 *      печатает ошибку в stderr и возвращается с ненулевым статус кодом
 */
public class echoCommand implements ICommand {
    CommandsImplementations cmdsImpls;
           // Содержимое файла (текст)
    String fileContent = null,
           // Имя файла
           fileName = null;

    public echoCommand(CommandsImplementations comdsImpls) {
        this.cmdsImpls = comdsImpls;
    }

    /**
     * Получение названия конкретной команды
     *
     * @return название конкретной команды
     */
    @Override
    public String getCmdName() {
        return "echo";
    }

    /**
     * Установка дополнительных опций конкретной команде, исходя из ключей в options
     *
     * @param options   опциональные ключи
     */
    @Override
    public void setOptions(String[] options) {
        if (options.length > 1) {
            fileContent = options[0];
            fileName = options[1];
        }
    }

    /**
     * Выполнение команды
     */
    @Override
    public void execute() {
        if (fileContent == null || fileName == null || fileName.isBlank()) {
            System.err.println("Не задано содержимое файла/название файла/название файла пустое");
            return;
        }

        cmdsImpls.echo(fileContent, fileName);
    }
}

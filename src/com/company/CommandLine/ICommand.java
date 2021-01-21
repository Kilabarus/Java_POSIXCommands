package com.company.CommandLine;

/**
 * Общий интерфейс для всех конкретных команд
 */
public interface ICommand {
    /**
     * Получение названия конкретной команды
     *
     * @return название конкретной команды
     */
    String getCmdName();

    /**
     * Установка дополнительных опций конкретной команде, исходя из ключей в options
     *
     * @param options   опциональные ключи
     */
    void setOptions(String[] options);

    /**
     * Выполнение команды
     */
    void execute();
}

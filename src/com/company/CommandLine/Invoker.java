package com.company.CommandLine;

/**
 * Класс "Отправителя", хранит ссылку на конкретную команду и вызывает её, когда необходимо
 */
public class Invoker {
    /**
     * Конкретная команда
     */
    ICommand cmd;

    /**
     * Устанавливает ссылку на конкретную команду
     *
     * @param cmd   конкретная команда
     */
    public void setCommand(ICommand cmd) {
        this.cmd = cmd;
    }

    /**
     * Вызывает установленную конкретную команду через метод, описанный в интерфейсе конкретной команды
     */
    public void invoke() {
        if (cmd != null) {
            cmd.execute();
        }
    }
}

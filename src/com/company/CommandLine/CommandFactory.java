package com.company.CommandLine;

import com.company.CommandLine.ConcreteCommands.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс "Фабрики команд", определяет необходимую конкретную команду и передаёт ссылку на неё "Отправителю"
 */
public class CommandFactory {
    /**
     * Список всех доступных конкретных команд
     *
     * С помощью него и метода getCmdName() в интерфейсе ICommand мы избавляемся от else-if'ов
     *      и можем искать нужную нам конкретную команду в цикле,
     *          а добавление новой команды будет состоять лишь в добавлении её в этот список
     */
    List<ICommand> cmds;

    /**
     * Реализация конкретных команд, нужен для инициализации
     */
    CommandsImplementations cmdsImpls;

    /**
     * Отправитель команд, ему мы передаем ссылку на конкретную команду
     */
    Invoker invoker;

    public CommandFactory(Invoker invoker) {
        cmdsImpls = new CommandsImplementations();
        this.invoker = invoker;

        cmds = new ArrayList<>();
        cmds.add(new lsCommand(cmdsImpls));
        cmds.add(new mkdirCommand(cmdsImpls));
        cmds.add(new echoCommand(cmdsImpls));
        cmds.add(new catCommand(cmdsImpls));
        cmds.add(new matrmultCommand(cmdsImpls));
    }

    /**
     * Устанавливает "Отправителю" конкретную команду
     *
     * @param args  массив с составляющими команды,
     *                  где элемент с 0-ым индеком - название команды,
     *                      элементы с 1-ым..n-ым индексом - атрибуты или опциональные ключи команды
     * @return  статус код, который равен
     *               0, если "Отправителю" успешно передана ссылка на конкретную команду
     *              -1, если была введена пустая команда
     *              -2, если не удалось распознать команду
     */
    public int setCmdForInvokerFromArgs(String[] args) {
        if (args.length == 0) {
            System.err.println("Введена пустая команда");
            return -1;
        }

        // Поиск нужной команды
        for (ICommand cmd : cmds) {
            if (cmd.getCmdName().equals(args[0])) {
                cmd.setOptions(Arrays.copyOfRange(args, 1, args.length));
                invoker.setCommand(cmd);

                return 0;
            }
        }

        System.err.println("Введена неизвестная команда");
        return -2;
    }
}

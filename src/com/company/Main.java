package com.company;

import com.company.CommandLine.CommandFactory;
import com.company.CommandLine.Invoker;

/**
 * Задача 3*. Утилита командной строки для работы с файлами
 *
 * Требуется написать утилиту для работы с файлами в текущей директории,
 * 		реализующую функционал, схожий с утилитами POSIX.
 * Желательно спроектировать иерархию классов в программе с прицелом на расширяемость, т.е. так,
 * 		чтобы в дальнейшем можно было добавлять новые команды или изменять существующие практически без изменений в других классах.
 * Для работы с текстовыми файлами требуется использовать кодировку UTF-8.
 *
 * Задача 4. Матричный калькулятор
 * Вариант 1. Умножение двух матриц
 *
 * Требуется написать калькулятор, осуществляющий операции над матрицами
 *
 * Данильченко Роман, 9 гр.
 */

public class Main {
    public static void main(String[] args) {
    	//args = new String[] {"ls", "-r"};
		Invoker invoker = new Invoker();
		CommandFactory cmdFactory = new CommandFactory(invoker);

		if (cmdFactory.setCmdForInvokerFromArgs(args) == 0) {
			invoker.invoke();
		}
    }
}

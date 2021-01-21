package com.company.CommandLine.ConcreteCommands;

import com.company.CommandLine.CommandsImplementations;
import com.company.CommandLine.ICommand;

public class matrmultCommand implements ICommand {
    CommandsImplementations cmdsImpls;
    String inputFileName, outputFileName;
    int numOfMatrs = 2;

    public matrmultCommand(CommandsImplementations comdsImpls) {
        this.cmdsImpls = comdsImpls;
    }

    @Override
    public String getCmdName() {
        return "matrmult";
    }

    @Override
    public void setOptions(String[] options) {
        if (options.length > 1) {
            inputFileName = options[0];
            outputFileName = options[1];
        }

        if (options.length > 2) {
            numOfMatrs = Integer.parseInt(options[2]);
            numOfMatrs = numOfMatrs < 1
                    ? 2
                    : numOfMatrs;
        }
    }

    @Override
    public void execute() {
        if (inputFileName == null || inputFileName.isBlank() || outputFileName == null || outputFileName.isBlank()) {
            System.err.println("Не заданы файлы для ввода матриц и вывода результирующей/названия файлов пусты");
            return;
        }

        cmdsImpls.matrmult(inputFileName, outputFileName, numOfMatrs);
    }
}

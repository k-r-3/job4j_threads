package ru.job4j.download;

import java.io.IOException;
import java.util.Arrays;

public class Runner {
    private Input input;
    private Validator validator;
    private String[] args;

    public Runner(Validator validator, Input in, String[] args) {
        this.validator = validator;
        this.input = in;
        this.args = args;
        check(args);
    }

    private void check(String[] args) {
        while (!validator.check(args)) {
            System.out.printf("wrong address or speed : %s \r\n input correct arguments\n",
                    Arrays.toString(args));
            try {
                args = input.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner(new ConsoleValidator(), new ConsoleInput(), args);
        String[] checkedArgs = runner.getArgs();
        String url = checkedArgs[0];
        int speed = Integer.parseInt(checkedArgs[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}

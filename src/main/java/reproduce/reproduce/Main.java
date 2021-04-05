package reproduce.reproduce;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            System.in.read();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.out.println("Main - Started");
        Run.run();
    }
}

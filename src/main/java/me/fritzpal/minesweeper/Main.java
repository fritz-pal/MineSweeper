package me.fritzpal.minesweeper;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String[] options = {"Easy", "Medium", "Hard", "Impossible", "Custom"};
        int option = JOptionPane.showOptionDialog(null, "Choose a difficulty", "Minesweeper", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (option) {
            case 0 -> new Window(15, 20);
            case 1 -> new Window(20, 50);
            case 2 -> new Window(25, 100);
            case 3 -> new Window(30, 180);
            case 4 -> {
                JTextField textField1 = new JTextField();
                JTextField textField2 = new JTextField();
                Object[] message = {"Size:", textField1, "Mines:", textField2};
                if (JOptionPane.showOptionDialog(null, message, "Minesweeper", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"OK"}, "ok") != 0)
                    return;
                String text1 = textField1.getText();
                String text2 = textField2.getText();
                try {
                    int number1 = Integer.parseInt(text1);
                    int number2 = Integer.parseInt(text2);
                    new Window(number1, number2);
                } catch (NumberFormatException ignored) {
                    main(args);
                }
            }
        }
    }
}
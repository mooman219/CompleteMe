package com.gmail.mooman219;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class CompleteMe {

    public static void main(String[] args) throws AWTException, IOException {
        long time = System.currentTimeMillis();
        Dictionary dictionary = new Dictionary("large.txt");
        time = System.currentTimeMillis() - time;
        System.out.println("Load time: " + time);

        Keyboard keyboard = new Keyboard();
        Controller controller = new Controller(dictionary, keyboard);
        Input.register(controller);

        controller.run();
    }

    public static void display(List<String> words, JList<String> list, int number) {
        DefaultListModel<String> listModel = new DefaultListModel<String>();
        for (int i = 0; i < number && i < words.size(); i++) {
            listModel.addElement(words.get(i));
        }
        list.setModel(listModel);
    }
}

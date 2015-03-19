package com.gmail.mooman219;

import java.awt.AWTException;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class CompleteMe {

    public static final LinkedBlockingQueue<Character> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws AWTException, IOException {
        Keyboard keyboard = new Keyboard();
        Input.register();

        long time = System.currentTimeMillis();
        Dictionary dict = new Dictionary("large.txt");
        time = System.currentTimeMillis() - time;
        System.out.println("Load time: " + time);

        JFrame frame = new JFrame("CompleteMe test");
        JList<String> list = new JList<>();
        frame.getContentPane().add(list);
        frame.setAlwaysOnTop(true);
        frame.setMinimumSize(new Dimension(250, 300));
        frame.pack();
        frame.setVisible(true);

        StringBuilder word = new StringBuilder();
        Character c;
        while (true) {
            c = null;
            try {
                c = queue.take();
                word.append(c);
            } catch (InterruptedException ex) {
                continue;
            }
            if (c != null) {
                if (c == ' ') {
                    word.delete(0, word.length());
                    list.setModel(new DefaultListModel());
                } else {
                    System.out.println("Search:" + word.toString());
                    time = System.currentTimeMillis();
                    display(dict.getList(word.toString()), list, 10);
                    time = System.currentTimeMillis() - time;
                    System.out.println("Lookup time: " + time);
                }
            }
        }
    }

    public static void display(List<String> words, JList<String> list, int number) {
        DefaultListModel listModel = new DefaultListModel();
        for (int i = 0; i < number && i < words.size(); i++) {
            listModel.addElement(words.get(i));
        }
        list.setModel(listModel);
    }
}
// atck hello worl ataack

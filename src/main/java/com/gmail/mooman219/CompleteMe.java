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

    public static final LinkedBlockingQueue<Operation> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) throws AWTException, IOException {
        Keyboard keyboard = new Keyboard();
        Input.register();

        long time = System.currentTimeMillis();
        Dictionary dict = new Dictionary("small.txt");
        time = System.currentTimeMillis() - time;
        System.out.println("Load time: " + time);

        JFrame frame = new JFrame("CompleteMe test");
        JList<String> list = new JList<>();
        frame.getContentPane().add(list);
        frame.setAlwaysOnTop(true);
        frame.setMinimumSize(new Dimension(250, 300));
        frame.pack();
        frame.setVisible(true);

        WordState state = new WordState();
        while (true) {
            try {
                Operation operation = queue.take();
                state.consume(operation);
                switch (operation.getType()) {
                    case ACCEPT:
                        keyboard.select(state.getPosition(), state.getLength());
                        keyboard.type(dict.getTopWord());
                        break;
                    case RESET:
                        list.setModel(new DefaultListModel());
                        break;
                    case ADD:
                    case REMOVE:
                        if (state.hasWord()) {
                            time = System.currentTimeMillis();
                            display(dict.getList(state.getWord()), list, 10);
                            time = System.currentTimeMillis() - time;
                            System.out.println("Searched '" + state.getWord() + "' in " + time + "ms");
                        } else {
                            list.setModel(new DefaultListModel());
                        }
                        break;
                }
            } catch (InterruptedException ex) {
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

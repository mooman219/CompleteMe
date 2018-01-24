package com.gmail.mooman219;

import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class Controller {

    private final LinkedBlockingQueue<Operation> queue = new LinkedBlockingQueue<>();
    private final WordState state = new WordState();
    private final Dictionary dictionary;
    private final Keyboard keyboard;
    private volatile boolean writing = false;

    public Controller(Dictionary dictionary, Keyboard keyboard) {
        this.dictionary = dictionary;
        this.keyboard = keyboard;
    }

    public void consume(NativeKeyEvent e, boolean isPress) {
        if (writing) {
            return;
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_SPACE && (e.getModifiers() & NativeInputEvent.CTRL_MASK) != 0) {
            NativeInputHelper.consumeNativeInputEvent(e);
        }
        if (isPress) {
            Operation operation = null;
            switch (e.getKeyCode()) {
                case NativeKeyEvent.VC_LEFT:
                    operation = new Operation(Operation.Type.MOVE_LEFT, '\0');
                    break;
                case NativeKeyEvent.VC_RIGHT:
                    operation = new Operation(Operation.Type.MOVE_RIGHT, '\0');
                    break;
                case NativeKeyEvent.VC_BACKSPACE:
                    operation = new Operation(Operation.Type.REMOVE, '\0');
                    break;
                case NativeKeyEvent.VC_SPACE:
                    if ((e.getModifiers() & NativeInputEvent.CTRL_MASK) != 0) {
                        operation = new Operation(Operation.Type.ACCEPT, '\0');
                        break;
                    }
                case NativeKeyEvent.VC_ENTER:
                    operation = new Operation(Operation.Type.RESET, '\0');
                    break;
                default:
                    char result = NativeInputHelper.nativeKeyToChar(e.getKeyCode(), e.getModifiers());
                    if (result != '\0') {
                        operation = new Operation(Operation.Type.ADD, result);
                    }
                    break;
            }
            if (operation != null) {
                try {
                    queue.put(operation);
                } catch (InterruptedException ex) {
                    // Eat
                }
            }
        }
    }

    public void run() {
        JFrame frame = new JFrame("CompleteMe test");
        JList<String> list = new JList<>();
        frame.getContentPane().add(list);
        frame.setAlwaysOnTop(true);
        frame.setMinimumSize(new Dimension(250, 300));
        frame.pack();
        frame.setVisible(true);

        while (true) {
            try {
                Operation operation = queue.take();
                state.consume(operation);
                switch (operation.getType()) {
                    case ACCEPT:
                        writing = true;
                        keyboard.select(state.getPosition(), state.getLength());
                        keyboard.type(dictionary.getTopWord());
                        writing = false;
                        break;
                    case RESET:
                        list.setModel(new DefaultListModel());
                        break;
                    case ADD:
                    case REMOVE:
                        if (state.hasWord()) {
                            long time = System.currentTimeMillis();
                            display(dictionary.getList(state.getWord()), list, 10);
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

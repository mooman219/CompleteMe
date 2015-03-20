package com.gmail.mooman219;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class WordState {

    private int position = 0;
    private final StringBuilder line = new StringBuilder();

    public void consume(Operation operation) {
        switch (operation.getType()) {
            case MOVE_LEFT:
                if (line.length() > 0) {
                    position--;
                }
                break;
            case MOVE_RIGHT:
                if (position <= line.length()) {
                    position++;
                }
                break;
            case RESET:
                line.delete(0, line.length());
                position = 0;
                break;
            case ADD:
                if (position < line.length()) {
                    line.insert(position, operation.getCharacter());
                } else {
                    line.append(operation.getCharacter());
                }
                position++;
                break;
            case REMOVE:
                if (line.length() > 0) {
                    line.deleteCharAt(line.length() - 1);
                    position--;
                }
                break;
        }
    }

    public boolean hasWord() {
        return line.length() > 0;
    }

    public String getWord() {
        return line.toString();
    }
}

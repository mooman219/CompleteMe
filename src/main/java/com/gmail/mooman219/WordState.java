package com.gmail.mooman219;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class WordState {

    private final StringBuilder line = new StringBuilder();

    public void consume(Operation operation) {
        switch (operation.getType()) {
            case RESET:
                line.delete(0, line.length());
                break;
            case ADD:
                line.append(operation.getCharacter());
                break;
            case REMOVE:
                if (line.length() > 0) {
                    line.deleteCharAt(line.length() - 1);
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

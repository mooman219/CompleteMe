package com.gmail.mooman219;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class WordState {

    private boolean accepted = false;
    private int position = 0;
    private final StringBuilder line = new StringBuilder();

    public void consume(Operation operation) {
        if (accepted) {
            reset();
            accepted = false;
        }
        switch (operation.getType()) {
            case MOVE_LEFT:
                if (position > 0) {
                    position--;
                } else {
                    reset();
                }
                break;
            case MOVE_RIGHT:
                if (position < line.length()) {
                    position++;
                } else {
                    reset();
                }
                break;
            case ACCEPT:
                accepted = true;
                break;
            case RESET:
                reset();
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
        System.out.println(operation.getType() + " -> Pos:" + position + ", Len:" + line.length() + ", Word:'" + line.toString() + "'");
    }

    private void reset() {
        line.delete(0, line.length());
        position = 0;
    }

    public int getPosition() {
        return position;
    }

    public int getLength() {
        return line.length();
    }

    public boolean hasWord() {
        return line.length() > 0;
    }

    public String getWord() {
        return line.toString();
    }
}

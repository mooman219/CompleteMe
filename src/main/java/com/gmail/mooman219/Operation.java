package com.gmail.mooman219;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class Operation {

    public static enum Type {

        MOVE_LEFT,
        MOVE_RIGHT,
        RESET,
        ACCEPT,
        ADD,
        REMOVE;
    }

    private final Type type;
    private final char character;

    public Operation(Type type, char character) {
        this.type = type;
        this.character = character;
    }

    public Type getType() {
        return type;
    }

    public char getCharacter() {
        return character;
    }
}

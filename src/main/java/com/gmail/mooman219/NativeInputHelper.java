package com.gmail.mooman219;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class NativeInputHelper {

    private static final Field NativeInputField_reserved;

    static {
        Field result;
        try {
            result = NativeInputEvent.class.getDeclaredField("reserved");
            result.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException ex) {
            // Eat the error. Consuming the event isn't supported on all systems.
            result = null;
        }
        NativeInputField_reserved = result;
    }

    /**
     * Prevents the given event from propagating to the rest of the system. Not
     * all systems support events being consumed.
     *
     * @param event The event to consume
     * @return true if the event was consumed without error, false otherwise
     */
    public static boolean consumeNativeInputEvent(NativeInputEvent event) {
        if (NativeInputField_reserved != null) {
            try {
                NativeInputField_reserved.setShort(event, (short) 0x01);
                return true;
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                // Eat the error. Consuming the event isn't supported on all systems.
            }
        }
        return false;
    }

    public static int nativeModifierToJava(int value) {
        int mask = 0;
        if ((value & NativeInputEvent.SHIFT_MASK) != 0) {
            mask |= KeyEvent.SHIFT_MASK;
        }
        if ((value & NativeInputEvent.CTRL_MASK) != 0) {
            mask |= KeyEvent.CTRL_MASK;
        }
        if ((value & NativeInputEvent.ALT_MASK) != 0) {
            mask |= KeyEvent.ALT_MASK;
        }
        if ((value & NativeInputEvent.META_MASK) != 0) {
            mask |= KeyEvent.META_MASK;
        }
        return mask;
    }

    public static int nativeKeyToJava(int value) {
        switch (value) {
            /**
             * Constants for the F1 through F24 function keys.
             */
            case NativeKeyEvent.VC_F1:
                return KeyEvent.VK_F1;
            case NativeKeyEvent.VC_F2:
                return KeyEvent.VK_F2;
            case NativeKeyEvent.VC_F3:
                return KeyEvent.VK_F3;
            case NativeKeyEvent.VC_F4:
                return KeyEvent.VK_F4;
            case NativeKeyEvent.VC_F5:
                return KeyEvent.VK_F5;
            case NativeKeyEvent.VC_F6:
                return KeyEvent.VK_F6;
            case NativeKeyEvent.VC_F7:
                return KeyEvent.VK_F7;
            case NativeKeyEvent.VC_F8:
                return KeyEvent.VK_F8;
            case NativeKeyEvent.VC_F9:
                return KeyEvent.VK_F9;
            case NativeKeyEvent.VC_F10:
                return KeyEvent.VK_F10;
            case NativeKeyEvent.VC_F11:
                return KeyEvent.VK_F11;
            case NativeKeyEvent.VC_F12:
                return KeyEvent.VK_F12;
            case NativeKeyEvent.VC_F13:
                return KeyEvent.VK_F13;
            case NativeKeyEvent.VC_F14:
                return KeyEvent.VK_F14;
            case NativeKeyEvent.VC_F15:
                return KeyEvent.VK_F15;
            case NativeKeyEvent.VC_F16:
                return KeyEvent.VK_F16;
            case NativeKeyEvent.VC_F17:
                return KeyEvent.VK_F17;
            case NativeKeyEvent.VC_F18:
                return KeyEvent.VK_F18;
            case NativeKeyEvent.VC_F19:
                return KeyEvent.VK_F19;
            case NativeKeyEvent.VC_F20:
                return KeyEvent.VK_F20;
            case NativeKeyEvent.VC_F21:
                return KeyEvent.VK_F21;
            case NativeKeyEvent.VC_F22:
                return KeyEvent.VK_F22;
            case NativeKeyEvent.VC_F23:
                return KeyEvent.VK_F23;
            case NativeKeyEvent.VC_F24:
                return KeyEvent.VK_F24;
            /**
             * VC_0 thru VC_9
             */
            case NativeKeyEvent.VC_1:
                return KeyEvent.VK_1;
            case NativeKeyEvent.VC_2:
                return KeyEvent.VK_2;
            case NativeKeyEvent.VC_3:
                return KeyEvent.VK_3;
            case NativeKeyEvent.VC_4:
                return KeyEvent.VK_4;
            case NativeKeyEvent.VC_5:
                return KeyEvent.VK_5;
            case NativeKeyEvent.VC_6:
                return KeyEvent.VK_6;
            case NativeKeyEvent.VC_7:
                return KeyEvent.VK_7;
            case NativeKeyEvent.VC_8:
                return KeyEvent.VK_8;
            case NativeKeyEvent.VC_9:
                return KeyEvent.VK_9;
            case NativeKeyEvent.VC_0:
                return KeyEvent.VK_0;

            case NativeKeyEvent.VC_MINUS: // '-':
                return KeyEvent.VK_MINUS;
            case NativeKeyEvent.VC_EQUALS: // '=':
                return KeyEvent.VK_EQUALS;
            /**
             * VC_A thru VC_Z
             */
            case NativeKeyEvent.VC_A:
                return KeyEvent.VK_A;
            case NativeKeyEvent.VC_B:
                return KeyEvent.VK_B;
            case NativeKeyEvent.VC_C:
                return KeyEvent.VK_C;
            case NativeKeyEvent.VC_D:
                return KeyEvent.VK_D;
            case NativeKeyEvent.VC_E:
                return KeyEvent.VK_E;
            case NativeKeyEvent.VC_F:
                return KeyEvent.VK_F;
            case NativeKeyEvent.VC_G:
                return KeyEvent.VK_G;
            case NativeKeyEvent.VC_H:
                return KeyEvent.VK_H;
            case NativeKeyEvent.VC_I:
                return KeyEvent.VK_I;
            case NativeKeyEvent.VC_J:
                return KeyEvent.VK_J;
            case NativeKeyEvent.VC_K:
                return KeyEvent.VK_K;
            case NativeKeyEvent.VC_L:
                return KeyEvent.VK_L;
            case NativeKeyEvent.VC_M:
                return KeyEvent.VK_M;
            case NativeKeyEvent.VC_N:
                return KeyEvent.VK_N;
            case NativeKeyEvent.VC_O:
                return KeyEvent.VK_O;
            case NativeKeyEvent.VC_P:
                return KeyEvent.VK_P;
            case NativeKeyEvent.VC_Q:
                return KeyEvent.VK_Q;
            case NativeKeyEvent.VC_R:
                return KeyEvent.VK_R;
            case NativeKeyEvent.VC_S:
                return KeyEvent.VK_S;
            case NativeKeyEvent.VC_T:
                return KeyEvent.VK_T;
            case NativeKeyEvent.VC_U:
                return KeyEvent.VK_U;
            case NativeKeyEvent.VC_V:
                return KeyEvent.VK_V;
            case NativeKeyEvent.VC_W:
                return KeyEvent.VK_W;
            case NativeKeyEvent.VC_X:
                return KeyEvent.VK_X;
            case NativeKeyEvent.VC_Y:
                return KeyEvent.VK_Y;
            case NativeKeyEvent.VC_Z:
                return KeyEvent.VK_Z;
            case NativeKeyEvent.VC_OPEN_BRACKET: // '['
                return KeyEvent.VK_OPEN_BRACKET;
            case NativeKeyEvent.VC_CLOSE_BRACKET: // ']'
                return KeyEvent.VK_CLOSE_BRACKET;
            case NativeKeyEvent.VC_BACK_SLASH: // '\'
                return KeyEvent.VK_BACK_SLASH;
            case NativeKeyEvent.VC_SEMICOLON: // ';'
                return KeyEvent.VK_SEMICOLON;
            case NativeKeyEvent.VC_QUOTE: // '''
                return KeyEvent.VK_QUOTE;
            case NativeKeyEvent.VC_COMMA: // ','
                return KeyEvent.VK_COMMA;
            case NativeKeyEvent.VC_PERIOD: // '.'
                return KeyEvent.VK_PERIOD;
            case NativeKeyEvent.VC_SLASH: // '/'
                return KeyEvent.VK_SLASH;
            /**
             * Begin Numeric Zone
             */
            case NativeKeyEvent.VC_NUM_LOCK:
                return KeyEvent.VK_NUM_LOCK;
            case NativeKeyEvent.VC_KP_DIVIDE:
                return KeyEvent.VK_DIVIDE;
            case NativeKeyEvent.VC_KP_MULTIPLY:
                return KeyEvent.VK_MULTIPLY;
            case NativeKeyEvent.VC_KP_SUBTRACT:
                return KeyEvent.VK_SUBTRACT;
            case NativeKeyEvent.VC_KP_EQUALS:
                return KeyEvent.VK_EQUALS;
            case NativeKeyEvent.VC_KP_ADD:
                return KeyEvent.VK_ADD;
            case NativeKeyEvent.VC_KP_ENTER:
                return KeyEvent.VK_ENTER;
            case NativeKeyEvent.VC_KP_SEPARATOR:
                return KeyEvent.VK_SEPARATOR;
            case NativeKeyEvent.VC_KP_1:
                return KeyEvent.VK_NUMPAD1;
            case NativeKeyEvent.VC_KP_2:
                return KeyEvent.VK_NUMPAD2;
            case NativeKeyEvent.VC_KP_3:
                return KeyEvent.VK_NUMPAD3;
            case NativeKeyEvent.VC_KP_4:
                return KeyEvent.VK_NUMPAD4;
            case NativeKeyEvent.VC_KP_5:
                return KeyEvent.VK_NUMPAD5;
            case NativeKeyEvent.VC_KP_6:
                return KeyEvent.VK_NUMPAD6;
            case NativeKeyEvent.VC_KP_7:
                return KeyEvent.VK_NUMPAD7;
            case NativeKeyEvent.VC_KP_8:
                return KeyEvent.VK_NUMPAD8;
            case NativeKeyEvent.VC_KP_9:
                return KeyEvent.VK_NUMPAD9;
            case NativeKeyEvent.VC_KP_0:
                return KeyEvent.VK_NUMPAD0;
            /**
             * Begin Cursor Key Zone
             */
            case NativeKeyEvent.VC_UP:
                return KeyEvent.VK_KP_UP;
            case NativeKeyEvent.VC_LEFT:
                return KeyEvent.VK_KP_LEFT;
            case NativeKeyEvent.VC_RIGHT:
                return KeyEvent.VK_KP_RIGHT;
            case NativeKeyEvent.VC_DOWN:
                return KeyEvent.VK_KP_DOWN;
            /**
             * Begin Function Key Zone
             */
            case NativeKeyEvent.VC_ESCAPE:
                return KeyEvent.VK_ESCAPE;
            case NativeKeyEvent.VC_BACKQUOTE:
                return KeyEvent.VK_BACK_QUOTE;
            case NativeKeyEvent.VC_BACKSPACE:
                return KeyEvent.VK_BACK_SPACE;
            case NativeKeyEvent.VC_TAB:
                return KeyEvent.VK_TAB;
            case NativeKeyEvent.VC_CAPS_LOCK:
                return KeyEvent.VK_CAPS_LOCK;
            case NativeKeyEvent.VC_ENTER:
                return KeyEvent.VK_ENTER;
            case NativeKeyEvent.VC_SPACE:
                return KeyEvent.VK_SPACE;
            case NativeKeyEvent.VC_PRINTSCREEN:
                return KeyEvent.VK_PRINTSCREEN;
            case NativeKeyEvent.VC_SCROLL_LOCK:
                return KeyEvent.VK_SCROLL_LOCK;
            case NativeKeyEvent.VC_PAUSE:
                return KeyEvent.VK_PAUSE;
            /**
             * Modifier and Control Keys
             */
            case NativeKeyEvent.VC_SHIFT_L:
                return KeyEvent.VK_SHIFT;
            case NativeKeyEvent.VC_SHIFT_R:
                return KeyEvent.VK_SHIFT;
            case NativeKeyEvent.VC_CONTROL_L:
                return KeyEvent.VK_CONTROL;
            case NativeKeyEvent.VC_CONTROL_R:
                return KeyEvent.VK_CONTROL;
            case NativeKeyEvent.VC_ALT_L: // Option or Alt Key
                return KeyEvent.VK_ALT;
            case NativeKeyEvent.VC_ALT_R: // Option or Alt Key
                return KeyEvent.VK_ALT;
            case NativeKeyEvent.VC_META_L: // Windows or Command Key
                return KeyEvent.VK_META;
            case NativeKeyEvent.VC_META_R: // Windows or Command Key
                return KeyEvent.VK_META;
            case NativeKeyEvent.VC_CONTEXT_MENU:
                return KeyEvent.VK_CONTEXT_MENU;
        }
        return 0;
    }

    public static char nativeKeyToChar(int value, int modifier) {
        boolean shift = (modifier & NativeInputEvent.SHIFT_MASK) == 0;
        switch (value) {
            /**
             * VC_0 thru VC_9
             */
            case NativeKeyEvent.VC_1:
                return shift ? '1' : '!';
            case NativeKeyEvent.VC_2:
                return shift ? '2' : '@';
            case NativeKeyEvent.VC_3:
                return shift ? '3' : '#';
            case NativeKeyEvent.VC_4:
                return shift ? '4' : '$';
            case NativeKeyEvent.VC_5:
                return shift ? '5' : '%';
            case NativeKeyEvent.VC_6:
                return shift ? '6' : '^';
            case NativeKeyEvent.VC_7:
                return shift ? '7' : '&';
            case NativeKeyEvent.VC_8:
                return shift ? '8' : '*';
            case NativeKeyEvent.VC_9:
                return shift ? '9' : '(';
            case NativeKeyEvent.VC_0:
                return shift ? '0' : ')';
            case NativeKeyEvent.VC_MINUS: // '-':
                return shift ? '-' : '_';
            case NativeKeyEvent.VC_EQUALS: // '=':
                return shift ? '=' : '+';
            /**
             * VC_A thru VC_Z
             */
            case NativeKeyEvent.VC_A:
                return shift ? 'a' : 'A';
            case NativeKeyEvent.VC_B:
                return shift ? 'b' : 'B';
            case NativeKeyEvent.VC_C:
                return shift ? 'c' : 'C';
            case NativeKeyEvent.VC_D:
                return shift ? 'd' : 'D';
            case NativeKeyEvent.VC_E:
                return shift ? 'e' : 'E';
            case NativeKeyEvent.VC_F:
                return shift ? 'f' : 'F';
            case NativeKeyEvent.VC_G:
                return shift ? 'g' : 'G';
            case NativeKeyEvent.VC_H:
                return shift ? 'h' : 'H';
            case NativeKeyEvent.VC_I:
                return shift ? 'i' : 'I';
            case NativeKeyEvent.VC_J:
                return shift ? 'j' : 'J';
            case NativeKeyEvent.VC_K:
                return shift ? 'k' : 'K';
            case NativeKeyEvent.VC_L:
                return shift ? 'l' : 'L';
            case NativeKeyEvent.VC_M:
                return shift ? 'm' : 'M';
            case NativeKeyEvent.VC_N:
                return shift ? 'n' : 'N';
            case NativeKeyEvent.VC_O:
                return shift ? 'o' : 'O';
            case NativeKeyEvent.VC_P:
                return shift ? 'p' : 'P';
            case NativeKeyEvent.VC_Q:
                return shift ? 'q' : 'Q';
            case NativeKeyEvent.VC_R:
                return shift ? 'r' : 'R';
            case NativeKeyEvent.VC_S:
                return shift ? 's' : 'S';
            case NativeKeyEvent.VC_T:
                return shift ? 't' : 'T';
            case NativeKeyEvent.VC_U:
                return shift ? 'u' : 'U';
            case NativeKeyEvent.VC_V:
                return shift ? 'v' : 'V';
            case NativeKeyEvent.VC_W:
                return shift ? 'w' : 'W';
            case NativeKeyEvent.VC_X:
                return shift ? 'x' : 'X';
            case NativeKeyEvent.VC_Y:
                return shift ? 'y' : 'Y';
            case NativeKeyEvent.VC_Z:
                return shift ? 'z' : 'Z';
            case NativeKeyEvent.VC_OPEN_BRACKET: // '['
                return shift ? '[' : '{';
            case NativeKeyEvent.VC_CLOSE_BRACKET: // ']'
                return shift ? ']' : '}';
            case NativeKeyEvent.VC_BACK_SLASH: // '\'
                return shift ? '\\' : '|';
            case NativeKeyEvent.VC_SEMICOLON: // ':'
                return shift ? ':' : ':';
            case NativeKeyEvent.VC_QUOTE: // '''
                return shift ? '\'' : '"';
            case NativeKeyEvent.VC_COMMA: // ','
                return shift ? ',' : '<';
            case NativeKeyEvent.VC_PERIOD: // '.'
                return shift ? '.' : '>';
            case NativeKeyEvent.VC_SLASH: // '/'
                return shift ? '/' : '?';
            /**
             * Begin Numeric Zone
             */
            case NativeKeyEvent.VC_KP_DIVIDE:
                return '/';
            case NativeKeyEvent.VC_KP_MULTIPLY:
                return '*';
            case NativeKeyEvent.VC_KP_SUBTRACT:
                return '-';
            case NativeKeyEvent.VC_KP_EQUALS:
                return '=';
            case NativeKeyEvent.VC_KP_ADD:
                return '+';
            case NativeKeyEvent.VC_KP_ENTER:
                return '\n';
            case NativeKeyEvent.VC_KP_SEPARATOR:
                return '-';
            case NativeKeyEvent.VC_KP_1:
                return '1';
            case NativeKeyEvent.VC_KP_2:
                return '2';
            case NativeKeyEvent.VC_KP_3:
                return '3';
            case NativeKeyEvent.VC_KP_4:
                return '4';
            case NativeKeyEvent.VC_KP_5:
                return '5';
            case NativeKeyEvent.VC_KP_6:
                return '6';
            case NativeKeyEvent.VC_KP_7:
                return '7';
            case NativeKeyEvent.VC_KP_8:
                return '8';
            case NativeKeyEvent.VC_KP_9:
                return '9';
            case NativeKeyEvent.VC_KP_0:
                return '0';
            /**
             * Begin Function Key Zone
             */
            case NativeKeyEvent.VC_BACKQUOTE:
                return shift ? '`' : '~';
            case NativeKeyEvent.VC_TAB:
                return '\t';
            case NativeKeyEvent.VC_ENTER:
                return '\n';
            case NativeKeyEvent.VC_SPACE:
                return ' ';
        }
        return '\0';
    }
}

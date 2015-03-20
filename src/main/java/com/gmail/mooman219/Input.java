package com.gmail.mooman219;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class Input implements NativeKeyListener {

    private static final Object object = new Object();
    private static Input singleton = null;

    public static void register() {
        synchronized (object) {
            if (singleton == null) {
                singleton = new Input();
            } else {
                throw new IllegalStateException("Already registered.");
            }
            try {
                // Clear previous logging configurations.
                LogManager.getLogManager().reset();

                // Get the logger for "org.jnativehook" and set the level to off.
                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                logger.setLevel(Level.OFF);

                // Only register if hook isn't already registered.
                if (!GlobalScreen.isNativeHookRegistered()) {
                    GlobalScreen.registerNativeHook();
                }
                GlobalScreen.getInstance().addNativeKeyListener(singleton);
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void unregister() {
        synchronized (object) {
            if (singleton == null) {
                throw new IllegalStateException("Already unregistered.");
            }
            try {
                GlobalScreen.getInstance().removeNativeKeyListener(singleton);
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
            singleton = null; // Let the GC handle this
        }
    }

    private Input() {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        if (nke.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            Input.unregister();
            System.exit(1);
            return;
        }
        Operation operation = null;
        switch (nke.getKeyCode()) {
            case NativeKeyEvent.VC_BACKSPACE:
                operation = new Operation(Operation.Type.REMOVE, '\0');
                break;
            case NativeKeyEvent.VC_ENTER:
            case NativeKeyEvent.VC_SPACE:
                operation = new Operation(Operation.Type.RESET, '\0');
                break;
            default:
                operation = new Operation(Operation.Type.ADD, NativeInputHelper.nativeKeyToChar(nke.getKeyCode(), nke.getModifiers()));
                break;
        }
        try {
            CompleteMe.queue.put(operation);
        } catch (InterruptedException ex) {
            // Eat
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
    }
}

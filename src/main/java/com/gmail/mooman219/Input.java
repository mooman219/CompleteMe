package com.gmail.mooman219;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
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
                GlobalScreen.getInstance().setEventDispatcher(new VoidDispatchService());
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
                GlobalScreen.getInstance().setEventDispatcher(null);
                GlobalScreen.getInstance().removeNativeKeyListener(singleton);
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }
            singleton = null; // Let the GC handle this
        }
    }

    private static class VoidDispatchService extends AbstractExecutorService {

        private boolean running = false;

        public VoidDispatchService() {
            running = true;
        }

        public void shutdown() {
            running = false;
        }

        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<Runnable>(0);
        }

        public boolean isShutdown() {
            return !running;
        }

        public boolean isTerminated() {
            return !running;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void execute(Runnable r) {
            r.run();
        }
    }

    private Input() {
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        System.out.println("Pressed " + NativeKeyEvent.getKeyText(nke.getKeyCode()) + " / " + NativeKeyEvent.getModifiersText(nke.getModifiers()));
        if (nke.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.exit(0);
            Input.unregister();
            return;
        }
        Operation operation = null;
        switch (nke.getKeyCode()) {
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
                if ((nke.getModifiers() & NativeInputEvent.CTRL_MASK) != 0) {
                    operation = new Operation(Operation.Type.ACCEPT, '\0');
                    NativeInputHelper.consumeNativeInputEvent(nke);
                    break;
                }
            case NativeKeyEvent.VC_ENTER:
                operation = new Operation(Operation.Type.RESET, '\0');
                break;
            default:
                char result = NativeInputHelper.nativeKeyToChar(nke.getKeyCode(), nke.getModifiers());
                if (result != '\0') {
                    operation = new Operation(Operation.Type.ADD, result);
                }
                break;
        }
        if (operation != null) {
            try {
                CompleteMe.queue.put(operation);
            } catch (InterruptedException ex) {
                // Eat
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {
        System.out.println("Released " + NativeKeyEvent.getKeyText(nke.getKeyCode()));
        if (nke.getKeyCode() == NativeKeyEvent.VC_SPACE && (nke.getModifiers() & NativeInputEvent.CTRL_MASK) != 0) {
            NativeInputHelper.consumeNativeInputEvent(nke);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
    }
}
//

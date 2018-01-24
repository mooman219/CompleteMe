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
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 * @author Joseph Cumbo (mooman219)
 */
public class Input implements NativeKeyListener {

    private static final Object object = new Object();
    private static Input singleton = null;
    private final Controller controller;

    public static void register(Controller controller) {
        synchronized (object) {
            if (singleton == null) {
                singleton = new Input(controller);
            } else {
                throw new IllegalStateException("Already registered.");
            }
            try {
                // Only register if hook isn't already registered.
                if (!GlobalScreen.isNativeHookRegistered()) {
                    // Clear previous logging configurations.
                    LogManager.getLogManager().reset();

                    // Get the logger for "org.jnativehook" and set the level to off.
                    Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
                    logger.setLevel(Level.OFF);

                    GlobalScreen.registerNativeHook();
                    GlobalScreen.setEventDispatcher(new VoidDispatchService());
                }
                GlobalScreen.addNativeKeyListener(singleton);
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
                GlobalScreen.setEventDispatcher(null);
                GlobalScreen.removeNativeKeyListener(singleton);
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

        @Override
        public void shutdown() {
            running = false;
        }

        @Override
        public List<Runnable> shutdownNow() {
            running = false;
            return new ArrayList<>(0);
        }

        @Override
        public boolean isShutdown() {
            return !running;
        }

        @Override
        public boolean isTerminated() {
            return !running;
        }

        @Override
        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return true;
        }

        @Override
        public void execute(Runnable r) {
            r.run();
        }
    }

    private Input(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        System.out.println("Pressed " + NativeKeyEvent.getKeyText(e.getKeyCode()) + " / " + NativeKeyEvent.getModifiersText(e.getModifiers()));
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            System.exit(0);
            Input.unregister();
            return;
        }
        controller.consume(e, true);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        System.out.println("Released " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        controller.consume(e, false);
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {
    }
}

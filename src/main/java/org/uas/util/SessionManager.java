package org.uas.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SessionManager implements Serializable {
    private static final String SESSION_FILE = "session.ser";

    private static SessionManager instance;
    private boolean isLoggedIn = false;

    // Static method untuk mendapatkan instance singleton
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            instance.createSessionFile();
        }

        return instance;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            saveSession();
        } else {
            loadSession();
        }
    }

    private void loadSession() {
        try (ObjectInputStream saya = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            SessionManager sessionManager = (SessionManager) saya.readObject();
            this.isLoggedIn = sessionManager.isLoggedIn;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error session sedang loading: " + e.getMessage());
        }

    }

    private void saveSession() {
        try (ObjectOutputStream session = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            session.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to simulate login
    public void login() {
        isLoggedIn = true;
        saveSession();

    }

    // Method to simulate logout
    public void logout() {
        isLoggedIn = false;
        saveSession();

    }
}

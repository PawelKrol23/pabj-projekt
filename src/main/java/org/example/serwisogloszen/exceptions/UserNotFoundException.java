package org.example.serwisogloszen.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User with login " + username + " not found.");
    }
}
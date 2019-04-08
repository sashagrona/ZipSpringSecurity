package net.bigmir.model;

public enum  UserRole {
    USER,ADMIN;

    @Override
    public String toString() {
        return "ROLE_" +name();
    }
}

package com.example.engan.model;

import androidx.annotation.NonNull;
import java.io.Serializable;

public class Player implements Serializable {
    public enum Role { 
        PARTICIPANTE("Participante"), 
        ENGANON("Engañón"), 
        ENCARNI("Encarni");
        
        private final String displayName;
        Role(String displayName) { this.displayName = displayName; }
        public String getDisplayName() { return displayName; }
    }

    private String name;
    private Role role;

    public Player(String name) {
        this.name = name;
        this.role = Role.PARTICIPANTE;
    }

    public String getName() { return name; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}

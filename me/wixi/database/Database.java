package me.wixi.database;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;

public abstract class Database {
    
    @Getter
    @Setter
    private Connection connection;

    public abstract void connect();
    
    @SneakyThrows
    public boolean isConnected() {
        return !(this.connection == null || this.connection.isClosed());
    }

    @SneakyThrows
    public void disconnect() {
        if (!isConnected()) return;
        this.connection.close();
    }

}
package me.wixi.database.types;

import me.wixi.database.Database;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.DriverManager;

@AllArgsConstructor
public class SQLiteDatabase extends Database {

    private final JavaPlugin plugin;

    @SneakyThrows
    @Override
    public void connect() {

        if (isConnected()) return;

        val logger = this.plugin.getLogger();
        val databaseFile = new File(this.plugin.getDataFolder(), "database.db");

        if (!databaseFile.exists())
            if (!databaseFile.createNewFile()) logger.severe("[ERROR] Failed to create database file");

        try {
            Class.forName("org.sqlite.JDBC");

            setConnection(
                    DriverManager.getConnection("jdbc:sqlite:" + databaseFile)
            );
        } catch (ClassNotFoundException exception) {
            logger.severe("[ERROR] Failed to connect to SQLite server.");
        }

        logger.info("[INFO] Successfully connected to SQLite.");
    }

}
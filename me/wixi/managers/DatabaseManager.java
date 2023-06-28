package me.wixi.managers;

import me.wixi.database.Database;
import me.wixi.database.DatabaseContainer;
import me.wixi.database.types.MySQLDatabase;
import me.wixi.database.types.SQLiteDatabase;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.util.UUID;

public class DatabaseManager {

    @Getter
    private final Database database;
    private final boolean mysql;

    public DatabaseManager(JavaPlugin plugin) {
        this.mysql = plugin.getConfig().getString("database.type").equalsIgnoreCase("MySQL");
        this.database = this.mysql ? new MySQLDatabase(plugin) : new SQLiteDatabase(plugin);
        this.database.connect();
    }

    @SneakyThrows
    public PreparedStatement prepareStatement(String sql, Object... objectsToSet) {
        val statement = this.database.getConnection().prepareStatement(sql);
        for (int i = 0; i < objectsToSet.length; i++) statement.setObject(i + 1, objectsToSet[i]);
        return statement;
    }

    @SneakyThrows
    public void createTable(String tableName, String columns) {
        val id = this.mysql ? "id INT AUTO_INCREMENT PRIMARY KEY" : "id INTEGER PRIMARY KEY AUTOINCREMENT";
        val query = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + id + ", uniqueId VARCHAR(36) NOT NULL, " + columns + ")";
        val statement = prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    public boolean exists(String table, UUID uniqueId) {
        val query = "SELECT * FROM " + table + " WHERE uniqueId = ?";
        val statement = prepareStatement(query, uniqueId.toString());
        val resultSet = statement.executeQuery();
        val exists = resultSet.next();

        statement.close();
        resultSet.close();

        return exists;
    }

    @SneakyThrows
    public Object get(String table, UUID uniqueId, String column) {
        val query = "SELECT * FROM " + table + " WHERE uniqueId = ?";
        val statement = prepareStatement(query, uniqueId.toString());
        val resultSet = statement.executeQuery();

        if (!resultSet.next()) return null;
        val result = resultSet.getObject(column);

        statement.close();
        resultSet.close();

        return result;
    }

    @SneakyThrows
    public void setIfAbsent(String table, DatabaseContainer databaseContainer, UUID uniqueId, Object... objects) {
        if (exists(table, uniqueId)) return;

        val query = "INSERT INTO " + table + databaseContainer.getColumnsAndQuestionMarks();
        val objectToSet = new Object[objects.length + 1];
        objectToSet[0] = uniqueId.toString();
        System.arraycopy(objects, 0, objectToSet, 1, objects.length);
        val statement = prepareStatement(query, objectToSet);

        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    public void update(String table, UUID uniqueId, String column, Object objectToSet) {
        if (!exists(table, uniqueId)) return;

        val query = "UPDATE " + table + " SET " + column + " = ? WHERE uniqueId = ?";
        val statement = prepareStatement(query, objectToSet, uniqueId.toString());

        statement.executeUpdate();
        statement.close();
    }

    @SneakyThrows
    public void delete(String table, UUID uniqueId) {
        if (!exists(table, uniqueId)) return;
        
        val query = "DELETE FROM " + table + " WHERE uniqueId = ?";
        val statement = prepareStatement(query, uniqueId.toString());

        statement.executeUpdate();
        statement.close();
    }

}

package me.wixi.database.types;

import me.wixi.database.Database;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;

@AllArgsConstructor
public class MySQLDatabase extends Database {

    private final JavaPlugin plugin;

    @SneakyThrows
    @Override
    public void connect() {

        if (isConnected()) return;

        val logger = this.plugin.getLogger();
        val config = this.plugin.getConfig();

        try {
            setConnection(
                    DriverManager.getConnection("jdbc:mysql://" + config.getString("database.host") + ":" + config.getInt("database.port") + "/"
                            + config.getString("database.database-name"), config.getString("database.username"), config.getString("database.password"))
            );
        } catch (Exception exception) {
            logger.severe("[ERROR] Failed to connect to MySQL server. are the credentials correct?");
            this.plugin.getServer().getPluginManager().disablePlugin(this.plugin);
            return;
        }

        logger.info("[INFO] Successfully connected to MySQL.");
    }

}

# DatabaseAPI
A simple MySQL and SQLite database API for anyone whos frustrated with the sql's syntax.

### Example Code:
```java
// databaseManager have to only registered once.
val databaseManager = new DatabaseManager();

// here we create the table.
databaseManager.createTable("Test", "name VARCHAR(40) NOT NULL, age INT DEFAULT 1");

// we set the row if there isn't any matching with that unique id.
databaseManager.setIfAbsent("Test", new DatabaseContainer("name", "age"), 
        UUID.fromString("6c1e7658-3ce1-4ed1-bdcc-b0630a0de2fa"), "SomeonesName", 18);
```

## There are more. (DatabaseManager class)

- Use ``prepareStatement()`` method, to prepare any statement of choice.
- Use ``createTable()`` method, to create a table.
- Use ``update()`` method, to update existing row in database table.
- Use ``exists()`` method, to check if a row exists.
- Use ``get()`` method, to get a row.
- Use ``setIfAbsent`` method, to set a row if it doesn't exist.

## How to use
Add repository and dependencies to maven.
```xml
<repositories>
        
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
     </repository>
        
</repositories>

<dependencies>
        
    <!-- DatabaseAPI -->
    <dependency>
        <groupId>com.github.iWixii</groupId>
        <artifactId>DatabaseAPI</artifactId>
        <version>LATEST_COMMIT_TAG</version>
    <dependency>

    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.26</version>
        <scope>provided</scope>
    </dependency>
            
    <!-- MySQL-Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
        <scope>provided</scope>
    </dependency>
            
</dependencies>
```

## Pro TIP:
Use [SQLite Viewer App](https://sqliteviewer.app/) to see your rows and tables. It's free!

#### Was it helpful? consider giving a star!

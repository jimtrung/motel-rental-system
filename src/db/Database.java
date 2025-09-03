package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Database {
  private static final String URL = "jdbc:postgresql://localhost:5432/rental-system-management";
  private static final String USER = "postgres";
  private static final String PASSWORD = "trung123";

  public static Connection getConnection() {
    try {
      Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

      return conn;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void init() {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {

      // 000001_enable_pgcrypto_extension.up
      String createPgcryptoExtension = """
          CREATE EXTENSION IF NOT EXISTS "pgcrypto";
          """;

      stmt.execute(createPgcryptoExtension);

      System.out.println("01/01 - Create pgcrypto extension");

      // 000002_create_users_table.up
      String createUsersTable = """
          CREATE TABLE IF NOT EXISTS users (
              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
          
              username TEXT NOT NULL UNIQUE CHECK (LENGTH(username) >= 3),
              email TEXT NOT NULL UNIQUE,
              password TEXT DEFAULT NULL,
          
              role TEXT NOT NULL CHECK(role IN ('ADMINSTRATOR', 'USER', 'MODERATOR')) DEFAULT 'USER',
              provider TEXT NOT NULL CHECK (provider IN ('LOCAL', 'GOOGLE')),
              token TEXT NOT NULL DEFAULT '',
              verified BOOLEAN NOT NULL DEFAULT FALSE,
          
              created_at TIMESTAMPTZ DEFAULT NOW(),
              updated_at TIMESTAMPTZ DEFAULT NOW()
          );
          """;

      stmt.execute(createUsersTable);

      System.out.println("02/02 - Create users table");

      // 000003_create_function_auto_update_updated_at_column.up
      String createFunctionAutoUpdateUpdatedAtColumn = """
          CREATE OR REPLACE FUNCTION update_updated_at_column()
          RETURNS TRIGGER AS $$
          BEGIN
            NEW.updated_at = NOW();
            RETURN NEW;
          END;
          $$ LANGUAGE plpgsql;
          """;

      stmt.execute(createFunctionAutoUpdateUpdatedAtColumn);

      System.out.println("03/03 - Create function auto update updated_at column");

      // 000004_create_trigger_update_updated_at_users_table.up
      String createTriggerUpdateUpdatedAtUsersTable = """
          DROP TRIGGER IF EXISTS set_updated_at_users ON users;
          
          CREATE TRIGGER set_updated_at_users
          BEFORE UPDATE ON users
          FOR EACH ROW
          EXECUTE FUNCTION update_updated_at_column();
          """;

      stmt.execute(createTriggerUpdateUpdatedAtUsersTable);

      System.out.println("04/04 - Create trigger auto update updated_at column users table");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void delete() {
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {

      // 000001_enable_pgcrypto_extension.down
      String dropPgcryptoExtension = """
          DROP EXTENSION IF EXISTS "pgcrypto";
          """;

      stmt.execute(dropPgcryptoExtension);

      System.out.println("01/01 - Drop pgcrypto extension");

      // 000002_create_users_table.down
      String dropUsersTable = """
            DROP TABLE IF EXISTS users;
          """;

      stmt.execute(dropUsersTable);

      System.out.println("02/02 - Drop users table");

      // 000003_create_function_auto_update_updated_at_column.down
      String dropFunctionAutoUpdateUpdatedAtColumn = """
          DROP FUNCTION IF EXISTS update_updated_at_column();
          """;

      stmt.execute(dropFunctionAutoUpdateUpdatedAtColumn);

      System.out.println("03/03 - Drop function auto update updated_at column");

      // 000004_create_trigger_update_updated_at_users_table.down
      String dropTriggerUpdateUpdatedAtUsersTable = """
          DROP TRIGGER IF EXISTS set_updated_at_users ON users;
          """;

      stmt.execute(dropTriggerUpdateUpdatedAtUsersTable);

      System.out.println("04/04 - Drop trigger auto update updated_at column users table");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

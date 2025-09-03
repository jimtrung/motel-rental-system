package repository;

import model.Provider;
import model.User;
import model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.UUID;

public class UserRepository {
  /* Attributes */
  private final Connection conn;

  /* Constructors */
  public UserRepository(Connection conn) {
    this.conn = conn;
  }

  /* Functions */
  // CRUD
  // Create
  public void create(User user) throws SQLException {
    String sql = """
        INSERT INTO users (username, email, password, role, provider, token, verified)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.username);
      stmt.setString(2, user.email);
      stmt.setString(3, user.password);
      stmt.setString(4, user.role.toString());
      stmt.setString(5, user.provider.toString());
      stmt.setString(6, user.token);
      stmt.setBoolean(7, user.verified);

      stmt.executeUpdate();
    }
  }

  // Read
  public User getById(UUID id) throws SQLException {
    String sql = """
        SELECT id, username, email, password,  role, provider, token, verified, created_at, updated_at
        FROM users
        WHERE id = ?
        """;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setObject(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        User user = new User();
        if (rs.next()) {
          user.id = rs.getObject("id", java.util.UUID.class);   // if UUID
          user.username = rs.getString("username");
          user.email = rs.getString("email");
          user.password = rs.getString("password");

          // enum mapping (assuming you saved role as string)
          user.role = UserRole.valueOf(rs.getString("role").toUpperCase());

          user.provider = Provider.valueOf(rs.getString("provider"));
          user.token = rs.getString("token");
          user.verified = rs.getBoolean("verified");

          user.createdAt = rs.getObject("created_at", java.time.OffsetDateTime.class);
          user.updatedAt = rs.getObject("updated_at", java.time.OffsetDateTime.class);

          return user;
        }
      }
    }

    return null;
  }

  public User getByUsername(String username) throws SQLException {
    String sql = """
        SELECT id, username, email, password,  role, provider, token, verified, created_at, updated_at
        FROM users
        WHERE username = ?
        """;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setObject(1, username);
      try (ResultSet rs = stmt.executeQuery()) {
        User user = new User();
        if (rs.next()) {
          user.id = rs.getObject("id", java.util.UUID.class);   // if UUID
          user.username = rs.getString("username");
          user.email = rs.getString("email");
          user.password = rs.getString("password");

          // enum mapping (assuming you saved role as string)
          user.role = UserRole.valueOf(rs.getString("role").toUpperCase());

          user.provider = Provider.valueOf(rs.getString("provider"));
          user.token = rs.getString("token");
          user.verified = rs.getBoolean("verified");

          user.createdAt = rs.getObject("created_at", java.time.OffsetDateTime.class);
          user.updatedAt = rs.getObject("updated_at", java.time.OffsetDateTime.class);

          return user;
        }
      }
    }

    return null;
  }

  public User getByEmail(String email) throws SQLException {
    String sql = """
        SELECT id, username, email, password,  role, provider, token, verified, created_at, updated_at
        FROM users
        WHERE email = ?
        """;

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setObject(1, email);
      try (ResultSet rs = stmt.executeQuery()) {
        User user = new User();
        if (rs.next()) {
          user.id = rs.getObject("id", java.util.UUID.class);   // if UUID
          user.username = rs.getString("username");
          user.email = rs.getString("email");
          user.password = rs.getString("password");

          // enum mapping (assuming you saved role as string)
          user.role = UserRole.valueOf(rs.getString("role").toUpperCase());

          user.provider = Provider.valueOf(rs.getString("provider"));
          user.token = rs.getString("token");
          user.verified = rs.getBoolean("verified");

          user.createdAt = rs.getObject("created_at", java.time.OffsetDateTime.class);
          user.updatedAt = rs.getObject("updated_at", java.time.OffsetDateTime.class);

          return user;
        }
      }
    }

    return null;
  }

  // Update
  public void updateField(UUID id, String field, Object newValue) throws Exception {
    // Check if field is valid
    Set<String> allowed = Set.of("username", "email", "password", "role", "token", "verified");

    if (!allowed.contains(field)) {
      throw new Exception("Invalid field " + field);
    }

    String sql = "UPDATE users SET " + field + " = ? WHERE id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setObject(1, newValue);
      stmt.setObject(2, id);
      stmt.executeUpdate();
    }
  }

  // Delete
  public void delete(UUID id) throws Exception {
    String sql = "DELETE * FROM users WHERE id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setObject(1, id);
      stmt.executeUpdate();
    }
  }
}

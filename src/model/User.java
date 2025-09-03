package model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class User {
  /* Atributes */
  public UUID id;
  public String username;
  public String email;
  public String password;
  public UserRole role;
  public Provider provider;
  public String token;
  public Boolean verified;
  public OffsetDateTime createdAt;
  public OffsetDateTime updatedAt;

  /* Constructors */
  public User() {}
}

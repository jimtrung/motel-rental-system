package service;

import model.Provider;
import model.User;
import model.UserRole;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;
import util.TokenUtil;

public class UserService {
  // Attribute
  private final UserRepository repo;

  // Constructor
  public UserService(UserRepository repo) {
    this.repo = repo;
  }

  // Functions
  public void signUp(String username, String email, String password) throws Exception {
    // Check if any field is empty
    if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
      throw new Exception("Username or email or password is empty");
    }

    // Check if username/email exists
    User user1 = repo.getByEmail(email);
    if (user1 != null) {
      throw new Exception("Email already exists");
    }

    User user2 = repo.getByUsername(username);
    if (user2 != null) {
      throw new Exception("Username already exists");
    }

    // Hash password
    String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());

    // Add token
    String token = TokenUtil.generateToken(32);

    // Add user to database
    User user = new User();
    user.username = username;
    user.email = email;
    user.password = passwordHash;
    user.token = token;
    user.verified = false;
    user.role = UserRole.USER;
    user.provider = Provider.LOCAL;

    repo.create(user);

    // Send email
  }

}

package model;

import com.google.gson.Gson;

import java.util.Objects;

public record UserData(String username, String password, String email) {
  public String toString() {
    return new Gson().toJson(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserData userData=(UserData) o;
    return Objects.equals(username, userData.username) && Objects.equals(password, userData.password) && Objects.equals(email, userData.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, email);
  }
}

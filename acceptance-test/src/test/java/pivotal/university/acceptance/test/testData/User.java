package pivotal.university.acceptance.test.testData;

import pivotal.university.acceptance.test.config.Settings;

import java.util.UUID;

public class User {

 private String username;
 private String password;
 private String emailAddress;

 public User(){
     username = "test" + String.valueOf(UUID.randomUUID()).substring(0,8);
     password = String.valueOf(UUID.randomUUID()).substring(0,8);
     emailAddress = "a" + String.valueOf(UUID.randomUUID()).substring(0,8) + "@example.com";
 }

 public void setUsername(String username){
     this.username = username;
 }

 public void setPassword(String password){
     this.password = password;
 }

 public String setNewPassword(){
     this.password = String.valueOf(UUID.randomUUID()).substring(0,8);
     return password;
 }

 public String getUsername(){
     return username;
 }

 public String getPassword(){
     return password;
 }

 public String getEmailAddress(){
     return emailAddress;
 }

 public String getAdminUsername(){
     return Settings.adminUsername;
 }
}

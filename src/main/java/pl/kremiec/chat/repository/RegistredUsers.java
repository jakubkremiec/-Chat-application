package pl.kremiec.chat.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class RegistredUsers {

    @Id
    private String username;

    private String email;
    private String password;
    private LocalDate birthDate;

    public RegistredUsers() {
    }

    public static class Builder{
        private String email;
        private String username;
        private String password;
        private LocalDate birthDate;


        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder birthDate(LocalDate birthDate){
            this.birthDate = birthDate;
            return this;
        }

        public RegistredUsers build(){
            return new RegistredUsers(this);
        }
    }

    private RegistredUsers(Builder builder){
        this.email = builder.email;
        this.birthDate = builder.birthDate;
        this.password = builder.password;
        this.username = builder.username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}

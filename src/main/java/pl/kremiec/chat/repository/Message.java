package pl.kremiec.chat.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Message {

@Id
private LocalDateTime time;
private String formWho;
private String message;

    public Message() {
    }

    public static class Builder{
        private LocalDateTime time;
        private String formWho;
        private String message;

        public Builder time(LocalDateTime time){
            this.time = time;
            return this;
        }

        public Builder fromWho(String formWho){
            this.formWho = formWho;
            return this;
        }

        public Builder message(String message){
            this.message = message;
            return this;
        }

        public Message build(){
            return new Message(this);
        }
    }

    private Message(Builder builder){
        this.time = builder.time;
        this.formWho = builder.formWho;
        this.message = builder.message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getFormWho() {
        return formWho;
    }

    public String getMessage() {
        return message;
    }
}

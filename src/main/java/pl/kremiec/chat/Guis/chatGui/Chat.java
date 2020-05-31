package pl.kremiec.chat.Guis.chatGui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.chat.Guis.loginGui.Login;
import pl.kremiec.chat.repository.Message;
import pl.kremiec.chat.repository.MessageRepo;
import pl.kremiec.chat.repository.RegistredUsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Route("chat")
public class Chat extends VerticalLayout {

    private RegistredUsers user = Login.user;
    MessageRepo messageRepo;

    @Autowired
    Chat(MessageRepo messageRepo){

        this.messageRepo=messageRepo;

        try {
            add(ChatLayout()); //adding chat layout to main layout
        }catch (Exception ex){
            UI.getCurrent().navigate("");
        }

    }

    public VerticalLayout ChatLayout(){

        VerticalLayout chatVerticalLayout = new VerticalLayout();

        chatVerticalLayout.add(new Label("Welcome " + user.getUsername()));

        TextArea chatArea = new TextArea();
        chatArea.setValue(getMessages()); //if you go to /chat, it's add messages at start to chat area
        chatArea.setWidth("50%");
        chatArea.setHeight("50%");
        chatVerticalLayout.add(chatArea);

        HorizontalLayout messageHorizontalLayout = new HorizontalLayout();
        TextField messageField = new TextField();
        Button messageButton = new Button("Send");
        messageButton.addClickListener(click->{

            Message message = new Message.Builder()     //creating objects of provided details
                    .message(messageField.getValue())
                    .fromWho(user.getUsername())
                    .time(LocalDateTime.now())
                    .build();

            messageField.setValue(""); //clearing message field
            messageRepo.save(message); //saving message object to database
            chatArea.setValue(getMessages());
        });

        messageHorizontalLayout.add(messageField,messageButton);
        chatVerticalLayout.add(messageHorizontalLayout);

        Button refreshButton = new Button("Refresh");
        chatVerticalLayout.add(refreshButton);
        refreshButton.addClickListener(buttonClickEvent -> {
            chatArea.setValue(getMessages());
        });


        return chatVerticalLayout;
    }

    public String getMessages(){    //creating new String of messages
        final String[] str = {""};

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        messageRepo.findAll().forEach(message -> {
            str[0] = str[0] + message.getTime().format(dateTimeFormatter) + " - ";
            if(message.getFormWho().equals(user.getUsername())){
                 str[0] = str[0] + "you";
            } else {
                str[0] = str[0] + message.getFormWho();
            }
            str[0] = str[0] + ": " + message.getMessage() + "\n";
        });
        return str[0];
    }
}

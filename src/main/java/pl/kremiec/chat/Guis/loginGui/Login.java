package pl.kremiec.chat.Guis.loginGui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import pl.kremiec.chat.repository.RegistredUsers;
import pl.kremiec.chat.repository.RegistredUsersRepo;

import java.util.ArrayList;

@Route("")
public class Login extends VerticalLayout {

    public static RegistredUsers user;      //variable visible globally

    private ArrayList<RegistredUsers> registredUsersArrayList;
    private RegistredUsersRepo registredUsersRepo;

    public Login(RegistredUsersRepo registredUsersRepo){

        this.registredUsersRepo = registredUsersRepo;
        registredUsersArrayList = new ArrayList<>();
        getUsersFromRepo(); //adding every registred user to arraylist

        add(loginLayout());
    }

    private void getUsersFromRepo(){
        registredUsersRepo.findAll().forEach(s-> registredUsersArrayList.add(s)); //add every registred user from database to arraylist
    }

    private VerticalLayout loginLayout(){

        VerticalLayout loginVerticalLayout = new VerticalLayout();

        loginVerticalLayout.add(new Label("Welcome to chat!"), new Label("Firstly, you need to login:"));

            TextField usernameTextField = new TextField("username");
            TextField passwordTextField = new TextField("password");
        loginVerticalLayout.add(usernameTextField,passwordTextField);

        Button loginButton = new Button("Login");

        loginButton.addClickListener(click->{   //checking if provided details match with any user in database
            Boolean userFound = false;
            for(RegistredUsers user: registredUsersArrayList) {

                if (user.getUsername().equals(usernameTextField.getValue()) && user.getPassword().equals(passwordTextField.getValue())) {
                    this.user = user;
                    UI.getCurrent().navigate("chat");
                    userFound = true;
                }
            }

            if(userFound==false){
                add(new Label("Incorrect details!"));
            }
        });

        loginVerticalLayout.add(loginButton);

        HorizontalLayout notRegistredHorizontalLayout = new HorizontalLayout();

            Label registerLabel = new Label("Not registred yet? click->");
            Button registerButton = new Button("Register");
            registerButton.setHeight("28px");
            registerButton.addClickListener(click-> UI.getCurrent().navigate("register"));

        notRegistredHorizontalLayout.add(registerLabel,registerButton);

        loginVerticalLayout.add(notRegistredHorizontalLayout);

        return loginVerticalLayout;
    }


}

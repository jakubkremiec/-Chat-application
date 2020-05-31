package pl.kremiec.chat.Guis.registerGui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kremiec.chat.repository.RegistredUsers;
import pl.kremiec.chat.repository.RegistredUsersRepo;

import java.util.ArrayList;


@Route("register")
public class Register extends VerticalLayout {

    private RegistredUsersRepo registredUsersRepo;

    @Autowired
    Register(RegistredUsersRepo registredUsersRepo){
        this.registredUsersRepo=registredUsersRepo;

        add(registerLayout());

    }

    public VerticalLayout registerLayout(){

        VerticalLayout registerVerticalLayout = new VerticalLayout();

            Label registerLabel = new Label("To register you need to provide your details");

            TextField emailTextField = new TextField("E-mail");
            TextField usernameTextField = new TextField("Username");
            TextField passwordTextField = new TextField("Password");
            DatePicker datePicker = new DatePicker();

            Button createButton = new Button("Register");

        registerVerticalLayout.add(registerLabel, emailTextField, usernameTextField, passwordTextField, datePicker);

        createButton.addClickListener(buttonClickEvent -> {
            RegistredUsers actualUser = new RegistredUsers.Builder()     //creating object of provided details
                    .username(usernameTextField.getValue())
                    .password(passwordTextField.getValue())
                    .email(emailTextField.getValue())
                    .birthDate(datePicker.getValue())
                    .build();

            saveToRepository(actualUser);
        });

        registerVerticalLayout.add(createButton);

        return registerVerticalLayout;
    }

    public void saveToRepository(RegistredUsers user){  //checking if provided details are not same, as one in database. Or if details are null

        ArrayList<RegistredUsers> registredUsersArrayList = new ArrayList<>();

        registredUsersRepo.findAll().forEach(s->registredUsersArrayList.add(s));

        boolean userExist=false;
        boolean detailsNull=true;

            for (RegistredUsers counter : registredUsersArrayList) {

                if(user.getUsername().equals(counter.getUsername()) || user.getEmail().equals(counter.getEmail())){
                    userExist = true;       //if user found, variable is true
                    break;
                }
            }

        if( !(user.getUsername()==null || user.getEmail()==null || user.getPassword()==null || user.getBirthDate()==null)){
            detailsNull = false;    //if any details are null, variable is false
        }

         if(userExist==false && detailsNull==false){
             registredUsersRepo.save(user);
             UI.getCurrent().navigate("");
         }else if(detailsNull==true){
             add(new Label("Details cannot be empty"));
         }else if(userExist==true){
             add(new Label("User already exist!"));
         }

    }


}

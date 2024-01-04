package ma.ensate.demandesetudiants.services;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Data //Cela est a revoir!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
@NoArgsConstructor
public class PasswordForgetReceiver {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public void passwordForget(){

    }
}

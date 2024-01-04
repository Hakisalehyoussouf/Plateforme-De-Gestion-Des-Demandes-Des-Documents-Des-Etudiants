package ma.ensate.demandesetudiants.services;


import ma.ensate.demandesetudiants.entities.Admins;
import ma.ensate.demandesetudiants.repositories.AdminsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginReceiver {

    private final AdminsRepository adminsRepository;
    private String email;
    private String password;
    private boolean result;

    @Autowired
    public LoginReceiver(AdminsRepository adminsRepository) {
        this.adminsRepository = adminsRepository;
    }



    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public boolean isResult() {
        return result;
    }


    public void login(){
        Admins admin = adminsRepository.findByEmailAndPassword(this.email, this.password);
        if(admin!=null) {
            this.result = true;
        }
        else {
            this.result=false;
        }

    }


}

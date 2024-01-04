package ma.ensate.demandesetudiants.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommandLogin implements Command {

    private LoginReceiver loginReceiver;

    @Autowired
    public CommandLogin(LoginReceiver loginReceiver) {
        this.loginReceiver = loginReceiver;
    }

    @Override
    public void excute() {
        loginReceiver.login();
    }
}

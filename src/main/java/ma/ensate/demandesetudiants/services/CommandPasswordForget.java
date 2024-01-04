package ma.ensate.demandesetudiants.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommandPasswordForget implements Command {

    private PasswordForgetReceiver passwordForgetReceiver;
    @Override
    public void excute() {
        passwordForgetReceiver.passwordForget();
    }
}

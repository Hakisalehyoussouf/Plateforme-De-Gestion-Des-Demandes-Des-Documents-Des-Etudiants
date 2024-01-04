package ma.ensate.demandesetudiants.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class CommandLogout implements Command {

    private LogoutReceiver logoutReceiver;
    @Override
    public void excute() {
        logoutReceiver.logout();
    }
}

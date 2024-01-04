package ma.ensate.demandesetudiants.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;



//Pour creation de la classe invoker, je dois voirr est-ce que je peux utiliser un design pattern de creation
@Service
@Transactional
public class Invoqueur {

    private Map<String, Command> commands = new HashMap<String,Command>();


    public void  addNewCommand(String key, Command cmd){
        commands.put(key,cmd);
    }

    public void invoquer(String key){
        Command command = commands.get(key);
        if(command!=null) {
            command.excute();
        }

    }
}

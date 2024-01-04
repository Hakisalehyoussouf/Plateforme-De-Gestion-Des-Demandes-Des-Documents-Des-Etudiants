package ma.ensate.demandesetudiants.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class FacadeEtudiant {
    private final Nombre2ap1 nombre2ap1;
    private final Nombre2ap2 nombre2ap2;
    private final NombreCi1 nombreCi1;
    private final NombreCi2 nombreCi2;
    private final NombreCi3 nombreCi3;


    public int getNumberOfStudentsByLevel(String niveau){
        int ret = 0;

        switch (niveau){
            case "2ap1" : {
                ret = nombre2ap1.getAll2ap1Etudiants();
                break;
            }
            case "2ap2" :{
                ret = nombre2ap2.getAll2ap2Etutiants();
                break;
            }
            case "ci1" :{
                ret = nombreCi1.getAllCi1Etutiants();
                break;
            }
            case "ci2" :{
                ret = nombreCi2.getAllCi2Etutiants();
                break;
            }
            case "ci3" :{
                ret = nombreCi3.getAllCi3Etutiants();
                break;
            }
        }
        return ret;
    }



}

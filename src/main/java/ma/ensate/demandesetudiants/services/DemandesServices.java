package ma.ensate.demandesetudiants.services;


import javafx.stage.FileChooser;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import ma.ensate.demandesetudiants.components.Email;
import ma.ensate.demandesetudiants.entities.*;
import ma.ensate.demandesetudiants.repositories.DemandesRepository;
import ma.ensate.demandesetudiants.repositories.HistoriquesRepository;
import ma.ensate.demandesetudiants.repositories.ReleveeRepository;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;





import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;


@Service
@Transactional
public class DemandesServices implements IDemandesServices{

    private final DemandesRepository demandesRepository;

    private final HistoriquesRepository historiquesRepository;
    private final ReleveeRepository releveeRepository;

    private final Email email;

    @Getter
    @Setter
    private Demandes demande;



    //Pour l'attestaion de reussite
    private PDDocument document;
    private String annee;
    private PDPage page;

    private PDImageXObject pdImage;
    private PDImageXObject pdImage1;
    private PDPageContentStream contentStream;
    private PDFont font;
    private final String  anneeScolaire = "2022/20023";


    /**
     *Pour les relevees des notes
     */



    private Relevee relevee;
    private double seuil;
    private final String[][] data=new String[13][3];
    private  Field[] fields;
    private  Field[] superFields;


    private String filiere_doc;


   //-- private PDDocument document;
    private String[] head;
    private  PDPage firstPage;
    private int pageHeigth;
    private  int pageWidth;
    //--private PDPageContentStream contentStream;
   //-- private PDImageXObject pdImage;
   //-- private PDImageXObject pdImage1;
    private int initX;
    private int initY;
    private int cellheight;
    private int cellWidth;
    private int colCount;
    private int rowCount;
    private int i,j;














    public DemandesServices(DemandesRepository demandesRepository, HistoriquesRepository historiquesRepository, ReleveeRepository releveeRepository, Email email) {
        this.demandesRepository = demandesRepository;
        this.historiquesRepository = historiquesRepository;
        this.releveeRepository = releveeRepository;
        this.email = email;
    }


    public int getNumberOfDemands() {
        List<Demandes> demandes = demandesRepository.findAll();
        return  demandes.size();
    }

    public List<Object[]> getDistinctDemands() {

        List<Object[]> distinctdemands = demandesRepository.countDistinctByDocument();
        return  distinctdemands;
    }

    @Override
    public List<Demandes> getDemandsByDocument(String document) {
        return demandesRepository.findByDocument(document);
    }
    @Override
    public void deleteDemandsByStudentAndDocument(Etudiants etudiants, String document) {
        demandesRepository.deleteByEtudiantAndDocument(etudiants, document);
    }

    @Override
    public void rejectedDemand(Demandes demandes) {
        this.demande = demandes;
        this.email.envoieRejet(this.demande.getEtudiant().getEmail());

    }



    @Override
    public void genererAttestationDeReussite(Demandes demandes) {


        this.demande = demandes;

        // Create a new empty document
        this.document = new PDDocument();

        if(this.demande.getFiliere().equalsIgnoreCase("gi2")){
            this.annee="1 ere Année de Cycle ingénieur: Génie Inforamtique";
        }else if(demande.getFiliere().equalsIgnoreCase("2ap2")) {
            this.annee="1 ere Année de Cycle Préparatoires: 2AP1 ";
        }

        // Create a new blank page
        this.page = new PDPage();

        // Add the page to the document
        this.document.addPage(this.page);



        try {
            // Create a new image from a file
            this.pdImage = PDImageXObject.createFromFile("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\images\\log.png", this.document);
            this.pdImage1 = PDImageXObject.createFromFile("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\images\\sign.jpeg", this.document);

            // Create a new content stream for adding content to the page
            this.contentStream = new PDPageContentStream(this.document, this.page);

            // Draw the image on the page
            // contentStream.draw
            this.contentStream.drawImage(this.pdImage, 10, 600, 600, 200);
            this.contentStream.drawImage(this.pdImage1, 200, 50,250,250);
            // float X=document.getPage(0).
            // float Y=pdImage.getHeight()/2;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }




        try {

            this.font = PDType1Font.HELVETICA;
            // Add some text after the image
            this.contentStream.beginText();
            this.contentStream.newLineAtOffset(50, 500);
            // contentStream.newLineAtOffset(25, 700);
            //  contentStream.setFont(font, 0);
            this.contentStream.setFont(font, 14);
            this.contentStream.setLeading(14.5f);
            this.contentStream.showText("Le Directeur de l'Ecole Nationale des Sciences Appliquées atteste que l'étudiant(e):  ");
            this.contentStream.newLine();
            this.contentStream.newLine();
            this.contentStream.showText(demande.getNom() +" "+ demande.getPrenom());
            this.contentStream.newLine();
            this.contentStream.newLine();
            this.contentStream.showText("a été déclaré(e) admis(e) au niveau : "+this.annee);
            this.contentStream.newLine();
            this.contentStream.newLine();
            this.contentStream.showText("au titre de l'année universitaire :  " + this.anneeScolaire);
            this.contentStream.endText();



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            this.contentStream.beginText();
            this.contentStream.newLineAtOffset( 120, 300);
            this.contentStream.setFont(PDType1Font.HELVETICA, 16);
            this.contentStream.showText("fait le   : "+java.time.LocalDate.now());
            this.contentStream.endText();



            // Close the content stream
            contentStream.close();


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.setInitialFileName("attestation de reussite.pdf");
            Window owner = null;
            File selectedFile = fileChooser.showSaveDialog(owner);

            if (selectedFile != null) {
                // Save the PDF to the chosen location
                document.save(selectedFile);
                JOptionPane.showMessageDialog(null, "PDF saved successfully");
            }

            document.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }





    }






    @Override
    public void genererConventionDeStage(Demandes demandes) {

        this.demande = demandes;

        try {

            // Create a new empty document
            PDDocument document = PDDocument.load(new File("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\Convention de stage.pdf"));
            //.save("C:\\Users\\pc\\Desktop\\STUDY\\Convention_stageee.pdf");



            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.setInitialFileName("Convention de stage.pdf");
            Window owner = null;
            File selectedFile = fileChooser.showSaveDialog(owner);

            if (selectedFile != null) {
                // Save the PDF to the chosen location
                document.save(selectedFile);
                JOptionPane.showMessageDialog(null, "PDF saved successfully");
            }


            /*JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save PDF");
            chooser.setSelectedFile(new File("Convention de stage.pdf"));
            int result = chooser.showSaveDialog(null);



            if (result == JFileChooser.APPROVE_OPTION) {
                //ave the PDF to the chosen location
                File saveFile = chooser.getSelectedFile();
                document.save(saveFile);
                JOptionPane.showMessageDialog(null, "PDF saved successfully");
            }*/

            document.close();
        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    @Override
    public void genererReleveeDesNotes(Demandes demandes) {


        this.demande = demandes;

        this.seuil = 0;
        if(this.demande.getFiliere().toLowerCase().equals("gi2")){
            this.relevee = releveeRepository.findReleveeByEtudiantApogeeAndType(this.demande.getEtudiant().getApogee(), Relevee_Gi2_Gi1.class);
            this.seuil=12;

            // Obtenez toutes les propriétés de l'objet Relevee_Gi2_Gi1
            this.fields = Relevee_Gi2_Gi1.class.getDeclaredFields();


            for (int i = 0, size = this.fields.length; i < size; i++) {
                System.out.println("size = " + size);
                Field field = this.fields[i];
                System.out.println("name = " + field.getName());
                try {
                    field.setAccessible(true);
                    System.out.println("name = " + field.get(relevee));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                if (!field.isAnnotationPresent(Transient.class)) {

                    try {
                        field.setAccessible(true); // Rendre la propriété accessible

                        String name = field.getName();
                        Object value =  field.get(relevee);

                        this.data[i][0] = name;
                        this.data[i][1] = value != null ? value.toString() : "0";

                        if( Double.parseDouble(data[i][1]) >= this.seuil){
                            data[i][2] = "validé";
                        }else {
                            data[i][2] = "non validé";
                        }


                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            Class<?> superClass = Relevee_Gi2_Gi1.class.getSuperclass();

            if (superClass != null) {
                this.superFields = superClass.getDeclaredFields();
                for (Field field : this.superFields) {
                    field.setAccessible(true);
                    System.out.println("name de superClasss = " + field.getName());
                    if (!field.isAnnotationPresent(Transient.class) && field.getName() == "moyenneN" ) {

                        try {
                            String name = field.getName();
                            Object value = field.get(this.relevee);
                            this.data[12][0] = "Moyenne";
                            this.data[12][1] = value != null ? value.toString() : "0";

                            if( Double.parseDouble(this.data[12][1]) >= this.seuil){
                                this.data[12][2] = "admis";
                            }else {
                                this.data[12][2] = "non admis";
                            }


                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }

            }

        }






        else if(this.demande.getFiliere().toLowerCase().equals("2ap2")){
            this.relevee = releveeRepository.findReleveeByEtudiantApogeeAndType(this.demande.getEtudiant().getApogee(), Relelvee_Prepa2_Prepa1.class);
            this.seuil=10;



            // Obtenez toutes les propriétés de l'objet Relevee_Gi2_Gi1
            this.fields = Relevee_Gi2_Gi1.class.getDeclaredFields();

            for (int i = 0, size = this.fields.length; i < size; i++) {
                Field field = this.fields[i];
                if (!field.isAnnotationPresent(Transient.class)) {

                    try {
                        field.setAccessible(true); // Rendre la propriété accessible

                        String name = field.getName();
                        Object value =  field.get(relevee);

                        this.data[i][0] = name;
                        this.data[i][1] = value != null ? value.toString() : "0";
                        this.data[i][2]="admis";






                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }


        //Fonction pour la generation de document
        this.document=new PDDocument();
        this.head= new String[]{"Module", "Moyenne", "Résultat"};

        this.firstPage=new PDPage();
        this.document.addPage(this.firstPage);





        try {


            this.pageHeigth= (int)firstPage.getTrimBox().getHeight();
            this.pageWidth=(int)firstPage.getTrimBox().getWidth();

            this.contentStream=new PDPageContentStream(this.document,this.firstPage);
            this.pdImage = PDImageXObject.createFromFile("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\images\\relevee.jpeg", this.document);
            this.pdImage1 = PDImageXObject.createFromFile("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\images\\sign.jpeg", this.document);
            this.contentStream.drawImage(this.pdImage, 10, 650, 600, 150);
            this.contentStream.drawImage(this.pdImage1, 200, 10,150,150);
            this.contentStream.setStrokingColor(Color.BLACK);
            this.contentStream.setLineWidth(1);
            this.initX=80;
            this.initY=this.pageHeigth-300;
            this.cellheight=20;
            this.cellWidth=150;
            this.colCount=3;
            this.rowCount=14;


            this.contentStream.beginText();
            this.contentStream.newLineAtOffset(90, this.initY-this.cellheight+100);
            this.contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            this.contentStream.showText("Nom et Prenom:   "+ this.demande.getNom() + " " + this.demande.getPrenom());

            this.contentStream.endText();
            this.contentStream.beginText();
            this.contentStream.newLineAtOffset(90, this.initY-this.cellheight+85);
            this.contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            this.contentStream.showText("Appogee           :   "+this.demande.getEtudiant().getApogee());
            this.contentStream.endText();

            if(this.demande.getEtudiant().getFiliere().equals("gi2")){
                this.filiere_doc="GI1";
            }else if(this.demande.getEtudiant().getFiliere().equals("2ap2")){
                this.filiere_doc="2AP1";
            }




        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }



        try{

            this.contentStream.beginText();
            this.contentStream.newLineAtOffset(90, this.initY-this.cellheight+70);
            this.contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            this.contentStream.showText("Filière               :   "+this.filiere_doc);
            this.contentStream.endText();


            for(this.i=0;this.i<this.rowCount;this.i++) {
                for( this.j=0;j<this.colCount;this.j++) {

                    if(this.i==13) {
                        this.contentStream.addRect(this.initX, this.initY-20, this.cellWidth, -this.cellheight);
                    } else {
                        this.contentStream.addRect(this.initX, this.initY, this.cellWidth, -this.cellheight);
                    }

                    this.contentStream.beginText();

                    if(this.i!=13) {
                        this.contentStream.newLineAtOffset(this.initX+10, this.initY-this.cellheight+10);
                    } else {
                        contentStream.newLineAtOffset(this.initX+10, this.initY-this.cellheight-10);
                    }

                    if(this.i==0 || this.i==13)  {
                        this.contentStream.setFont(PDType1Font.TIMES_BOLD, 14);
                    } else if(this.i==13){
                        this.contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                    } else {
                        this.contentStream.setFont(PDType1Font.HELVETICA, 13);
                    }


                    if(this.i==0) {
                        this.contentStream.showText(this.head[this.j]);
                    } else if(this.i==13){
                        this.contentStream.showText(this.data[12][j]);
                    } else {
                        this.contentStream.showText(this.data[i-1][j]);
                    }

                    this.contentStream.endText();
                    this.initX+=this.cellWidth;
                }
                this.initX=80;
                this.initY -=this.cellheight;
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }




        try{

            this.contentStream.beginText();

            this.contentStream.newLineAtOffset(initX+90, initY-cellheight-40);
            this.contentStream.setFont(PDType1Font.HELVETICA, 16);
            this.contentStream.showText("fait le   : "+java.time.LocalDate.now());
            this.contentStream.endText();
            this.contentStream.stroke();
            this.contentStream.close();

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }



        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName("relevee des notes.pdf");
        Window owner = null;
        File selectedFile = fileChooser.showSaveDialog(owner);

        if (selectedFile != null) {
            // Save the PDF to the chosen location
            try {
                document.save(selectedFile);
                JOptionPane.showMessageDialog(null, "PDF saved successfully");
                document.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }






    }










    public int getNumberOfHistoricalDemands(){
        List<Historiques> historiques = historiquesRepository.findAll();
        return historiques.size();
    }
    @Override
    public List<Historiques> getHistoricalDemandsByDocument(String document) {
        return historiquesRepository.findByDocument(document);
    }
    @Override
    public Historiques addHistoricalDemand(Historiques demande) {
        return historiquesRepository.save(demande);
    }




}

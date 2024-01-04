package ma.ensate.demandesetudiants.services;

import ma.ensate.demandesetudiants.components.Email;
import ma.ensate.demandesetudiants.entities.Relelvee_Prepa2_Prepa1;
import ma.ensate.demandesetudiants.entities.Relevee;
import ma.ensate.demandesetudiants.entities.Relevee_Gi2_Gi1;
import ma.ensate.demandesetudiants.repositories.DemandesRepository;
import ma.ensate.demandesetudiants.repositories.HistoriquesRepository;
import ma.ensate.demandesetudiants.repositories.ReleveeRepository;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


@Service
@Transactional
public class ReleveeDesNotesPDFGenerator extends DemandePDFGenerator {

    private final DemandesRepository demandesRepository;
    private final HistoriquesRepository historiquesRepository;
    private final ReleveeRepository releveeRepository;
    private final Email email;




    private Relevee relevee;
    private double seuil;
    private final String[][] data=new String[13][3];
    private  Field[] fields;
    private  Field[] superFields;


    private String filiere_doc;


    private PDDocument document;
    private String[] head;
    private  PDPage firstPage;
    private int pageHeigth;
    private  int pageWidth;
    private PDPageContentStream contentStream;
    private PDImageXObject pdImage;
    private PDImageXObject pdImage1;
    private int initX;
    private int initY;
    private int cellheight;
    private int cellWidth;
    private int colCount;
    private int rowCount;
    private int i,j;




    public ReleveeDesNotesPDFGenerator(DemandesRepository demandesRepository, HistoriquesRepository historiquesRepository, ReleveeRepository releveeRepository, Email email) {
        this.demandesRepository = demandesRepository;
        this.historiquesRepository = historiquesRepository;
        this.releveeRepository = releveeRepository;
        this.email = email;
    }


    @Override
    protected void createDocument() {

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


    }

    @Override
    protected void addHeader() {

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

    }

    @Override
    protected void addBody() {

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


}

    @Override
    protected void addFooter() {

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


    }

    @Override
    protected void saveDocument() {


        try {
            this.document.save("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\relevee des notes.pdf");
            this.email.envoie(this.demande.getEtudiant().getEmail(), "C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\relevee des notes.pdf");
            this.document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }




    }
}

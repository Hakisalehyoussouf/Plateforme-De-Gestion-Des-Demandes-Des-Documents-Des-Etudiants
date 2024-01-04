package ma.ensate.demandesetudiants.services;

import ma.ensate.demandesetudiants.components.Email;
import ma.ensate.demandesetudiants.repositories.DemandesRepository;
import ma.ensate.demandesetudiants.repositories.HistoriquesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;


@Service
@Transactional
public class AttestationDeReussitePDFGenerator extends DemandePDFGenerator {

    private final  DemandesRepository demandesRepository;
    private final HistoriquesRepository historiquesRepository;

    private final Email email;



    private PDDocument document;
    private String annee;
    private PDPage page;

    private  PDImageXObject pdImage;
    private PDImageXObject pdImage1;
    private PDPageContentStream contentStream;
    private PDFont font;
    private final String  anneeScolaire = "2022/20023";




    public AttestationDeReussitePDFGenerator(DemandesRepository demandesRepository, HistoriquesRepository historiquesRepository, Email email) {
        this.demandesRepository = demandesRepository;
        this.historiquesRepository = historiquesRepository;
        this.email = email;
    }


    @Override
    protected void createDocument() {

        // Create a new empty document
        this.document = new PDDocument();

        if(demande.getFiliere().equalsIgnoreCase("gi2")){
            this.annee="1 ere Année de Cycle ingénieur: Génie Inforamtique";
        }else if(demande.getFiliere().equalsIgnoreCase("2ap2")) {
            this.annee="1 ere Année de Cycle Préparatoires: 2AP1 ";
        }

        // Create a new blank page
        this.page = new PDPage();

        // Add the page to the document
        this.document.addPage(this.page);
    }



    @Override
    protected void addHeader() {

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


    }



    @Override
    protected void addBody() {


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
    }

    @Override
    protected void addFooter() {

        try {
            this.contentStream.beginText();
            this.contentStream.newLineAtOffset( 120, 300);
            this.contentStream.setFont(PDType1Font.HELVETICA, 16);
            this.contentStream.showText("fait le   : "+java.time.LocalDate.now());
            this.contentStream.endText();



            // Close the content stream
            contentStream.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void saveDocument() {

        try {
            // Save the document
            this.document.save("C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\attestation de reussite.pdf");
            email.envoie(this.demande.getEtudiant().getEmail(),"C:\\Formation\\Spring_Ecosystem\\demandes-etudiants\\src\\main\\resources\\static\\documents\\attestation de reussite.pdf");
            // Close the document
            this. document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

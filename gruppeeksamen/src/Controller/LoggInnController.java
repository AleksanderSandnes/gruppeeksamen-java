package Controller;

import Data.DataHandler;
import Data.NyBrukerDataHandler;
import Modell.Bruker;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggInnController {
    private static ObservableList<String> listeBrukenavn = FXCollections.observableArrayList();
    private static ObservableList<String> listePassord = FXCollections.observableArrayList();
    private static ObservableList<String> listeMail = FXCollections.observableArrayList();

    @FXML
    //Dette skal bort
    private ListView brukerListen, passordListen, mailListen, brukerListView;
    @FXML
    private TextField inputBrukernavn, tja;
    @FXML
    private PasswordField inputPassord;
    @FXML
    private Button btnLoggInn;
    @FXML
    private Button btnNyBruker;

    @FXML
    void nyttVindu(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/nyBrukerVindu.fxml"));
            Parent hoved = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Lag en ny bruker");
            stage.setScene(new Scene(hoved));
            stage.show();
        }
        catch (Exception e) {
            System.out.println("Det fungerer ikke");
        }
    }

    @FXML
    private void sjekkBruker(ActionEvent event) throws IOException {
        tja.setText(gaarGjennomListe(inputBrukernavn.getText(),inputPassord.getText(), event));
    }

    private String gaarGjennomListe(String brukernavn, String passord, ActionEvent event) throws IOException {
        ObservableList<String> brukernavnListe = DataHandler.hentData("src/brukere.csv", 0/*Brukernavn*/, listeBrukenavn);
        ObservableList<String> passordListe = DataHandler.hentData("src/brukere.csv", 1/*Passord*/, listePassord);
        String tja = "";
        for (int i = 0; i < brukernavnListe.size(); i++) {
            if (brukernavnListe.get(i).equals(brukernavn) && passordListe.get(i).equals(passord)) {
                tja = "Det gikk";
                Stage stage = (Stage) btnLoggInn.getScene().getWindow();
                stage.close();
                DataHandler.sendTilNyScene("../view/loggetInn.fxml");
                break;
            } else {
                //Brukeren skal få en feilmelding
                tja = "Dette gikk ikke så bra";
                break;
            }
        }
        return tja;
    }

    /*public void nyButtonClicked(ActionEvent actionEvent) {
        Bruker enBruker = new Bruker();

        boolean nyBrukerVellyket = MainJavaFX.getInstance().visNyBrukerVindu(enBruker);

        if (nyBrukerVellyket) {
            NyBrukerDataHandler.hentBrukerData().add(enBruker);
            brukerListView.getSelectionModel().select(enBruker);
        }
    }*/

    @FXML
    public void initialize() {
        //Dette skal bort
        brukerListen.setItems(DataHandler.hentData("src/brukere.csv",0/*Brukernavn*/, listeBrukenavn));
        passordListen.setItems(DataHandler.hentData("src/brukere.csv",1/*Passord*/, listePassord));
        mailListen.setItems(DataHandler.hentData("src/brukere.csv",2/*Mail*/, listeMail));
    }
}

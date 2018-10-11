package magic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML
    AnchorPane apMainPane;
    @FXML
    ListView<Person> lvLlistaCartes;
    @FXML
    MenuItem miGoThread;
    @FXML
    ProgressBar pbProgress;

    public void initialize(){

        // Personalitzem la CellFactory
        lvLlistaCartes.setCellFactory((list) -> {
            return new ListCell<Person>() {
                @Override
                public void updateItem(Person item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new ImageView(item.getImg()));
                        setText(item.getName());
                    }
                }
            };
        });

        // Afegir llista observable d'items
        ObservableList<Person> persons = FXCollections.observableArrayList(
                new Person("Julia", "icon.png"), new Person("Greta", "icon.png"));
        lvLlistaCartes.setItems(persons);

        // Afegir un item
        lvLlistaCartes.getItems().add(new Person("Pepi", "icon.png"));

        // Handle ListView selection changes with a listener
        lvLlistaCartes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                System.out.println("ListView Selection Changed (selected: " + newValue.getName() + ")");
            }
        );
    }

    public void miGoThreadOnAction(ActionEvent actionEvent) {
        Tasca2Pla tasca = new Tasca2Pla();
        tasca.setOnSucceeded(e -> {
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0.0F);
            System.out.println(tasca.getValue());
        });

        pbProgress.progressProperty().bind(tasca.progressProperty());

        Thread th = new Thread(tasca);
        th.setDaemon(true);
        th.start();
    }
}

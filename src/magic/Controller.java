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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML
    private AnchorPane apMainPane;
    @FXML
    private ListView<Person> lvLlistaCartes;
    @FXML
    private MenuItem miGoThread, miCancelThread;

    @FXML
    private ProgressBar pbProgress;

    private Thread th;

    // Classe per usar a la cellFactory de la ListView
    public class myListCell extends ListCell<Person>{
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
    }

    public void initialize(){

        // Personalitzem la CellFactory
        lvLlistaCartes.setCellFactory((list) -> { return new myListCell(); });

        // El mateix es podria fer amb una classe anònima en lloc de crear myListCell
        /*lvLlistaCartes.setCellFactory((list) -> {
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
        })*/;

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

        th = null;
    }

    public void miGoThreadOnAction(ActionEvent actionEvent) {
        Tasca2Pla tasca = new Tasca2Pla();
        tasca.setOnSucceeded(e -> {
            pbProgress.progressProperty().unbind();
            pbProgress.setProgress(0.0F);
            System.out.println(tasca.getValue());
        });

        pbProgress.progressProperty().bind(tasca.progressProperty());

        this.th = new Thread(tasca);
        this.th.setDaemon(true);
        this.th.start();
    }

    public void miCancelThreadOnAction(ActionEvent actionEvent) {
        if (this.th != null) {
            this.th.interrupt();
            this.th = null;
        }
    }

    /**
     * Event que gestiona un doble click sobre el ListView
     * @param mouseEvent
     */
    public void lvMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            System.out.println(lvLlistaCartes.getSelectionModel().getSelectedItem());
        }
    }


}

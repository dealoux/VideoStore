package ducle.videoStore.scenes;

import ducle.item.Item;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.NumberFormat;

public class ItemEditorController {
    @FXML
    private DialogPane itemEditorPane;
    @FXML
    private Label itemEditorOutputLabel;
    @FXML
    private TextField itemIdEditor;
    @FXML
    private TextField itemTitleEditor;
    @FXML
    private ComboBox<String> itemRentalTypeEditor;
    @FXML
    private ComboBox<String> itemGenreEditor;
    @FXML
    private ComboBox<String> itemLoanTypeEditor;
    @FXML
    private TextField itemStockEditor;
    @FXML
    private TextField itemFeeEditor;
    @FXML
    private ComboBox<String> itemRentalStatusEditor;

    public void initialize(){
        itemRentalTypeEditor.setItems(FXCollections.observableArrayList(Item.getRentalTypeList()));
        itemGenreEditor.setItems(FXCollections.observableArrayList(Item.getGenreList()));
        itemLoanTypeEditor.setItems(FXCollections.observableArrayList(Item.getLoanTypeList()));
        itemRentalStatusEditor.setItems(FXCollections.observableArrayList(Item.getRentalStatusList()));
    }

    public void setItem(Item item){
        itemIdEditor.textProperty().bindBidirectional(item.idProperty());
        itemTitleEditor.textProperty().bindBidirectional(item.titleProperty());
        itemRentalTypeEditor.valueProperty().bindBidirectional(item.rentalTypeProperty());
        itemGenreEditor.valueProperty().bindBidirectional(item.genreProperty());
        itemLoanTypeEditor.valueProperty().bindBidirectional(item.loanTypeProperty());
        itemStockEditor.textProperty().bindBidirectional(item.stockProperty(), NumberFormat.getCompactNumberInstance());
        itemFeeEditor.textProperty().bindBidirectional(item.feeProperty());
        itemRentalStatusEditor.valueProperty().bindBidirectional(item.rentalStatusProperty());
    }
}
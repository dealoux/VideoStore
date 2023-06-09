/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 202301
  Assessment: Project
  Author: Le Minh Duc
  ID: s4000577
  Created  date: 29/05/2023
  Acknowledgement: I have acknowledged that all the resources here are the course materials as well as my own experiences
  Purpose: Controller for rental-view, for customer to rent/return items, one of the tabs of the main customer-view
*/

package ducle.videoStore.scenes;

import ducle.videoStore.item.Item;
import ducle.videoStore.user.customer.Customer;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RentalController {
    @FXML
    private VBox rentalPane;
    @FXML
    private Label rentalOutput;
    private Customer customer;

    @FXML
    private BorderPane browseStorePane;
    private TableView<Item> itemTableStore;
    private ObservableList<Item> itemsStore;
    @FXML
    private TextField itemSearchStore;
    @FXML
    private Button rentButton;
    @FXML
    private ComboBox<String> storeDisplayComboBox;
    private List<String> displayComboList = new ArrayList<>(Arrays.asList("All", "Out-of-stock", "In-stock"));
    @FXML
    private BorderPane browseInventoryPane;
    @FXML
    private TableView<Item> itemTableInventory;
    private ObservableList<Item> itemsInventory;
    @FXML
    private TextField itemSearchInventory;
    @FXML
    private Button returnButton;
    @FXML
    private Button returnMultipleButton;
    @FXML
    private Button returnAllButton;

    @FXML
    private TableColumn<Item, String> itemIdInventory;
    @FXML
    private TableColumn<Item, String> itemTitleInventory;
    @FXML
    private TableColumn<Item, String> itemRentalTypeInventory;
    @FXML
    private TableColumn<Item, String> itemGenreInventory;
    @FXML
    private TableColumn<Item, String> itemLoanTypeInventory;
    @FXML
    private TableColumn<Item, Integer> itemFee;
    @FXML
    private TableColumn<Item, Integer> itemQuantity;

    @FXML
    public void initialize(){
        initBrowseStoreSection();
        initBrowseInventorySection();
    }

    private void initBrowseStoreSection(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("itemTable-view.fxml"));
            itemTableStore = fxmlLoader.load();
            browseStorePane.setCenter(itemTableStore);

        } catch (IOException e ){
            System.out.println(e);
        }

        storeDisplayComboBox.setItems(FXCollections.observableArrayList(displayComboList));
        storeDisplayComboBox.setValue("All");

        // Disable the delete button if nothing is selected
        rentButton.disableProperty().bind(Bindings.isNull(itemTableStore.getSelectionModel().selectedItemProperty()));
    }

    private void initBrowseInventorySection(){
        itemIdInventory.setCellValueFactory(new PropertyValueFactory<>("id"));
        itemTitleInventory.setCellValueFactory(new PropertyValueFactory<>("title"));
        itemRentalTypeInventory.setCellValueFactory(new PropertyValueFactory<>("rentalType"));
        itemGenreInventory.setCellValueFactory(new PropertyValueFactory<>("genre"));
        itemLoanTypeInventory.setCellValueFactory(new PropertyValueFactory<>("loanType"));
        itemFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Disable the return/return multiple buttons if nothing is selected
        returnButton.disableProperty().bind(Bindings.isNull(itemTableInventory.getSelectionModel().selectedItemProperty()));
        returnMultipleButton.disableProperty().bind(Bindings.isNull(itemTableInventory.getSelectionModel().selectedItemProperty()));
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
        refreshTables();
    }

    public void refreshStoreTable(){
        itemsStore = SceneUtilities.getObsItemList();
        SceneUtilities.itemFilter(itemsStore, itemSearchStore, itemTableStore);
    }

    public void refreshInventoryTable(){
        itemsInventory = FXCollections.observableArrayList(customer.getRentalList());
        SceneUtilities.itemFilter(itemsInventory, itemSearchInventory, itemTableInventory);
    }

    public void refreshTables(){
        refreshStoreTable();
        refreshInventoryTable();
    }

    @FXML
    protected void onRentButton(ActionEvent event){
        Item item = itemTableStore.getSelectionModel().getSelectedItem();

        rentalOutput.setText(customer.rent(item));
        refreshInventoryTable();
    }

    @FXML
    protected void onStoreDisplayComboBox(ActionEvent event){
        String result = "Displayed all ";

        switch (storeDisplayComboBox.getSelectionModel().getSelectedItem()){
            case "All":
                SceneUtilities.itemFilter(itemsStore, itemSearchStore, itemTableStore);
                result += "items";
                break;
            case "Out-of-stock":
                SceneUtilities.itemFilter(SceneUtilities.getObsOOSItemList(), itemSearchStore, itemTableStore);
                result += "out-of-stock items";
                break;
            case "In-stock":
                SceneUtilities.itemFilter(SceneUtilities.getObsISItemList(), itemSearchStore, itemTableStore);
                result += "in-stock items";
                break;
        }

        rentalOutput.setText(result);
    }

    @FXML
    protected void onReturnButton(ActionEvent event){
        Item item = itemTableInventory.getSelectionModel().getSelectedItem();

        rentalOutput.setText(customer.returnItem(item));
        refreshInventoryTable();
    }

    @FXML
    protected void onReturnMultipleButton(ActionEvent event){
        Item item = itemTableInventory.getSelectionModel().getSelectedItem();

        Optional<ButtonType> confirmation = SceneUtilities.confirmationDialog(
                "Confirm return of multiple copies",
                "Return confirmation",
                "Are you sure you would like to return every copies of " + item.print());

        if(confirmation.get() == ButtonType.OK){
            rentalOutput.setText(customer.returnItemMultiple(item));
            refreshInventoryTable();
        }
    }

    @FXML
    protected void onReturnAllButton(ActionEvent event){
        Optional<ButtonType> confirmation = SceneUtilities.confirmationDialog(
                "Confirm return of every items in inventory",
                "Return confirmation",
                "Are you sure you would like to return every items in your inventory?");

        if(confirmation.get() == ButtonType.OK){
            rentalOutput.setText(customer.returnAllItem());
            refreshInventoryTable();
        }
    }
}
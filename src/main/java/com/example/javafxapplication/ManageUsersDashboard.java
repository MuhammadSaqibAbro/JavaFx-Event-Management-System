package com.example.javafxapplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersDashboard {
    private static final String FILE_NAME = ManageUsersDashboard.class.getResource("/users.txt").getPath();

    public ManageUsersDashboard(Stage stage) {
        // Header
        Text header = new Text("Manage Users");
        header.setFont(Font.font("Arial", 36));
        header.setFill(Color.WHITE);
        StackPane headerPane = new StackPane(header);
        headerPane.setStyle("-fx-background-color: linear-gradient(to right, #4CAF50, #2196F3); -fx-padding: 20px;");

        // TableView for users
        TableView<User> userTable = new TableView<>();
        ObservableList<User> users = FXCollections.observableArrayList(loadUsers());

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        userTable.getColumns().addAll(usernameCol, passwordCol, roleCol);
        userTable.setItems(users);
        userTable.setStyle("-fx-background-color: #f4f4f4; -fx-table-cell-border-color: transparent;");
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Input fields
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        txtPassword.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList("Manager", "Event Coordinator", "Support Operator"));
        roleComboBox.setPromptText("Select Role");
        roleComboBox.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        // Buttons
        Button btnAddUser = createStyledButton("Add User", "#4CAF50");
        btnAddUser.setOnAction(e -> {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();
            String role = roleComboBox.getValue();

            if (!username.isEmpty() && !password.isEmpty() && role != null) {
                addUserToFile(username, password, role);
                users.setAll(loadUsers());
                txtUsername.clear();
                txtPassword.clear();
                roleComboBox.getSelectionModel().clearSelection();
            } else {
                showAlert("Error", "Please fill in all fields!");
            }
        });

        Button btnDeleteUser = createStyledButton("Delete User", "#F44336");
        btnDeleteUser.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                deleteUserFromFile(selectedUser.getUsername());
                users.setAll(loadUsers());
                txtUsername.clear();
                txtPassword.clear();
                roleComboBox.getSelectionModel().clearSelection();
            } else {
                showAlert("Error", "Please select a user to delete!");
            }
        });

        Button btnUpdateUser = createStyledButton("Update User", "#FF9800");
        btnUpdateUser.setOnAction(e -> {
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                // Get the updated details from the input fields
                String newUsername = txtUsername.getText().trim();
                String newPassword = txtPassword.getText().trim();
                String newRole = roleComboBox.getValue();

                if (!newUsername.isEmpty() && !newPassword.isEmpty() && newRole != null) {
                    // Update the selected user object with the new values
                    selectedUser.setUsername(newUsername);
                    selectedUser.setPassword(newPassword);
                    selectedUser.setRole(newRole);

                    // Update the user in the file
                    updateUserInFile(selectedUser);

                    // Refresh the table to reflect the updated user list
                    userTable.refresh(); // Refresh the table view to show updated values

                    // Clear input fields
                    txtUsername.clear();
                    txtPassword.clear();
                    roleComboBox.getSelectionModel().clearSelection();
                } else {
                    showAlert("Error", "Please fill in all fields!");
                }
            } else {
                showAlert("Error", "Please select a user to update!");
            }
        });



        Button btnBack = createStyledButton("Back", "#2196F3");
        btnBack.setOnAction(e -> new ManagerDashboard(stage));

        // Add listener for row selection
        userTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                txtUsername.setText(newValue.getUsername());
                txtPassword.setText(newValue.getPassword());
                roleComboBox.setValue(newValue.getRole());
            }
        });

        // Layout for input and buttons
        GridPane formLayout = new GridPane();
        formLayout.setVgap(15);
        formLayout.setHgap(10);
        formLayout.setAlignment(Pos.TOP_LEFT);

        formLayout.add(createStyledLabel("Username:"), 0, 0);
        formLayout.add(txtUsername, 1, 0);
        formLayout.add(createStyledLabel("Password:"), 0, 1);
        formLayout.add(txtPassword, 1, 1);
        formLayout.add(createStyledLabel("Role:"), 0, 2);
        formLayout.add(roleComboBox, 1, 2);

        VBox buttonBox = new VBox(15, btnAddUser, btnUpdateUser, btnDeleteUser, btnBack);
        buttonBox.setAlignment(Pos.TOP_LEFT);

        VBox leftPanel = new VBox(20);
        leftPanel.setAlignment(Pos.TOP_LEFT);
        leftPanel.setStyle("-fx-padding: 20px; -fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        leftPanel.getChildren().addAll(formLayout, buttonBox);

        // Main layout
        HBox mainLayout = new HBox(30);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb); -fx-padding: 30px;");
        mainLayout.getChildren().addAll(leftPanel, userTable);

        VBox root = new VBox(headerPane, mainLayout);

        Scene scene = new Scene(root, 900, 700);
        stage.setTitle("Manage Users Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        return button;
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        return label;
    }

    private List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    users.add(new User(parts[0], parts[1], parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private void addUserToFile(String username, String password, String role) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(username + "," + password + "," + role);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUserInFile(User user) {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                users.set(i, user); // Replace the old user with the updated one
                break;
            }
        }

        // Write the updated list of users back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User u : users) {
                writer.write(u.getUsername() + "," + u.getPassword() + "," + u.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteUserFromFile(String username) {
        List<User> users = loadUsers();
        users.removeIf(user -> user.getUsername().equals(username));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static class User {
        private String username;
        private String password;
        private String role;

        public User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}

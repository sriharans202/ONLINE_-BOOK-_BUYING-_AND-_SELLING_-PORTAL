import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
public class OnlineBookPortal extends Frame implements ActionListener {
private static final long serialVersionUID = 1L;
// Declare components
private TextField bookNameField, authorField, priceField, emailField, phoneField;
private Button addButton, viewButton, buyButton, sellButton;
private TextArea booksArea;
private Label bookNameLabel, authorLabel, priceLabel, emailLabel, phoneLabel,
statusLabel;
private ArrayList<String> booksForSale;
private static final String FILE_NAME = "books.txt";
// Constructor to set up the GUI components
public OnlineBookPortal() {
// Initialize books list
booksForSale = new ArrayList<>();
loadBooksFromFile(); // Load books from file when the app starts
// Set up frame properties
setTitle("Book Buying and Selling Portal");
setSize(500, 350);
setLayout(null); // Using absolute positioning for more control over placement
// Initialize labels
bookNameLabel = new Label("Book Name:");
authorLabel = new Label("Author:");
priceLabel = new Label("Price:");
emailLabel = new Label("Email:");
phoneLabel = new Label("Phone:");
statusLabel = new Label("");
// Initialize text fields
bookNameField = new TextField(20);
authorField = new TextField(20);
priceField = new TextField(10);
emailField = new TextField(20);
phoneField = new TextField(15);
// Initialize buttons
addButton = new Button("Add Book");
viewButton = new Button("View Books");
buyButton = new Button("Buy Book");
sellButton = new Button("Sell Book");
// Initialize TextArea for displaying books
booksArea = new TextArea(10, 30);
booksArea.setEditable(false);
// Set positions for components (using setBounds for absolute layout)
bookNameLabel.setBounds(20, 50, 80, 30);
bookNameField.setBounds(120, 50, 200, 30);
authorLabel.setBounds(20, 90, 80, 30);
authorField.setBounds(120, 90, 200, 30);
priceLabel.setBounds(20, 130, 80, 30);
priceField.setBounds(120, 130, 100, 30);
emailLabel.setBounds(20, 170, 80, 30);
emailField.setBounds(120, 170, 200, 30);
phoneLabel.setBounds(20, 210, 80, 30);
phoneField.setBounds(120, 210, 200, 30);
addButton.setBounds(20, 250, 100, 30);
viewButton.setBounds(130, 250, 100, 30);
buyButton.setBounds(240, 250, 100, 30);
sellButton.setBounds(380, 250, 100, 30); // Place the "Sell Book" button on the
right
booksArea.setBounds(20, 290, 460, 80);
statusLabel.setBounds(20, 380, 460, 30);
// Add components to the frame
add(bookNameLabel);
add(bookNameField);
add(authorLabel);
add(authorField);
add(priceLabel);
add(priceField);
add(emailLabel);
add(emailField);
add(phoneLabel);
add(phoneField);
add(addButton);
add(viewButton);
add(buyButton);
add(sellButton);
add(booksArea);
add(statusLabel);
// Add action listeners to buttons
addButton.addActionListener(this);
viewButton.addActionListener(this);
buyButton.addActionListener(this);
sellButton.addActionListener(this);
// Set frame visible
setVisible(true);
// Close the frame when the user clicks the close button
addWindowListener(new WindowAdapter() {
public void windowClosing(WindowEvent we) {
System.exit(0);
}
});
}
// Load books from a file
private void loadBooksFromFile() {
try (BufferedReader reader = new BufferedReader(new
FileReader(FILE_NAME))) {
String line;
while ((line = reader.readLine()) != null) {
booksForSale.add(line);
}
} catch (IOException e) {
System.out.println("No previous book records found, starting fresh.");
}
}
// Save books to the file
private void saveBooksToFile() {
try (BufferedWriter writer = new BufferedWriter(new
FileWriter(FILE_NAME))) {
for (String book : booksForSale) {
writer.write(book);
writer.newLine();
}
} catch (IOException e) {
statusLabel.setText("Error saving books to file.");
}
}
// ActionListener method to handle button clicks
public void actionPerformed(ActionEvent e) {
String command = e.getActionCommand();
// Check if email and phone number fields are filled
if (emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
statusLabel.setText("Email and Phone number are mandatory!");
return;
}
// Add a book to the list
if (command.equals("Add Book")) {
String bookName = bookNameField.getText();
String author = authorField.getText();
String priceText = priceField.getText();
if (bookName.isEmpty() || author.isEmpty() || priceText.isEmpty()) {
statusLabel.setText("Please fill all book details.");
return;
}
try {
double price = Double.parseDouble(priceText);
String book = "Book: " + bookName + " by " + author + " - Price: $" +
price;
booksForSale.add(book);
saveBooksToFile(); // Save books to file after adding
statusLabel.setText("Book added successfully.");
} catch (NumberFormatException ex) {
statusLabel.setText("Invalid price entered.");
}
// Clear text fields after adding
bookNameField.setText("");
authorField.setText("");
priceField.setText("");
}
// View the available books for sale
else if (command.equals("View Books")) {
if (booksForSale.isEmpty()) {
booksArea.setText("No books available for sale.");
} else {
booksArea.setText("");
for (String book : booksForSale) {
booksArea.append(book + "\n");
}
}
statusLabel.setText("Viewing books for sale.");
}
// Handle buying a book
else if (command.equals("Buy Book")) {
new BuyBookWindow(); // Open the new frame for buying a book
}
// Handle selling a book
else if (command.equals("Sell Book")) {
String bookName = bookNameField.getText();
String author = authorField.getText();
String priceText = priceField.getText();
if (bookName.isEmpty() || author.isEmpty() || priceText.isEmpty()) {
statusLabel.setText("Please fill all fields.");
return;
}
try {
double price = Double.parseDouble(priceText);
String book = "Book: " + bookName + " by " + author + " - Price: $" +

price;
booksForSale.add(book);
saveBooksToFile(); // Save books after selling
statusLabel.setText("Successfully listed the book for sale: " + bookName);
} catch (NumberFormatException ex) {
statusLabel.setText("Invalid price entered.");
}
// Clear text fields after selling
bookNameField.setText("");
authorField.setText("");
priceField.setText("");
}
}
// Inner class to create the "Buy Book" window
class BuyBookWindow extends Frame {
private static final long serialVersionUID = 1L;
private TextField bookNameField, authorField, priceField, phoneField;
private Button submitButton;
private Label bookNameLabel, authorLabel, priceLabel, phoneLabel,
statusLabel;
public BuyBookWindow() {
// Set up frame properties
setTitle("Buy Book");
setSize(400, 300);
setLayout(null); // Use null layout for absolute positioning
// Initialize labels and fields
bookNameLabel = new Label("Book Name:");
authorLabel = new Label("Author:");
priceLabel = new Label("Price:");
phoneLabel = new Label("Phone:");
statusLabel = new Label("");
bookNameField = new TextField(20);
authorField = new TextField(20);
priceField = new TextField(10);
phoneField = new TextField(15);
submitButton = new Button("Submit");
// Set positions for components (using setBounds for absolute layout)
bookNameLabel.setBounds(20, 50, 80, 30);
bookNameField.setBounds(120, 50, 200, 30);
authorLabel.setBounds(20, 90, 80, 30);
authorField.setBounds(120, 90, 200, 30);
priceLabel.setBounds(20, 130, 80, 30);
priceField.setBounds(120, 130, 100, 30);
phoneLabel.setBounds(20, 170, 80, 30);
phoneField.setBounds(120, 170, 200, 30);
submitButton.setBounds(150, 210, 100, 30);
statusLabel.setBounds(20, 250, 360, 30);
// Add components to the frame
add(bookNameLabel);
add(bookNameField);
add(authorLabel);
add(authorField);
add(priceLabel);
add(priceField);
add(phoneLabel);
add(phoneField);
add(submitButton);
add(statusLabel);
// Add action listener to submit button
submitButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
String bookName = bookNameField.getText();
String author = authorField.getText();
String priceText = priceField.getText();
String phone = phoneField.getText();
// Check if all fields are filled
if (bookName.isEmpty() || author.isEmpty() || priceText.isEmpty() ||
phone.isEmpty()) {
statusLabel.setText("Please fill all the details.");
return;
}

try {
double price = Double.parseDouble(priceText);
statusLabel.setText("Book purchased successfully: " + bookName);
} catch (NumberFormatException ex) {
statusLabel.setText("Invalid price entered.");
}
}
});
// Set frame visible
setVisible(true);
}
}
public static void main(String[] args) {
new OnlineBookPortal();
}
}

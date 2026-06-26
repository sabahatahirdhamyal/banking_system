package bankmanagmentsystem;
import java.util.Scanner;
public class BankManagmentSystem {
    static Scanner input = new Scanner(System.in);
    static String[] names = new String[10];
    static String[] CNICs = new String[10];
    static String[] passwords = new String[10];
    static String[] emails = new String[10];
    static double[] balances = new double[10];
    static String adminPIN = "123";
    static int totalUsers = 0;
    public static void main(String[] args) {
        boolean isRunning = true;
        int userIndex = -1;
        while (isRunning) {
            displayMainMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    startRegistration();
                    break;
                case 2:
                    userIndex = login();
                    break;
                case 3:
                    if(userIndex >= 0) showBalance(userIndex);
                    else System.out.println("Please login first!");
                    break;
                case 4:
                    if(userIndex >= 0) withdraw(userIndex);
                    else System.out.println("Please login first!");
                    break;
                case 5:
                    if(userIndex >= 0) deposit(userIndex);
                    else System.out.println("Please login first!");
                    break;
                case 6:
                    if(userIndex >= 0) applyForLoan();
                    else System.out.println("Please login first!");
                    break;
                case 7:
                    isRunning = false;
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
    static void displayMainMenu() {
        System.out.println("MAIN MENU: ");
        System.out.println("1- Register Account");
        System.out.println("2- Login");
        System.out.println("3- Show Balance");
        System.out.println("4- Withdraw Money");
        System.out.println("5- Deposit Money");
        System.out.println("6- Apply for Loan");
        System.out.println("7- Exit");
        System.out.print("Enter choice (1-7): ");
    }
    static int getChoice() {
        int choice = input.nextInt();
        input.nextLine(); 
        return choice;
    }
    static void startRegistration() {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        if(!validateName(name)) 
        return;
        System.out.print("Enter your CNIC (xxxxx-xxxxxxx-x): ");
        String cnic = input.nextLine();
        if(!validateCNIC(cnic))    
        return;
        System.out.print("Enter your password (password should be atleast 6 character long.): ");
        String password = input.nextLine();
        if(!validatePassword(password))
        return;
        System.out.print("Enter your email: ");
        String email = input.nextLine();
        if(!validateEmail(email))
        return;
        addUser(name, cnic, password, email);
        System.out.println("Registration successful!");
    }
    static boolean validateName(String name) {
        if(name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return false;
        }
        return true;
    }
    static boolean validateCNIC(String cnic) {
        if(cnic.length() != 15 || cnic.charAt(5) != '-' || cnic.charAt(13) != '-') {
            System.out.println("Invalid CNIC format.");
            return false;
        }
        return true;
    }
    static boolean validatePassword(String password) {
        if(password.length() < 6) {
            System.out.println("Password must be at least 6 characters.");
            return false;
        }
        return true;
    }
    static boolean validateEmail(String email) {
        if(!email.contains("@")) {
            System.out.println("Invalid email.");
            return false;
        }
        return true;
    }
    static void addUser(String name, String cnic, String password, String email) {
        names[totalUsers] = name;
        CNICs[totalUsers] = cnic;
        passwords[totalUsers] = password;
        emails[totalUsers] = email;
        balances[totalUsers] = 0.0;
        totalUsers++;
    }
    static int login() {
        System.out.println("LOGIN as:");
        System.out.println("1- USER");
        System.out.println("2- ADMIN");
        System.out.print("Enter choice: ");
        int choice = input.nextInt();
        input.nextLine(); 
        switch(choice) {
            case 1: return userLogin();
            case 2: return adminLogin();
            default:
                System.out.println("Invalid choice.");
                return -1;
        }
    }
    static int userLogin() {
        System.out.print("Enter your name: ");
        String name = input.nextLine();
        System.out.print("Enter your CNIC: ");
        String cnic = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();
        int index = getUserIndex(name, cnic, password);
        if(index != -1) {
            System.out.println("Login successful! Welcome " + names[index]);
            return index;
        } else {
            System.out.println("Invalid login.");
            return -1;
        }
    }
    static int adminLogin() {
        System.out.print("Enter admin PIN: ");
        String pin = input.nextLine();
        if(pin.equals(adminPIN)) {
            System.out.println("Admin login successful!");
            adminMenu();
            return -2;
        } else {
            System.out.println("Wrong admin PIN.");
            return -1;
        }
    }
    static int getUserIndex(String name, String cnic, String password) {
        for(int i = 0; i < totalUsers; i++) {
            if(names[i].equals(name) && CNICs[i].equals(cnic) && passwords[i].equals(password)) {
                return i;
            }
        }
        return -1;
    }
    static void showBalance(int userIndex) {
        System.out.printf("Your current balance: Rs %.2f", balances[userIndex]);
    }
    static void deposit(int userIndex) {
        System.out.print("Enter amount to deposit: ");
        double amount = input.nextDouble();
        input.nextLine();
        if(!validateDeposit(amount)) return;
        balances[userIndex] += amount;
        System.out.printf("Deposited Rs %.2f successfully!", amount);
    }
    static boolean validateDeposit(double amount) {
        if(amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        return true;
    }
    static void withdraw(int userIndex) {
        System.out.print("Enter amount to withdraw: ");
        double amount = input.nextDouble();
        input.nextLine();
        if(!validateWithdraw(amount, userIndex)) 
        return;
        balances[userIndex] -= amount;
        System.out.printf("Withdrawn Rs %.2f successfully!", amount);
    }
    static boolean validateWithdraw(double amount, int userIndex) {
        if(amount <= 0) {
            System.out.println("Amount must be positive.");
            return false;
        }
        if(amount > balances[userIndex]) {
            System.out.println("Insufficient balance.");
            return false;
        }
        return true;
    }
    static void applyForLoan() {
        System.out.print("Enter loan amount: ");
        double loanAmount = input.nextDouble();
        input.nextLine();
        if(!validateLoanAmount(loanAmount))
        return;
        System.out.print("Enter loan purpose: ");
        String purpose = input.nextLine();
        System.out.print("Enter loan duration (years): ");
        int years = input.nextInt();

        loanApprovalMessage(loanAmount, purpose, years);
    }
    static boolean validateLoanAmount(double amount) {
        if(amount <= 0) {
            System.out.println("Loan amount must be greater than 0.");
            return false;
        }
        if(amount > 50000) {
            System.out.println("Loan denied. Maximum allowed: Rs 50,000");
            return false;
        }
        return true;
    }
    static void loanApprovalMessage(double amount, String purpose, int years) {
        System.out.println("Loan request submitted successfully!");
        System.out.println("Amount: Rs " + amount);
        System.out.println("Purpose: " + purpose);
        System.out.println("Duration: " + years + " years");
    }
    static void adminMenu() {
        boolean adminRunning = true;
        while(adminRunning) {
            System.out.println("ADMIN MENU: ");
            System.out.println("1. View All Users");
            System.out.println("2. Check Total Bank Balance");
            System.out.println("3. Exit Admin Menu");
            System.out.print("Enter choice: ");
            int choice = input.nextInt();
            input.nextLine();
            switch(choice) {
                case 1: viewAllUsers(); break;
                case 2: checkTotalBalance(); break;
                case 3: 
                    System.out.println("Exiting admin menu");
                    adminRunning = false;
                    break;
                default: System.out.println("Invalid choice."); 
            }
        }
    }
    static void viewAllUsers() {
        if(totalUsers == 0) {
            System.out.println("No users registered yet.");
            return;
        }
        System.out.println("ALL REGISTERED USERS");
        for(int i = 0; i < totalUsers; i++) {
            printUserDetails(i);
        }
    }
    static void printUserDetails(int index) {
        System.out.println("Name: " + names[index] + ", CNIC: " + CNICs[index] +
                ", Email: " + emails[index] + ", Balance: Rs " + balances[index]);
    }
    static void checkTotalBalance() {
        double total = calculateTotalBalance();
        System.out.printf("Total bank balance of all users: Rs %.2f", total);
    }
    static double calculateTotalBalance() {
        double total = 0;
        for(int i = 0; i < totalUsers; i++) {
            total += balances[i];
        }
        return total;
    }
    static void exitProgram() {
        System.out.println("Thank you! Have a nice day :)");
    }
}


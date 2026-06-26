import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern; 
public class BankManagmentSystem { 

    // ---- defining objects and arraylists to store user data ---- 

    static Scanner input = new Scanner(System.in); 

    static ArrayList<String> names = new ArrayList<>(); 

    static ArrayList<String> CNIC = new ArrayList<>(); 

    static ArrayList<String> DateOfBirth = new ArrayList<>(); 

    static ArrayList<String> Email = new ArrayList<>(); 

    static ArrayList<String> phoneNumbers = new ArrayList<>(); 

    static ArrayList<Double> balances = new ArrayList<>(); 

    static ArrayList<String> userNames = new ArrayList<>(); 

    static ArrayList<String> passwords = new ArrayList<>(); 

    static ArrayList<String> SecurityQuestions = new ArrayList<>(); 

    static ArrayList<String> SecurityAnswers = new ArrayList<>(); 

    static ArrayList<Boolean> loginLocks = new ArrayList<>(); 

    static ArrayList<Integer> fixedInvestTimes = new ArrayList<>(); 

    static ArrayList<Double> InvestedAmount = new ArrayList<>(); 

    static ArrayList<String> linesOfRegisteredUsers = new ArrayList<>(); // Cache for file lines 

    static ArrayList<Integer> accountNumbers = new ArrayList<>(); 

    static ArrayList<ArrayList<String>> transactionHistory = new ArrayList<>(); // store each user's transactions 

    static ArrayList<Boolean> accountFrozen = new ArrayList<>(); 

    static ArrayList<Double> stocksInvestedAmount = new ArrayList<>(); // Principal invested 

    static ArrayList<Double> stocksCurrentValue = new ArrayList<>(); // Current market value 

    static String FileName = "JavaBankData.txt"; 

    static String counterFileName = "counter.txt"; 

    static int accountNumber = 100000; // starting point for account numbers 

    static String adminPIN = "123"; 

 

    //----- MAIN METHOD -----// 

    public static void main(String[] args) { 

 

        System.out.println("----Welcome to the Bank Management System!----"); 

 

        boolean isRunning = true; 

        readAllDataFromFile(); // Load existing user data from file 

        initializingNextAccountNumber(); // Load last account number counter 

 

        while (isRunning) { 

            int userIndex = -1; 

 

            displayInitialMenu(); 

 

            int initialChoice; 

            do { 

                initialChoice = getChoice(); 

                if (initialChoice < 1 || initialChoice > 3) { 

                    System.out.println("Invalid choice! Please enter 1, 2, or 3."); 

                } 

            } while (initialChoice < 1 || initialChoice > 3); 

 

            switch (initialChoice) { 

                case 1: 

                    startRegistration(); 

                    break; 

                case 2: 

                    userIndex = login(); 

 
if (userIndex >= 0) { 

                    
                        userMenu(userIndex); 

                    } else if (userIndex == -2) { 

                        // Admin menu is handled inside adminLogin() 

                    } 

                    userIndex = -1; 

                    break; 

                case 3: 

                    isRunning = false; 

                    exitProgram(); 

                    break; 

                default: 

                    break; 

            } 

        } 

    } // end of main method 

 

    //------- INITIAL MENU METHOD -------// 

    static void displayInitialMenu() { 

        System.out.println("\n------- MENU -------"); 

        System.out.println("1- Register New Account"); 

        System.out.println("2- Login to Account (User/Admin)"); 

        System.out.println("3- Exit Program"); 

        System.out.print("Enter choice (1-3): "); 

    } 

 

    // ------- USER MENU METHOD ------- // 

    static void userMenu(int userIndex) { 

        boolean isLoggedIn = true; 

        while (isLoggedIn) { 

            displayMainMenu(); 

 

            int choice; 

            do { 

                choice = getChoice(); 

                if (choice < 1 || choice > 9) { // Max choice is 9 for Logout 

                    System.out.println("Invalid choice! Please enter a number between 1 and 9."); 

                } 

            } while (choice < 1 || choice > 9); 

 

            switch (choice) { 

                case 1: 

                    showBalance(userIndex); 

                    break; 

                case 2: 

                    withdraw(userIndex); 

                    break; 

                case 3: 

                    deposit(userIndex); 

                    break; 

                case 4: 

                    investmentMenu(userIndex); 

                    break; 

                case 5: 

                    applyForLoan(); 

                    break; 

                case 6: 

                    transferFunds(userIndex); 

                    break; 

                case 7: 

                    payUtilityBills(userIndex); 

                    break; 

                case 8: 

                    userSettingsMenu(userIndex); // call new settings menu 

                    break; 

                case 9: 

                    System.out.println("Logout successful. Returning to initial menu."); 

                    isLoggedIn = false; 

                    break; 

                default: 

                    break; 

            } 

            if (!isLoggedIn) { 

                // IMPORTANT: Save all changes when the user logs out 

                writeAllDataToFile(); 

            } 

        } 

    } 

 

    //------ MAIN MENU DISPLAY METHOD ------// 

    static void displayMainMenu() { 

        System.out.println("\n----- ACCOUNT MAIN MENU -----"); 

        System.out.println("1- Show Balance"); 

        System.out.println("2- Withdraw Money"); 

        System.out.println("3- Deposit Money"); 

        System.out.println("4- Investment"); 

        System.out.println("5- Apply for Loan"); 

        System.out.println("6- Transfer Funds"); 

        System.out.println("7- Pay utility Bills"); 

        System.out.println("8- Open My Settings"); 

        System.out.println("9- Logout"); 

        System.out.print("Enter choice (1-9): "); 

    } 

 

    // ----- INPUT VALIDATION METHOD ----- // 

    static int getChoice() { 

        int choice; 

        while (true) { 

            try { 

                choice = input.nextInt(); 

                input.nextLine(); 

                return choice; 

            } catch (InputMismatchException e) { 

                System.out.print("Invalid input. Please enter a valid number (integer): "); 

                input.nextLine(); 

            } 

        } 

    } 

 

    // ----- REGISTRATION METHOD ----- // 

    static void startRegistration() { 

        System.out.println("\n----- ACCOUNT REGISTRATION -----"); 

 

        String name; 

        do { 

            System.out.print("Enter your name: "); 

            name = input.nextLine(); 

            if (!validateName(name)) { 

                // Validation message is inside validateName 

            } 

        } while (!validateName(name)); 

 

        String cnic; 

        do { 

            System.out.print("Enter your CNIC (xxxxx-xxxxxxx-x): "); 

            cnic = input.nextLine(); 

            if (!validateCNIC(cnic)) { 

                // Validation message is inside validateCNIC 

            } 

        } while (!validateCNIC(cnic)); 

 

        if (cnicAlreadyExists(cnic)) { 

            System.out.println("CNIC already registered. Please use a different CNIC."); 

            return; 

        } 

 

        String dateOfBirth; 

        do { 

            System.out.print("Enter your date of birth (dd/mm/yyyy): "); 

            dateOfBirth = input.nextLine(); 

            if (!validateDateOfBirth(dateOfBirth)) { 

                System.out.println("Invalid date format. Please use dd/mm/yyyy."); 

            } 

        } while (!validateDateOfBirth(dateOfBirth)); 

 

        // Age verification for being at least 18 years old 

        // NOTE: This assumes current year is 2025 based on the original code's logic 

        int age = 2025 - Integer.parseInt(dateOfBirth.substring(6, 10)); 

        if (age < 18) { 

            System.out.println("You must be at least 18 years old to register."); 

            return; 

        } 

 

        String phoneNumber; 

        do { 

            System.out.print("Enter your phone number: "); 

            phoneNumber = input.nextLine(); 

            if (!validatePhoneNumber(phoneNumber)) { 

                // Validation message is inside validatePhoneNumber 

            } 

        } while (!validatePhoneNumber(phoneNumber)); 

 

        String email; 

        do { 

            System.out.print("Enter your email: "); 

            email = input.nextLine(); 

            if (!validateEmail(email)) { 

                // Validation message is inside validateEmail 

            } 

        } while (!validateEmail(email)); 

 

        System.out.println(" Please create a unique user name and password "); 

 

        // Creating unique user name and password 

        String userName; 

        while (true) { 

            System.out.print("Enter user name: "); 

            userName = input.nextLine(); 

            if (userNames.contains(userName)) { 

                System.out.println("User name already taken. Please choose another one."); 

            } else { 

                break; 

            } 

        } 

 

        String password; 

        while (true) { 

            System.out.print("Enter password: "); 

            password = input.nextLine(); 

            boolean isStrongEnough = (password.length() >= 6) && !password.trim().isEmpty() 

                    && (password.contains("@") || password.contains("#") || password.contains("$") || password.contains("%")); 

            if (!isStrongEnough) { 

                System.out.println("Error: Password must be at least 6 characters long and contain @, #, $, or %."); 

                continue; 

            } 

            if (passwords.contains(password)) { 

                System.out.println("Error: Password already taken. Please choose another one."); 

                continue; 

            } 

            break; 

        } 

 

        System.out.println("Set up a security question for account recovery."); 

        String securityQuestion; 

        System.out.print("Enter a security question (for unauthorized login attempts): "); 

        securityQuestion = input.nextLine(); 

 

        String securityAnswer; 

        System.out.print("Enter the answer to your security question: "); 

        securityAnswer = input.nextLine(); 

 

        addUser(name, cnic, dateOfBirth, phoneNumber, email, userName, password, securityQuestion, securityAnswer); 

        System.out.println("Registration successful! "); 

 

    } 

 

    // ----- VALIDATION METHODS ----- // 

    static boolean validateName(String name) { 

        if (name.trim().isEmpty()) { 

            System.out.println("Name cannot be empty."); 

            return false; 

        } 

        return true; 

    } 

 

    static boolean validateCNIC(String cnic) { 

        if (cnic.length() != 15 || cnic.charAt(5) != '-' || cnic.charAt(13) != '-') { 

            System.out.println("Invalid CNIC format. Expected format: xxxxx-xxxxxxx-x"); 

            return false; 

        } 

        return true; 

    } 

 

    static boolean cnicAlreadyExists(String cnic) { 

        return CNIC.contains(cnic); 

    } 

 

    static boolean validateDateOfBirth(String dateOfBirth) { 

        if (dateOfBirth == null || dateOfBirth.length() != 10 || dateOfBirth.charAt(2) != '/' || dateOfBirth.charAt(5) != '/') { 

            return false; 

        } 

        // Use a simple regex to check if characters are digits where expected 

        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", dateOfBirth)) { 

            return false; 

        } 

        try { 

            // Extract Day, Month, Year 

            int day = Integer.parseInt(dateOfBirth.substring(0, 2)); 

            int month = Integer.parseInt(dateOfBirth.substring(3, 5)); 

            int year = Integer.parseInt(dateOfBirth.substring(6, 10)); 

            //  Month and Day Basic Range Check 

            if (month < 1 || month > 12) { 

                return false; 

            } 

            if (day < 1 || day > 31) { 

                return false; 

            } 

            // Limit year to a reasonable range 

            if (year < 1900 || year > 2100) { // Example range 

                return false; 

            } 

            // 3. Month-Specific Day Check 

            if (month == 4 || month == 6 || month == 9 || month == 11) { 

                if (day > 30) { 

                    return false; 

                } 

            } else if (month == 2) { 

 

                boolean isLeap = (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0); 

 

                if (isLeap) { 

                    if (day > 29) { 

                        return false; 

                    } 

                } else { 

                    if (day > 28) { 

                        return false; 

                    } 

                } 

            } 

            return true; 

 

        } catch (NumberFormatException e) { 

            System.out.println("Invalid date format."); 

            return false; 

        } 

    } 

 

    static boolean validatePhoneNumber(String phoneNumber) { 

 

        if (phoneNumber.length() < 11 || !phoneNumber.startsWith("0")) { 

            System.out.println("Phone number must be at least 11 digits and start with 0."); 

            return false; 

        } 

        return true; 

    } 

 

    static boolean validateEmail(String email) { 

        if (!email.contains("@")) { 

            System.out.println("Invalid email."); 

            return false; 

        } 

        return true; 

    } 

 

    // ----- ADD USER METHOD ----- // 

    static void addUser(String name, String cnic, String dateOfBirth, String phoneNumber, String email, String userName, String password, String securityQuestion, String securityAnswer) { 

        names.add(name); 

        CNIC.add(cnic); 

        DateOfBirth.add(dateOfBirth); 

        phoneNumbers.add(phoneNumber); 

        Email.add(email); 

        userNames.add(userName); 

        passwords.add(password); 

        SecurityQuestions.add(securityQuestion); 

        SecurityAnswers.add(securityAnswer); 

        balances.add(0.0); 

        loginLocks.add(false); 

        fixedInvestTimes.add(0); 

        InvestedAmount.add(0.0); 

        accountNumbers.add(accountNumber); 

        transactionHistory.add(new ArrayList<>()); 

        accountFrozen.add(false); 

        stocksInvestedAmount.add(0.0); // Initialize new stock fields 

        stocksCurrentValue.add(0.0); 

 

        accountNumber++; // Increment account number for the next user 

        writeAllDataToFile(); 

    } 

 

    // --- METHOD FOR SAVING ALL DATA FROM ALL ARRAYLISTS --- 

    public static void writeAllDataToFile() { 

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("JavaBankData.txt", false))) { 

            for (int i = 0; i < names.size(); i++) { 

 

                // Ensure all 17 pieces of data are written 

                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%.2f,%d,%b,%d,%.2f,%.2f,%b", 

                        names.get(i), 

                        CNIC.get(i), 

                        DateOfBirth.get(i), 

                        phoneNumbers.get(i), 

                        Email.get(i), 

                        userNames.get(i), 

                        passwords.get(i), 

                        SecurityQuestions.get(i), 

                        SecurityAnswers.get(i), 

                        balances.get(i), 

                        InvestedAmount.get(i), 

                        fixedInvestTimes.get(i), 

                        loginLocks.get(i), 

                        accountNumbers.get(i), 

                        stocksInvestedAmount.get(i), 

                        stocksCurrentValue.get(i), 

                        accountFrozen.get(i) 

                ); 

                writer.write(line); 

                writer.newLine(); 

            } 

        } catch (IOException e) { 

            System.out.println("An error occurred while saving user data to file: " + e.getMessage()); 

        } 

        writeNextAccountNumber(); 

    } 

 

    // --- LOADING METHODS --- 

 

    // Utility to clear all lists before loading fresh data 

    static void clearAllLists() { 

        names.clear(); 

        CNIC.clear(); 

        DateOfBirth.clear(); 

        Email.clear(); 

        phoneNumbers.clear(); 

        balances.clear(); 

        userNames.clear(); 

        passwords.clear(); 

        SecurityQuestions.clear(); 

        SecurityAnswers.clear(); 

        loginLocks.clear(); 

        fixedInvestTimes.clear(); 

        InvestedAmount.clear(); 

        accountNumbers.clear(); 

        transactionHistory.clear(); 

        accountFrozen.clear(); 

        stocksInvestedAmount.clear(); 

        stocksCurrentValue.clear(); 

        linesOfRegisteredUsers.clear(); 

    } 

 

    static void readAllDataFromFile() { 

        clearAllLists(); 

        try (BufferedReader reader = new BufferedReader(new FileReader("JavaBankData.txt"))) { 

            String line; 

            while ((line = reader.readLine()) != null) { 

                linesOfRegisteredUsers.add(line); 

            } 

        } catch (FileNotFoundException e) { 

            System.out.println("Data file not found. Starting with a fresh user database."); 

        } catch (IOException e) { 

            System.out.println("An error occurred while reading user data from file: " + e.getMessage()); 

        } 

        loadDataIntoLists(); 

    } 

     

    static void loadDataIntoLists() { 

        for (String line : linesOfRegisteredUsers) { 

            try { 

                String[] parts = line.split(","); 

                // Check if the line has the minimum expected number of parts (17) 

                if (parts.length >= 17) {  

                    names.add(parts[0].trim()); 

                    CNIC.add(parts[1].trim()); 

                    DateOfBirth.add(parts[2].trim()); 

                    phoneNumbers.add(parts[3].trim()); 

                    Email.add(parts[4].trim()); 

                    userNames.add(parts[5].trim()); 

                    passwords.add(parts[6].trim()); 

                    SecurityQuestions.add(parts[7].trim()); 

                    SecurityAnswers.add(parts[8].trim()); 

                    balances.add(Double.parseDouble(parts[9].trim())); 

                    InvestedAmount.add(Double.parseDouble(parts[10].trim())); 

                    fixedInvestTimes.add(Integer.parseInt(parts[11].trim())); 

                    loginLocks.add(Boolean.parseBoolean(parts[12].trim())); 

                    accountNumbers.add(Integer.parseInt(parts[13].trim())); 

                    stocksInvestedAmount.add(Double.parseDouble(parts[14].trim())); 

                    stocksCurrentValue.add(Double.parseDouble(parts[15].trim())); 

                    accountFrozen.add(Boolean.parseBoolean(parts[16].trim())); 

                    transactionHistory.add(new ArrayList<>());  

 

                } else { 

                    System.out.println("Warning: Skipping malformed line with insufficient data: " + line); 

                } 

            } catch (NumberFormatException e) { 

                System.out.println("Error parsing numeric/boolean data in file line: " + line); 

            } 

        } 

    } 

     

    static void initializingNextAccountNumber() { 

        try (BufferedReader reader = new BufferedReader(new FileReader(counterFileName))) { 

            String line = reader.readLine(); 

            if (line != null) { 

                int lastAccountNum = Integer.parseInt(line.trim()); 

                if (!accountNumbers.isEmpty()) { 

                    int highestCurrent = 0; 

                    for (int accNum : accountNumbers) { 

                        if (accNum > highestCurrent) { 

                            highestCurrent = accNum; 

                        } 

                    } 

                    accountNumber = Math.max(lastAccountNum, highestCurrent + 1); 

                } else { 

                    accountNumber = lastAccountNum; 

                } 

            } 

        } catch (FileNotFoundException e) { 

            System.out.println("Account number counter file not found. Starting from default: " + accountNumber); 

        } catch (IOException | NumberFormatException e) { 

            System.out.println("Error reading account number counter. Starting from default: " + accountNumber); 

        } 

    } 

 

    static void writeNextAccountNumber() { 

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(counterFileName, false))) { 

            // Write the next account number that should be assigned (current highest + 1) 

            writer.write(String.valueOf(accountNumber)); 

        } catch (IOException e) { 

            System.out.println("ERROR: Error saving next account number to counter file: " + e.getMessage()); 

        } 

    } 

     

    // ----- LOGIN METHOD ----- // 

    static int login() { 

        System.out.println("\n--- LOGIN as: ---"); 

        System.out.println("1- USER"); 

        System.out.println("2- ADMIN"); 

        System.out.print("Enter choice: "); 

        int choice; 

        do { 

            choice = getChoice(); 

            if (choice < 1 || choice > 2) { 

                System.out.println("Invalid login choice! Please enter 1 for USER or 2 for ADMIN: "); 

            } 

        } while (choice < 1 || choice > 2); 

 

        switch (choice) { 

            case 1: 

                return userLogin(); 

            case 2: 

                return adminLogin(); 

            default: 

                return -1; 

        } 

    } 

 

    // ----- USER AND ADMIN LOGIN METHODS ----- // 

    static int userLogin() { 

 

        System.out.println("---- User Login ----"); 

 

        System.out.print("Enter your user name: "); 

        String nameInput = input.nextLine(); 

 

        int userIndex = getUserIndex(nameInput.trim()); 

 

        if (userIndex == -1) { 

            System.out.println("User name not found. Please register first or enter the correct username."); 

            return -1; 

        } 

 

        if (loginLocks.get(userIndex)) { 

            System.out.println("Your account is currently locked. Please contact support or try account recovery."); 

            return -1; 

        } 

 

        int passwordFailures = 0; 

 

        // Loop executes a maximum of 3 times. 

        while (passwordFailures <= 3) { 

            System.out.print("Enter your password: "); 

            String password = input.nextLine(); 

 

            if (passwordCheck(password, userIndex)) { 

                System.out.println("Login successful! Welcome " + names.get(userIndex)); 

                loginLocks.set(userIndex, false); 

                return userIndex; 

            } else { 

                passwordFailures++; // Increment failure count 

 

                if (passwordFailures < 3) { 

                    int attemptsLeft = 3 - passwordFailures; 

                    System.out.println("Incorrect password. " + attemptsLeft + " attempt(s) remaining before account lock."); 

                } 

            } 

        } 

 

        // --- LOCKING LOGIC: This code executes ONLY if the loop completed 3 failures. --- 

        System.out.println("Too many failed login attempts (3). Your account is now locked."); 

 

        loginLocks.set(userIndex, true); 

        writeAllDataToFile(); 

        loginLock(userIndex, nameInput.trim(), ""); 

 

        return -1; 

    } 

 

    //------- LOGIN LOCK METHOD -------// 

    static void loginLock(int userIndex, String name, String password) { 

 

        System.out.println("Your account is locked due to too many failed login attempts.\n Do you want to unlock it? (yes/no): "); 

        String response = input.nextLine().trim().toLowerCase(); 

        if (response.equalsIgnoreCase("yes")) { 

            // Note: The original passwordChange method signature includes unused parameters (password, loginlock) 

            // It has been kept consistent with your provided structure. 

            passwordChange(passwords.get(userIndex), SecurityAnswers.get(userIndex), userIndex, SecurityQuestions.get(userIndex), loginLocks.get(userIndex)); 

        } else { 

            return; 

        } 

    } 

 

    static int passwordChange(String password, String securityAnswer, int userIndex, String securityQuestion, Boolean loginlock) { 

 

        System.out.print("Answer your security question to unlock your account: "); 

        System.out.println(securityQuestion); 

        String answer = input.nextLine(); 

        if (answer.equals(securityAnswer)) { 

            String newPassword; 

            do { 

                System.out.print("Enter new password (must be strong): "); 

                newPassword = input.nextLine(); 

                boolean isStrongEnough = (newPassword.length() >= 6) && 

                        !newPassword.trim().isEmpty() && 

                        (newPassword.contains("@") || newPassword.contains("#") || newPassword.contains("$") || newPassword.contains("%")); 

                if (!isStrongEnough) { 

                    System.out.println("Error: Password must be at least 6 characters long and contain @, #, $, or %."); 

                } else if (passwords.contains(newPassword)) { 

                    System.out.println("Error: Password already taken. Please choose another one."); 

                } else { 

                    break; 

                } 

            } while (true); 

 

            passwords.set(userIndex, newPassword); 

            loginLocks.set(userIndex, false); 

            writeAllDataToFile(); // Save new password and unlocked status 

            System.out.println("Your account has been unlocked and password updated."); 

            return userIndex; 

        } else { 

            System.out.println("Incorrect answer. Cannot unlock account.\nplease visit the java bank near you to unlock your account."); 

            return -1; 

        } 

    } 

    // ----- ADMIN LOGIN METHOD ----- // 

    static int adminLogin() { 

        System.out.print("Enter admin PIN: "); 

        String pin = input.nextLine(); 

        if (pin.equals(adminPIN)) { 

            System.out.println("Admin login successful!"); 

            adminMenu(); 

            return -2; 

        } else { 

            System.out.println("Wrong admin PIN."); 

            return -1; 

        } 

    } 

 

    // ----- GET USER INDEX METHOD ----- // 

    static int getUserIndex(String userName) { 

        for (int i = 0; i < names.size(); i++) { 

            if (userNames.get(i).equals(userName)) { 

                return i; 

            } 

        } 

        return -1; 

    } 

    static boolean passwordCheck(String password, int userIndex) { 

        return passwords.get(userIndex).equals(password); 

    } 

     

    // ----- ACCOUNT ACTIONS ----- // 

     

    static void showBalance(int userIndex) { 

        System.out.printf("Your current balance: Rs %.2f\n", balances.get(userIndex)); 

    } 

     

    static void deposit(int userIndex) { 

        System.out.print("Enter amount to deposit: "); 

        double amount; 

        try { 

            amount = input.nextDouble(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

        input.nextLine(); 

 

        if (!validateDeposit(amount)) return; 

 

        double newBalance = balances.get(userIndex) + amount; 

        balances.set(userIndex, newBalance); 

        transactionHistory.get(userIndex).add("Deposited Rs " + String.format("%.2f", amount)); 

        writeAllDataToFile(); // Save the updated balance 

 

        System.out.printf("Deposited Rs %.2f successfully!\n", amount); 

    } 

 

    static boolean validateDeposit(double amount) { 

        if (amount <= 0) { 

            System.out.println("Deposit amount must be positive."); 

            return false; 

        } 

        return true; 

    } 

 

    static void withdraw(int userIndex) { 

         

        if (accountFrozen.get(userIndex)) { 

            System.out.println("Account is frozen. Cannot withdraw money."); 

            return; 

        } 

         

        System.out.print("Enter amount to withdraw: "); 

        double amount; 

         

        try { 

            amount = input.nextDouble(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

        input.nextLine(); 

 

        if (!validateWithdraw(amount, userIndex)) return; 

 

        double newBalance = balances.get(userIndex) - amount; 

        balances.set(userIndex, newBalance); 

        transactionHistory.get(userIndex).add("Withdrew Rs " + String.format("%.2f", amount)); 

        writeAllDataToFile(); // Save the updated balance 

 

        System.out.printf("Withdrawn Rs %.2f successfully!\n", amount); 

    } 

 

    static boolean validateWithdraw(double amount, int userIndex) { 

        if (amount <= 0) { 

            System.out.println("Withdrawal amount must be positive."); 

            return false; 

        } 

        if (amount > balances.get(userIndex)) { 

            System.out.println("Insufficient balance."); 

            return false; 

        } 

        return true; 

    } 

     

    // ----- USER SETTINGS MENU ----- // 

 

    static void userSettingsMenu(int userIndex) { 

        boolean inSettings = true; 

        while (inSettings) { 

            System.out.println("\n--- USER SETTINGS ---"); 

            System.out.println("1. Change Password"); 

            System.out.println("2. Freeze/Unfreeze Account"); 

            System.out.println("3. View Transaction History"); 

            System.out.println("4. Return to Main Menu"); 

            System.out.print("Enter choice: "); 

            int choice = getChoice(); 

 

            switch (choice) { 

                case 1: 

                    changePassword(userIndex); 

                    break; 

                case 2: 

                    toggleAccountFreeze(userIndex); 

                    break; 

                case 3: 

                    viewTransactionHistory(userIndex); 

                    break; 

                case 4: 

                    inSettings = false; 

                    break; 

                default: 

                    System.out.println("Invalid choice! Please enter 1-4."); 

                    break; 

            } 

            writeAllDataToFile(); // Save setting changes 

        } 

    } 

     

    static void changePassword(int userIndex) { 

        System.out.print("Enter your current password: "); 

        String current = input.nextLine(); 

        if (!passwords.get(userIndex).equals(current)) { 

            System.out.println("Incorrect password!"); 

            return; 

        } 

 

        System.out.print("Enter new password: "); 

        String newPassword = input.nextLine(); 

        boolean isStrongEnough = (newPassword.length() >= 6) && 

                (newPassword.contains("@") || newPassword.contains("#") || 

                        newPassword.contains("$") || newPassword.contains("%")); 

        if (!isStrongEnough) { 

            System.out.println("Password must be at least 6 characters and contain @, #, $, or %."); 

            return; 

        } 

        passwords.set(userIndex, newPassword); 

        System.out.println("Password changed successfully!"); 

    } 

 

    static void toggleAccountFreeze(int userIndex) { 

        boolean frozen = accountFrozen.get(userIndex); 

        if (!frozen) { 

            accountFrozen.set(userIndex, true); 

            System.out.println("Your account is now frozen. You cannot withdraw or invest until unfrozen."); 

        } else { 

            accountFrozen.set(userIndex, false); 

            System.out.println("Your account is now active again."); 

        } 

    } 

    static void viewTransactionHistory(int userIndex) { 

        ArrayList<String> transactions = transactionHistory.get(userIndex); 

        if (transactions.isEmpty()) { 

            System.out.println("No transactions yet."); 

        } else { 

            System.out.println("--- TRANSACTION HISTORY ---"); 

            for (String t : transactions) { 

                System.out.println(t); 

            } 

        } 

    } 

 

    // ----- INVESTMENT METHODS ----- // 

     

    static void investmentMenu(int userIndex) { 

        System.out.println("\n--- INVESTMENT OPTIONS ---"); 

        System.out.println("1. Fixed Deposit (7% return)"); 

        System.out.println("2. Stocks (High risk, high return)"); 

        System.out.println("3. Mutual Funds (Medium risk)"); 

        System.out.println("4. Back to Main Menu"); 

        System.out.print("Enter choice: "); 

        int choice; 

        if (accountFrozen.get(userIndex)) { 

            System.out.println("Account is frozen. Cannot withdraw or invest."); 

            return; 

        } 

        do { 

            choice = getChoice(); 

            if (choice < 1 || choice > 4) { 

                System.out.println("Invalid choice! Please enter 1-4."); 

            } 

        } while (choice < 1 || choice > 4); 

 

        if (choice == 4) { 

            return; // Go back 

        } 

         

        System.out.printf("Your current balance: Rs %.2f\n", balances.get(userIndex)); 

         

        double initialAmount = 0.0; 

        if (choice == 3) { 

             System.out.print("Enter amount to invest: Rs "); 

            try { 

                initialAmount = input.nextDouble(); 

                input.nextLine(); 

            } catch (InputMismatchException e) { 

                System.out.println("Invalid input. Please enter a valid number."); 

                input.nextLine(); 

                return; 

            } 

 

            if (initialAmount <= 0) { 

                System.out.println("Amount must be positive!"); 

                return; 

            } 

 

            if (initialAmount > balances.get(userIndex)) { 

                System.out.println("Insufficient balance!"); 

                return; 

            } 

             

            // Deduct once for Mutual Funds 

            double newBalance = balances.get(userIndex) - initialAmount; 

            balances.set(userIndex, newBalance); 

             

            System.out.printf("Amount deducted: Rs %.2f\n", initialAmount); 

            System.out.printf("Remaining balance: Rs %.2f\n", newBalance); 

        } 

 

 

        switch (choice) { 

            case 1: 

                System.out.println("\n--- FIXED DEPOSIT ---"); 

                fixedDepositInvestment(userIndex);  

                break; 

 

            case 2: 

                StocksMenu(userIndex); 

                break; 

 

            case 3: 

                System.out.println("\n--- MUTUAL FUNDS ---"); 

                System.out.printf("Invested Rs %.2f in Mutual Funds\n", initialAmount); 

                System.out.println("Average return: 10-12% per year"); 

                System.out.println("Professional fund managers will manage your money"); 

                break; 

        } 

        

        writeAllDataToFile(); 

        System.out.println("Investment process finished."); 

    } 

     

    // Modified Fixed Deposit logic to prompt for initial investment within the method 

    static void fixedDepositInvestment(int userIndex) { 

        double amount = 0; 

         

        // Handle the first investment cycle logic properly  

        System.out.printf("Total Invested Amount till now: Rs %.2f\n", InvestedAmount.get(userIndex)); 

         

        int currentCount = fixedInvestTimes.get(userIndex); 

 

        // If the cycle is already complete, liquidate first 

        if (currentCount == 10) { 

            liquidateFixedDeposit(userIndex); 

            return; 

        } 

         

        // Start the continuous investment loop 

        for (int i = currentCount + 1; i <= 10; i++) { 

             

            do { 

                System.out.println("Investment " + i + " of 10"); 

                System.out.printf("Total Invested Amount till now: Rs %.2f\n", InvestedAmount.get(userIndex)); 

                System.out.print("Enter amount to invest in Fixed Deposit: "); 

 

                try { 

                    amount = input.nextDouble(); 

                } catch (java.util.InputMismatchException e) { 

                    System.out.println("Invalid input. Please enter a valid number."); 

                    input.nextLine(); 

                    amount = 0; 

                    continue; 

                } 

                input.nextLine(); 

 

                if (amount <= 0) { 

                    System.out.println("Investment amount must be positive."); 

                } else if (amount > balances.get(userIndex)) { 

                    System.out.println("Insufficient balance for this investment. Current Balance: Rs " + String.format("%.2f", balances.get(userIndex))); 

                } 

            } while (amount <= 0 || amount > balances.get(userIndex)); 

 

            // Execute the investment transaction 

            double newBalance = balances.get(userIndex) - amount; 

            balances.set(userIndex, newBalance); 

            System.out.printf("Invested Rs %.2f in Fixed Deposit successfully! Remaining Balance: Rs %.2f\n", amount, newBalance); 

             

            // Update investment tracking lists 

            InvestedAmount.set(userIndex, InvestedAmount.get(userIndex) + amount); 

            fixedInvestTimes.set(userIndex, i); 

            writeAllDataToFile(); 

 

            // Check for completion or user exit 

            if (i == 10) { 

                System.out.println("Congratulations! You have completed all 10 fixed deposit investments."); 

                liquidateFixedDeposit(userIndex); 

                return; 

            } else { 

                System.out.println("Do you want to continue with Investment " + (i + 1) + " of 10? (yes/no): "); 

                String response = input.nextLine().trim().toLowerCase(); 

                if (response.equals("no")) { 

                    System.out.println("Exiting Fixed Deposit investment menu. Your progress is saved (" + i + " investments completed)."); 

                    return; 

                } 

            } 

        } 

    } 

     

    static void liquidateFixedDeposit(int userIndex) { 

        double totalInvestment = InvestedAmount.get(userIndex); 

        double returnRate = 0.07; 

         

        double totalReturn = totalInvestment * returnRate;  

        double finalCashOut = totalInvestment + totalReturn; 

         

        // Credit the final amount to the main balance 

        balances.set(userIndex, balances.get(userIndex) + finalCashOut); 

         

        // Reset investment variables 

        InvestedAmount.set(userIndex, 0.0); 

        fixedInvestTimes.set(userIndex, 0); 

         

        System.out.println("\n--- FIXED DEPOSIT LIQUIDATED ---"); 

        System.out.printf("Total Invested: Rs %.2f\n", totalInvestment); 

        System.out.printf("7%% Interest Earned: Rs %.2f\n", totalReturn); 

        System.out.printf("Total Credited to Balance: Rs %.2f\n", finalCashOut); 

        System.out.printf("Your New Balance: Rs %.2f\n", balances.get(userIndex)); 

         

        // Add to transaction history 

        transactionHistory.get(userIndex).add("Fixed Deposit Liquidated: +Rs " + String.format("%.2f", finalCashOut) + " (Investment + Profit)"); 

         

        writeAllDataToFile(); 

    } 

     

    static void StocksMenu(int userIndex) { 

 

        System.out.println("\n--- STOCKS INVESTMENT ---"); 

        System.out.printf("Total Invested: Rs %.2f\n", stocksInvestedAmount.get(userIndex)); 

        System.out.printf("Current Portfolio Value: Rs %.2f\n", stocksCurrentValue.get(userIndex)); 

         

        System.out.println("1. Invest More in Stocks (High Risk / Recursive Simulation)"); 

        System.out.println("2. Cash Out Stocks"); 

        System.out.print("Enter choice: "); 

 

        try { 

            int choice = input.nextInt(); 

            input.nextLine(); 

 

            switch (choice) { 

                case 1: 

                    stockInvestment(userIndex); 

                    break; 

                case 2: 

                    cashOutStocks(userIndex); 

                    break; 

                default: 

                    System.out.println("Invalid investment choice. Returning to main menu."); 

            } 

        } catch (InputMismatchException e) { 

            System.out.println(" Invalid input. Returning to main menu."); 

            input.nextLine(); 

        } 

    } 

 

    static void stockInvestment(int userIndex) { 

        System.out.println("\n--- SIMULATED STOCKS INVESTMENT (Recursive Compounding) ---"); 

        System.out.print("Enter amount to invest in Stocks: "); 

        double amount; 

        try { 

            amount = input.nextDouble(); 

            input.nextLine(); 

        } catch (InputMismatchException e) { 

            System.out.println(" Invalid amount format. Investment cancelled."); 

            input.nextLine(); 

            return; 

        } 

 

        if (amount <= 0 || amount > balances.get(userIndex)) { 

            System.out.println(" Invalid amount or Insufficient balance. Investment cancelled."); 

            return; 

        } 

 

        // 1. Process Transaction: Subtract from main balance 

        balances.set(userIndex, balances.get(userIndex) - amount); 

 

        // Calculate the total value *before* applying the new random change 

        double valueBeforeChange = stocksCurrentValue.get(userIndex) + amount; 

         

        transactionHistory.get(userIndex).add("Stocks Investment: -Rs " + String.format("%.2f", amount)); 

 

 

        // 2. Trigger the Recursive Calculation for 5 simulated periods 

        final int NUM_PERIODS = 5; 

        System.out.println("\nSimulating market volatility over " + NUM_PERIODS + " trading periods:"); 

 

        // Recursion is used here to model compounding growth logically 

        double finalValue = calculateCompoundedReturn(valueBeforeChange, NUM_PERIODS); 

 

        // 3. Update and Display Results 

        stocksCurrentValue.set(userIndex, finalValue); 

        stocksInvestedAmount.set(userIndex, stocksInvestedAmount.get(userIndex) + amount); 

 

        double totalGainLoss = finalValue - valueBeforeChange; 

 

        System.out.println("\n Investment Cycle Complete."); 

        System.out.printf("Initial Portfolio Value: Rs %.2f\n", valueBeforeChange); 

        System.out.printf("FINAL PORTFOLIO VALUE: Rs %.2f\n", finalValue); 

        System.out.printf("TOTAL GAIN/LOSS: Rs %.2f\n", totalGainLoss); 

 

        writeAllDataToFile(); 

    } 

 

 

    static double calculateCompoundedReturn(double currentValue, int periodsRemaining) { 

 

        if (periodsRemaining <= 0) { 

            return currentValue; 

        } 

        Random rand = new Random(); 

        double randomReturnRate = rand.nextDouble() * 0.23 - 0.08; 

 

        double gainLoss = currentValue * randomReturnRate; 

        double newValue = currentValue + gainLoss; 

 

        System.out.printf("   Period %d Return (%+.2f%%): %s%.2f (New Value: %.2f)\n", 

                (6 - periodsRemaining), randomReturnRate * 100, (gainLoss >= 0 ? "+" : "-"), Math.abs(gainLoss), newValue); 

 

        return calculateCompoundedReturn(newValue, periodsRemaining - 1); 

    } 

 

    public static void cashOutStocks(int userIndex) { 

        double currentStockValue = stocksCurrentValue.get(userIndex); 

        double investedAmount = stocksInvestedAmount.get(userIndex); 

 

        if (currentStockValue == 0.0) { 

            System.out.println("Your stock portfolio is currently zero."); 

            return; 

        } 

 

        balances.set(userIndex, balances.get(userIndex) + currentStockValue); 

 

        System.out.printf("\nSuccessfully liquidated stock portfolio for Rs %.2f.\n", currentStockValue); 

        System.out.printf("Total Return (Gain/Loss): Rs %.2f\n", currentStockValue - investedAmount); 

 

        stocksInvestedAmount.set(userIndex, 0.0); 

        stocksCurrentValue.set(userIndex, 0.0); 

         

        transactionHistory.get(userIndex).add("Stocks Cashed Out: +Rs " + String.format("%.2f", currentStockValue) + " (Investment + Gain/Loss)"); 

 

        System.out.printf("Amount credited to your main balance. New Balance: Rs %.2f\n", balances.get(userIndex)); 

        writeAllDataToFile(); 

    } 

 

    // ----- LOAN METHODS ----- // 

 

    static void applyForLoan() { 

        System.out.println("\n--- LOAN APPLICATION ---"); 

        System.out.print("Enter loan amount: "); 

        double loanAmount; 

        try { 

            loanAmount = input.nextDouble(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

        input.nextLine(); 

 

        if (!validateLoanAmount(loanAmount)) return; 

 

        System.out.print("Enter loan purpose: "); 

        String purpose = input.nextLine(); 

 

        System.out.print("Enter loan duration (years): "); 

        int years; 

        try { 

            years = input.nextInt(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a whole number for years."); 

            input.nextLine(); 

            return; 

        } 

        input.nextLine(); 

 

        loanApprovalMessage(loanAmount, purpose, years); 

    } 

 

    static boolean validateLoanAmount(double amount) { 

        if (amount <= 0) { 

            System.out.println("Loan amount must be greater than 0."); 

            return false; 

        } 

        if (amount > 50000) { 

            System.out.println("Loan denied. Maximum allowed: Rs 50,000"); 

            return false; 

        } 

        return true; 

    } 

 

    static void loanApprovalMessage(double amount, String purpose, int years) { 

        // Loan is automatically approved in this simulation 

        System.out.println("\nLoan request submitted successfully!"); 

        System.out.println("Amount: Rs " + String.format("%.2f", amount)); 

        System.out.println("Purpose: " + purpose); 

        System.out.println("Duration: " + years + " years"); 

        System.out.println("\nLoan is approved based on our simulated credit check! Funds will be disbursed shortly."); 

    } 

     

    // ----- FUND TRANSFER METHODS ----- // 

     

    static void transferFunds(int userIndex) { 

        System.out.println("\n--- TRANSFER FUNDS ---"); 

        System.out.println("1. Transfer within Java Bank (Account to Account)"); 

        System.out.println("2. Transfer to other bank (Inter-Bank)"); 

        System.out.println("3. Transfer to Mobile Number (Peer-to-Peer)"); 

        System.out.print("Enter choice: "); 

        int choice; 

        do { 

            choice = getChoice(); 

            if (choice < 1 || choice > 3) { 

                System.out.println("Invalid choice! Please enter 1, 2, or 3."); 

            } 

        } while (choice < 1 || choice > 3); 

 

        switch (choice) { 

            case 1: 

                transferWithinSameBank(userIndex);  

                break; 

            case 2: 

                transferToBankAccount(userIndex);  

                break; 

            case 3: 

                transferToMobile(userIndex); 

                break; 

        } 

        writeAllDataToFile(); 

    } 

     

    static void transferWithinSameBank(int senderIndex) { 

        System.out.println("\n--- TRANSFER WITHIN JAVA BANK ---"); 

        System.out.print("Enter recipient's 6-digit account number (e.g., 100000): "); // Account numbers start at 100000 (6 digits minimum) 

        String accNumStr = input.nextLine(); 

        int accNum; 

        try { 

            accNum = Integer.parseInt(accNumStr.trim()); 

        } catch (NumberFormatException e) { 

            System.out.println("Invalid account number format."); 

            return; 

        } 

         

        int recipientIndex = -1; 

        for (int i = 0; i < accountNumbers.size(); i++) { 

            if (accountNumbers.get(i) == accNum) {  

                recipientIndex = i; 

                break; 

            } 

        } 

         

        if (recipientIndex == -1) { 

            System.out.println("Account number not found in Java Bank!"); 

            return; 

        } 

        if (recipientIndex == senderIndex) { 

            System.out.println("Cannot transfer to your own account!"); 

            return; 

        } 

         

        System.out.print("Enter amount to transfer: Rs "); 

        double amount; 

        try { 

            amount = input.nextDouble(); 

            input.nextLine(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

         

        if (amount <= 0) { 

            System.out.println("Amount must be positive!"); 

            return; 

        } 

 

        if (amount > balances.get(senderIndex)) { 

            System.out.println("Insufficient balance! Current Balance: Rs " + String.format("%.2f", balances.get(senderIndex))); 

            return; 

        } 

         

        // Execute transfer 

        balances.set(senderIndex, balances.get(senderIndex) - amount); 

        balances.set(recipientIndex, balances.get(recipientIndex) + amount); 

         

        // Log transactions 

        transactionHistory.get(senderIndex).add("Transferred -Rs " + String.format("%.2f", amount) + " to Account " + accNum); 

        transactionHistory.get(recipientIndex).add("Received +Rs " + String.format("%.2f", amount) + " from Account " + accountNumbers.get(senderIndex)); 

         

        System.out.printf("Transferred Rs %.2f to account %d successfully!\n", amount, accNum); 

        System.out.println("Recipient: " + names.get(recipientIndex)); 

        System.out.printf("Your new balance: Rs %.2f\n", balances.get(senderIndex)); 

    } 

     

    static void transferToBankAccount(int senderIndex) { 

        System.out.println("\n--- TRANSFER TO ANY BANK ---"); 

        System.out.println("Your Bank: Java Bank"); 

        System.out.println("Your Account #: " + accountNumbers.get(senderIndex)); 

        System.out.print("\nEnter recipient's bank name: "); 

        String bankName = input.nextLine().trim(); 

        System.out.print("Enter recipient's account number: "); 

        String accNum = input.nextLine().trim(); 

        System.out.print("Enter recipient's account holder name: "); 

        String recipientName = input.nextLine().trim(); 

        System.out.print("Enter amount to transfer: Rs "); 

        double amount; 

        try { 

            amount = input.nextDouble(); 

            input.nextLine(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

        if (amount <= 0) { 

            System.out.println("Amount must be positive!"); 

            return; 

        } 

 

        if (amount > balances.get(senderIndex)) { 

            System.out.println("Insufficient balance!"); 

            System.out.printf("Your current balance: Rs %.2f\n", balances.get(senderIndex)); 

            return; 

        } 

         

        // Transaction Fee Calculation (2% of amount, minimum 50 Rs) 

        double transactionFee = amount * 0.02; 

        if (transactionFee < 50) { 

            transactionFee = 50; 

        } 

 

        double totalDeduction = amount + transactionFee; 

        if (totalDeduction > balances.get(senderIndex)) { 

            System.out.println("Insufficient balance for transfer + transaction fee!"); 

            System.out.printf("Transfer amount: Rs %.2f\n", amount); 

            System.out.printf("Transaction fee: Rs %.2f\n", transactionFee); 

            System.out.printf("Total required: Rs %.2f\n", totalDeduction); 

            System.out.printf("Your balance: Rs %.2f\n", balances.get(senderIndex)); 

            return; 

        } 

 

        // Show confirmation 

        System.out.println("\n--- TRANSFER SUMMARY ---"); 

        System.out.println("From: " + names.get(senderIndex)); 

        System.out.println("From Account: " + accountNumbers.get(senderIndex)); 

        System.out.println("From Bank: Java Bank"); 

        System.out.println("To: " + recipientName); 

        System.out.println("To Account: " + accNum); 

        System.out.println("To Bank: " + bankName); 

        System.out.printf("Amount: Rs %.2f\n", amount); 

        System.out.printf("Transaction Fee: Rs %.2f\n", transactionFee); 

        System.out.printf("Total Deduction: Rs %.2f\n", totalDeduction); 

 

        System.out.print("\nConfirm transfer? (yes/no): "); 

        String confirm = input.nextLine().trim().toLowerCase(); 

 

        if (!confirm.equals("yes")) { 

            System.out.println("Transfer cancelled."); 

            return; 

        } 

        double oldBalance = balances.get(senderIndex); 

        balances.set(senderIndex, oldBalance - totalDeduction); 

         

        String transactionId = "IBT" + System.currentTimeMillis() % 1000000; 

         

        // Log transaction 

        transactionHistory.get(senderIndex).add("Inter-Bank Transfer -Rs " + String.format("%.2f", totalDeduction) + " (Incl. Fee: " + String.format("%.2f", transactionFee) + ") to " + bankName); 

         

        System.out.println("\n--- TRANSFER SUCCESSFUL ---"); 

        System.out.println("Transaction ID: " + transactionId); 

        System.out.println("Amount transferred: Rs " + String.format("%.2f", amount)); 

        System.out.println("Transaction fee: Rs " + String.format("%.2f", transactionFee)); 

        System.out.printf("Previous balance: Rs %.2f\n", oldBalance); 

        System.out.printf("New balance: Rs %.2f\n", balances.get(senderIndex)); 

        System.out.println("Transaction time: " + java.time.LocalDateTime.now()); 

        System.out.println("Note: Funds will be transferred within 1-2 business days."); 

    } 

     

    static void transferToMobile(int senderIndex) { 

        System.out.print("Enter recipient's mobile number: "); 

        String mobile = input.nextLine(); 

        if (mobile.length() < 11 || !mobile.startsWith("0")) { 

            System.out.println("Invalid mobile number! Must be at least 11 digits and start with 0."); 

            return; 

        } 

        System.out.print("Enter amount to transfer: Rs "); 

        double amount; 

        try { 

            amount = input.nextDouble(); 

            input.nextLine(); 

        } catch (java.util.InputMismatchException e) { 

            System.out.println("Invalid input. Please enter a valid number."); 

            input.nextLine(); 

            return; 

        } 

        if (amount <= 0) { 

            System.out.println("Amount must be positive!"); 

            return; 

        } 

        if (amount > balances.get(senderIndex)) { 

            System.out.println("Insufficient balance! Current Balance: Rs " + String.format("%.2f", balances.get(senderIndex))); 

            return; 

        } 

         

        // Execute transfer 

        balances.set(senderIndex, balances.get(senderIndex) - amount); 

         

        // Log transaction 

        transactionHistory.get(senderIndex).add("Mobile Transfer -Rs " + String.format("%.2f", amount) + " to Mobile " + mobile); 

 

        System.out.printf("Transferred Rs %.2f to mobile %s successfully!\n", amount, mobile); 

        System.out.printf("Your new balance: Rs %.2f\n", balances.get(senderIndex)); 

    } 

     

    // ----- UTILITY BILL METHODS ----- // 

 

    static void payUtilityBills(int userIndex) { 

        System.out.println("\n--- PAY UTILITY BILLS ---"); 

        System.out.println("1. Electricity Bill"); 

        System.out.println("2. Gas Bill"); 

        System.out.println("3. Internet Bill"); 

        System.out.print("Enter choice: "); 

        int choice; 

        do { 

            choice = getChoice(); 

            if (choice < 1 || choice > 3) { 

                System.out.println("Invalid choice! Please enter 1, 2, or 3."); 

            } 

        } while (choice < 1 || choice > 3); 

        switch (choice) { 

            case 1: 

                payBill(userIndex, "Electricity"); 

                break; 

            case 2: 

                payBill(userIndex, "Gas"); 

                break; 

            case 3: 

                payBill(userIndex, "Internet"); 

                break; 

        } 

        writeAllDataToFile(); 

    } 

     

    static void payBill(int userIndex, String billType) { 

        System.out.printf("\n--- PAY %s BILL ---\n", billType.toUpperCase()); 

         

        //generating random bill amount within 1000 and 10,000 

        double billAmount = (Math.random() * 9000) + 1000; 

        billAmount = Math.round(billAmount * 100.0) / 100.0; // Round to 2 decimal places 

         

        System.out.printf("Your %s bill amount: Rs %.2f\n", billType, billAmount); 

        System.out.print("Enter your consumer/reference number: "); 

        String refNumber = input.nextLine(); 

 

        System.out.print("Do you want to pay this bill? (yes/no): "); 

        String confirm = input.nextLine().trim().toLowerCase(); 

 

        if (!confirm.equals("yes")) { 

            System.out.println("Payment cancelled."); 

            return; 

        } 

        if (billAmount > balances.get(userIndex)) { 

            System.out.println("Insufficient balance to pay the bill! Current Balance: Rs " + String.format("%.2f", balances.get(userIndex))); 

            return; 

        } 

         

        // Execute payment 

        balances.set(userIndex, balances.get(userIndex) - billAmount); 

         

        // Log transaction 

        transactionHistory.get(userIndex).add("Utility Bill Paid (" + billType + "): -Rs " + String.format("%.2f", billAmount)); 

 

        String transactionId = String.format("%s-%s-%d",  

            billType.substring(0, Math.min(3, billType.length())).toUpperCase(), 

            refNumber.substring(Math.max(0, refNumber.length() - 4)), 

            (int)(Math.random() * 1000)); 

             

        System.out.printf("%s bill paid successfully! Rs %.2f deducted.\n", billType, billAmount); 

        System.out.println("Transaction ID: " + transactionId); 

        System.out.printf("Your new balance: Rs %.2f\n", balances.get(userIndex)); 

    } 

 

 

    // ----- ADMIN METHODS ----- // 

 

    static void adminMenu() { 

        boolean adminRunning = true; 

        while (adminRunning) { 

            System.out.println("\n--- ADMIN MENU ---"); 

            System.out.println("1. View All Users"); 

            System.out.println("2. Check Total Bank Balance"); 

            System.out.println("3. Exit Admin Menu"); 

            System.out.print("Enter choice: "); 

 

            int choice; 

            do { 

                choice = getChoice(); 

                if (choice < 1 || choice > 3) { 

                    System.out.println("Invalid choice! Please enter a number between 1 and 3."); 

                } 

            } while (choice < 1 || choice > 3); 

 

            switch (choice) { 

                case 1: 

                    viewAllUsers(); 

                    break; 

                case 2: 

                    checkTotalBalance(); 

                    break; 

                case 3: 

                    System.out.println("Exiting admin menu. Returning to initial menu."); 

                    adminRunning = false; 

                    break; 

                default: 

                    break; 

            } 

        } 

    } 

 

    static void viewAllUsers() { 

        if (names.isEmpty()) { 

            System.out.println("No users registered yet."); 

            return; 

        } 

        System.out.println("\n--- ALL REGISTERED USERS ---"); 

        for (int i = 0; i < names.size(); i++) { 

            printUserDetails(i); 

        } 

    } 

     

    static void printUserDetails(int index) { 

        String status = loginLocks.get(index) ? "LOCKED" : (accountFrozen.size() > index && accountFrozen.get(index) ? "FROZEN" : "ACTIVE"); 

        System.out.println("Account #: " + accountNumbers.get(index) + 

                ", Name: " + names.get(index) + 

                ", CNIC: " + CNIC.get(index) + 

                ", Email: " + Email.get(index) + 

                ", Balance: Rs " + String.format("%.2f", balances.get(index)) + 

                ", Status: " + status); 

    } 

     

    static void checkTotalBalance() { 

        double total = calculateTotalBalance(); 

        System.out.printf("Total bank balance of all users: Rs %.2f\n", total); 

    } 

     

    static double calculateTotalBalance() { 

        double total = 0; 

        for (double balance : balances) { 

            total += balance; 

        } 

        return total; 

    } 

     

    // ----- EXIT PROGRAM METHOD ----- // 

    static void exitProgram() 

    { 

        writeAllDataToFile(); // Ensure final state is saved 

        System.out.println("Thank you! Have a nice day :)"); 

    } 

} 


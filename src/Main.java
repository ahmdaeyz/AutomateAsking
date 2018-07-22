import java.io.File;
import java.util.Scanner;
/**
 * Created by ahmdaeyz.
 * Date: 20/7/2018
 */

public class Main {
    private static Scanner scanner= new Scanner(System.in);

    public static void main(String[] args) {
        Bot.intiatlizingBotsList(Bot.bots);
        boolean quit = false;
        int choice;
        txtOptions();
        while(!quit){
            System.out.print("Choice : ");
            choice=scanner.nextInt();
            scanner.nextLine();
                switch (choice){
                    case 1:
                        askWithoutAcc();
                        break;
                    case 2:
                        askwithAcc();
                        break;
                    case 3:
                        askGroupWithoutAcc();
                        break;
                    case 4:
                        askGroupWithAcc();
                        break;
                    case 5:
                        quit=true;
                        break;
                }
            }
    }
    private static void txtOptions(){
        System.out.println("Options : ");
        System.out.println(" *1* Ask someone without an account.\n" +
                           " *2* Ask someone with a BOT account.\n" +
                           " *3* Ask a group without an account.\n" +
                           " *4* Ask a group with a BOT account.\n" +
                           " *5* Quit.");
    }
    private static void askWithoutAcc(){
        int choice;
        boolean back = false;
        System.out.println(" #1 Write the text in the console.\n"+
                           " #2 Sumbit a file.\n"+
                           " #3 Back.");
        while(!back){
            System.out.println("Choice : ");
            choice=scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    System.out.println("Enter ur Question : ");
                    String q =scanner.nextLine() ;
                    askAnonQ(q);
                    break;
                case 2:
                    askAnonQFromFile();
                    break;
                case 3:
                    back=true;
                    txtOptions();
                    break;
            }

        }
    }
    private static void askAnonQ(String Q){
        String usrName;
        System.out.println("Enter the username of the asked : ");
        usrName=scanner.next();
        Question q = new Question(Q,0,false);
        Spammer spammer = new Spammer();
        spammer.askAnon(usrName,q);
    }
    private static void askAnonQFromFile(){
        String filepath,usrName;
        System.out.println("Enter File path of the Question : ");
        filepath=scanner.next();
        System.out.println("Enter username of the asked : ");
        usrName=scanner.next();
        Question q = new Question(filepath,true);
        Spammer spammer = new Spammer();
        spammer.askAnon(usrName,q);

    }
    private static void askwithAcc() {
        String userName, password;
        System.out.println("Enter the BOT credientials : ");
        System.out.println("Username : ");
        userName=scanner.next();
        System.out.println("Password : ");
        password=scanner.next();
        Bot bot = new Bot(userName,password);
        int choice;
        boolean back = false;
        System.out.println(" #1 Write the text in the console.\n" +
                           " #2 Sumbit a file.\n" +
                           " #3 Back.");
        while (!back) {
            System.out.println("Choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter ur Question : ");
                    String q =scanner.nextLine() ;
                    askAccQ(q,bot);
                    break;
                case 2:
                    askAccQFromFile(bot);
                    break;
                case 3:
                    back = true;
                    txtOptions();
                    break;
            }
        }
    }
    private static void askAccQ(String q,Bot bot){
        String usrName;
        System.out.println("Enter the username of the asked : ");
        usrName=scanner.next();
        Question Q = new Question(q,0,false);
        bot.askSomeone(usrName,Q,false);
    }
    private static void askAccQFromFile(Bot bot){
        String filepath,usrName;
        System.out.println("Enter File path of the Question : ");
        filepath=scanner.next();
        System.out.println("Enter username of the asked : ");
        usrName=scanner.next();
        Question q = new Question(filepath,true);
        bot.askSomeone(usrName,q,false);
    }
    private static void askGroupWithoutAcc() {
        int choice;
        boolean back = false;
        System.out.println(" #1 Write the text in the console.\n" +
                           " #2 Sumbit a file.\n" +
                           " #3 Back.");
        while (!back) {
            System.out.println("Choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter ur Question : ");
                    String q =scanner.nextLine() ;
                    askAnonAGroupQ(q);
                    break;
                case 2:
                    askAnonAGroupQFromFile();
                    break;
                case 3:
                    back = true;
                    txtOptions();
                    break;
            }
        }
    }
    private static void askAnonAGroupQ(String q){
        String usersFilePath;
        System.out.println("Enter the path to the users file : ");
        usersFilePath=scanner.next();
        Question Q = new Question(q,0,false);
        Spammer spammer = new Spammer(usersFilePath);
        spammer.askAGroup(Q);
    }
    private static void askAnonAGroupQFromFile(){
        String filepath,usersFilePath;
        System.out.println("Enter the path to the users file : ");
        usersFilePath=scanner.next();
        System.out.println("Enter File path of the Question : ");
        filepath=scanner.next();
        Question q = new Question(filepath,true);
        Spammer spammer = new Spammer(usersFilePath);
        spammer.askAGroup(q);
    }
    private static void askGroupWithAcc(){
        String userName, password;
        System.out.println("Enter the BOT credientials : ");
        System.out.println("Username : ");
        userName=scanner.next();
        System.out.println("Password : ");
        password=scanner.next();
        Bot bot = new Bot(userName,password);
        int choice;
        boolean back = false;
        System.out.println(" #1 Write the text in the console.\n" +
                           " #2 Sumbit a file.\n" +
                           " #3 Back.");
        while (!back) {
            System.out.println("Choice : ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter ur Question : ");
                    String q =scanner.nextLine() ;
                    askAccAGroupQ(q,bot);
                    break;
                case 2:
                    askAccAGroupQFromFile(bot);
                    break;
                case 3:
                    back = true;
                    txtOptions();
                    break;
            }
        }
    }
    private static void askAccAGroupQ(String q,Bot bot){
        String usersFilePath;
        System.out.println("Enter the path to the users file : ");
        usersFilePath=scanner.next();
        Question Q = new Question(q,0,false);
        bot.askAGroup(new File(usersFilePath),Q,false);
    }
    private static void askAccAGroupQFromFile(Bot bot){
        String filepath,usersFilePath;
        System.out.println("Enter the path to the users file : ");
        usersFilePath=scanner.next();
        System.out.println("Enter File path of the Question : ");
        filepath=scanner.next();
        Question q = new Question(filepath,true);
        bot.askAGroup(new File(usersFilePath),q,false);
    }
//    private static String gettingTxtFromConsole(){
//        System.out.println("Enter \"d\" in a new line when done");
//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        BufferedReader br = new BufferedReader(inputStreamReader);
//        StringBuilder sb = new StringBuilder();
//        try {
//            String line = "";
//            String paragraph = "";
//            System.out.println("Enter the text: ");
//            InputStreamReader isr = new InputStreamReader(System.in);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            do
//            {
//                line = bufferedReader.readLine();
//                paragraph = paragraph + line + " ";
//            }while(!line.equals("d"));
//            isr.close();
//            bufferedReader.close();
//            return paragraph;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Bot {
    private static WebClient webClient = new WebClient(BrowserVersion.FIREFOX_52);
    private String botUsrName;
    private String password;
    public static ArrayList<Bot> bots = new ArrayList<>();
    public Bot(String botUsrName, String password) {
        this.botUsrName = botUsrName;
        this.password = password;
    }
    public boolean askSomeone(String usrName,Question q,boolean anon){
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        try {
            if(!q.getQuestion().equals(null)) {
                HtmlPage htmlPage = webClient.getPage("https://ask.fm/login");
                HtmlForm login = htmlPage.querySelector("form");
                login.getInputByName("login").setValueAttribute(this.botUsrName);
                login.getInputByName("password").setValueAttribute(this.password);
                HtmlSubmitInput btnin = htmlPage.querySelector(".btn-primary-wide");
                HtmlPage hpage = btnin.click();
                if (hpage.asText().contains("See all friends")) {
                    System.out.println("The bot is logged in.");
                    HtmlPage usrPage = webClient.getPage("https://ask.fm/" + usrName);
                    HtmlForm asking = usrPage.getForms().get(0);
                    if (!anon) {
                        HtmlInput type = usrPage.querySelector("div.optionsBar input[value='anonymous']");
                        type.setAttribute("value", "user");
                    }
                    asking.getTextAreaByName("question[question_text]").setText(q.getQuestion());
                    HtmlButton askBtn = usrPage.getForms().get(0).getButtonByName("button");
                    HtmlPage page = askBtn.click();
                    if (page.asText().contains("Done")) {
                        System.out.println("The Bot Asked : " + usrName);
                        return true;
                    }
                } else if (htmlPage.asText().contains("Please verify you’re a human!")) {
                    System.out.println("Darn it the Folks Found out that this is a bot :'(");
                } else {
                    System.out.println("Smth is wrong with the bot.");
                }
            }else{
                System.out.println("Number of letters of the question exceeded the default.");
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean askAGroup(File file, Question q,boolean anon) {
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        try{
            if(!q.getQuestion().equals(null)) {
                HtmlPage htmlPage = webClient.getPage("https://ask.fm/login");
                HtmlForm login = htmlPage.querySelector("form");
                login.getInputByName("login").setValueAttribute(this.botUsrName);
                login.getInputByName("password").setValueAttribute(this.password);
                HtmlSubmitInput btnin = htmlPage.querySelector(".btn-primary-wide");
                HtmlPage profile = btnin.click();
                if (profile.asText().contains("See all friends")) {
                    System.out.println("The bot is logged in.");
                    ArrayList<String> usrNames = gettingUsrNames(file);
                    if (usrNames.isEmpty()) {
                        System.out.println("There is smth wrong with ur usernames file.");
                    } else {
                        for (String usrName : usrNames) {
                            HtmlPage usrPage = webClient.getPage("https://ask.fm/" + usrName);
                            HtmlForm asking = usrPage.getForms().get(0);
                            if (!anon) {
                                HtmlInput type = usrPage.querySelector("div.optionsBar input[value='anonymous']");
                                type.setAttribute("value", "user");
                            }
                            asking.getTextAreaByName("question[question_text]").setText(q.getQuestion());
                            HtmlButton askBtn = usrPage.getForms().get(0).getButtonByName("button");
                            HtmlPage page = askBtn.click();
                            if (page.asText().contains("Done")) {
                                System.out.println("The Bot Asked : " + usrName);
                            }
                        }
                        return true;
                    }
                } else if (htmlPage.asText().contains("Please verify you’re a human!")) {
                    System.out.println("Darn it the Folks Found out that this is a bot :'(");
                } else {
                    System.out.println("Smth is wrong with the bot.");
                }
            }else{
                System.out.println("Number of letters of the question exceeded the default.");
                return false;
            }
    }catch(IOException e){
        e.printStackTrace();
    }
        return false;
    }
    public static Bot signUp(String botName){
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        try {
            HtmlPage signUpPage = webClient.getPage("https://ask.fm/signup");
            HtmlPage tenMinutesMail = webClient.getPage("https://10minutemail.com/");
            String email = tenMinutesMail.getElementById("mailAddress").getAttribute("value");
            HtmlForm signUpForm = signUpPage.getForms().get(0);
            signUpForm.getInputByName("user[email]").setValueAttribute(email);
            signUpForm.getInputByName("user[name]").setValueAttribute(botName);
            int randnum = (new Random()).nextInt(1000);
            signUpForm.getInputByName("user[login]").setValueAttribute("askingBot"+randnum);
            signUpForm.getInputByName("user[password]").setValueAttribute("bot1234");
            signUpForm.getSelectByName("user[born_at_day]").setSelectedAttribute(signUpForm.getSelectByName("user[born_at_day]").getOptionByValue("1"),true);
            signUpForm.getSelectByName("user[born_at_month]").setSelectedAttribute(signUpForm.getSelectByName("user[born_at_month]").getOptionByValue("1"),true);
            signUpForm.getSelectByName("user[born_at_year]").setSelectedAttribute(signUpForm.getSelectByName("user[born_at_year]").getOptionByValue("1998"),true);
            signUpForm.getSelectByName("user[gender_id]").setSelectedAttribute(signUpForm.getSelectByName("user[gender_id]").getOptionByValue("1"),true);
            HtmlInput signUpBtn = signUpForm.querySelector("div.simpleFormItem input[value='Sign up']");
            HtmlPage profilePage = signUpBtn.click();
                if(profilePage.asText().contains("Wall")) {
                    System.out.println("Bot created Successfully.");
                    bots.add(new Bot("askingBot" + randnum, "bot1234"));
                    return new Bot("askingBot" + randnum, "bot1234");
                }else{
                    System.out.println("probably duplicate username.");
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void likeAnAnswer(){
        //
    }
    public void followAUser(){
        //
    }
    private ArrayList<String> gettingUsrNames(File usrs){
        ArrayList<String> usrNames = new ArrayList<>();
        try {
            FileReader fr = new FileReader(usrs);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while((line !=null)){
                usrNames.add(line);
                line =br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usrNames;
    }
    public static void intiatlizingBotsList(ArrayList<Bot> bots){
        bots.add(new Bot("AskingBot979","bot1234"));
        bots.add(new Bot("askingBot2","bot1234"));
        bots.add(new Bot("askingBot3","bot1234"));
        bots.add(new Bot("askingBot4","bot1234"));
        bots.add(new Bot("askingBot5","bot1234"));
    }
    private  boolean removeAccount(Bot bot){
        try {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setCssEnabled(false);
            webClient.setAjaxController(new NicelyResynchronizingAjaxController());
            HtmlPage htmlPage = webClient.getPage("https://ask.fm/login");
            HtmlForm login = htmlPage.querySelector("form");
            login.getInputByName("login").setValueAttribute(bot.botUsrName);
            login.getInputByName("password").setValueAttribute(bot.password);
            HtmlSubmitInput btnin = htmlPage.querySelector(".btn-primary-wide");
            HtmlPage profile = btnin.click();
            if (profile.asText().contains("See all friends")) {
                System.out.println("The bot is logged in.");
                HtmlPage leaving = webClient.getPage("https://ask.fm/account/settings/delete-account");
                HtmlForm toEnterPassword = leaving.getForms().get(0);
                HtmlInput password = toEnterPassword.getInputByName("user[password]");
                password.setValueAttribute(bot.password);
                HtmlInput leave = toEnterPassword.getInputByName("commit");
                HtmlPage done = leave.click();
                if(done.asText().contains("Sign up")){
                    System.out.println("Account Removed.");
                    return true;
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
//    private static HtmlAnchor getConfirmationLink(String pageLink,String email){
//        webClient.getOptions().setJavaScriptEnabled(false);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
//        try {
//            HtmlPage emailPage = webClient.getPage(pageLink);
//            Thread.sleep(30000);
//            emailPage.refresh();
//            if(emailPage.asText().contains(email)){
//              return (HtmlAnchor) emailPage.getFirstByXPath("/html/body/div[1]/div[1]/div[2]/div/div/div/div/table/tbody/tr[2]/td/p/a/@href");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.*;
import java.util.ArrayList;

public class Spammer {
    private static WebClient webClient = new WebClient(BrowserVersion.CHROME);
    private File usrs;
    public Spammer(String filePath){
        this.usrs = new File(filePath);
    }
    public Spammer(){}
    public  boolean askAnon(String userName,Question q){
        try {
            if(!q.getQuestion().equals(null)){
                webClient.getOptions().setJavaScriptEnabled(false);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setCssEnabled(false);
                webClient.setAjaxController(new NicelyResynchronizingAjaxController());
                HtmlPage usrPage = webClient.getPage("https://ask.fm/" + userName);
                HtmlForm asking = usrPage.getForms().get(0);
                asking.getTextAreaByName("question[question_text]").setText(q.getQuestion());
                HtmlInput frst = usrPage.getElementByName("question[terms_accepted]");
                frst.setValueAttribute("true");
                HtmlInput secnd = usrPage.getElementByName("question[privacy_accepted]");
                secnd.setValueAttribute("true");
                HtmlButton askBtn = usrPage.getForms().get(0).getButtonByName("button");
                HtmlPage page = askBtn.click();
                if(page.asText().contains("Your question has been sent.")){
                    System.out.println("You just Asked : "+userName+".");
                    return true;
                }
            }else{
                System.out.println("Number of letters of the question exceeded the default.");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public  boolean askAGroup(Question q){
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        if(!q.getQuestion().equals(null)) {
            File usrs = this.usrs;
            ArrayList<String> usrNames = gettingUsrNames(usrs);
            if (usrNames.isEmpty()) {
                System.out.println("There is smth wrong with ur usernames file.");
                return false;
            } else {
                for (String userName : usrNames) {
                    if (!userName.equals(null)) {
                        askAnon(userName, q);
                        return true;
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else{
            System.out.println("Number of letters of the question exceeded the default.");
            return false;
        }

        return false;
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
}

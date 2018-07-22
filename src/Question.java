import java.io.*;
public class Question {
    private String content;
    private File quetionContent;
    private int length;
    private boolean isfile;

    public Question(String content,int length,boolean isfile){
        if(isValid(content)) {
            this.content = content;
            this.length=content.length();
            this.isfile=false;
        }else{
            this.content=null;
            this.length=0;
        }
    }
    public Question(String filePath,boolean isfile){
        if(questionInFileIsValid(new File((filePath)))) {
            this.quetionContent = new File(filePath);
            this.isfile=true;
        }else{
            this.quetionContent=null;
        }
    }

    private boolean isValid(String content){
        if(content.length()<=300&&!content.equals("")){
            return true;
        }
        return false;
    }
    private boolean questionInFileIsValid(File file){
        if(file.exists()&&file.isFile()){
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while(line!=null){
                    sb.append(line);
                    sb.append("\n");
                    line=br.readLine();
                }
                br.close();
                if(sb.toString().length()<=300&&!sb.toString().equals("")) {
                    return true;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("This is not a file or doesn't exist.");
            return false;
        }
        return false;
    }
    public String getQuestion(){
        if(this.isfile) {
            StringBuilder sb = new StringBuilder();
            try {
                FileReader fr = new FileReader(this.quetionContent);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append("\n");
                    line = br.readLine();
                }
                br.close();
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            return this.content;
        }
        return null;
    }
}

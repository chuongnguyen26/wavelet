/**
 * Name; Chuong Nguyen
 * PID: A17281350
 * Email: chn021@ucsd.edu
 */
import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    int size = 0;
    String[] list = new String[5];

    private String[] expandList(String[] currentList){
        int newCapacity = currentList.length * 10;
        String[] newList = new String[newCapacity];
        for(int i = 0; i < currentList.length; i++){
            newList[i] = currentList[i];
        }
        return newList;
    }
    public String handleRequest(URI url){
        if(url.getPath().equals("/")){
            String response = "Please append this command '/add-message?s=' to the URl follow with a word.";
            return response;
        }
        else{
            System.out.println("Query: " + url.getQuery());
            if(url.getPath().contains("/add-message")){
            String[] inputRequest = url.getQuery().split("=");
                if(inputRequest[0].equals("s")){
                    if(size == list.length){
                        list = expandList(list);
                    }
                    list[size] = inputRequest[1];
                    size++;
                    String returnString = "";
                    for(int i = 0; i < size; i++){
                        returnString += list[i] + "\n";
                    }
                    return returnString;
                }
            }
            return "404 Command Not Found. Please retry.";
        }
    } 
}

class StringServer{
    public static void main(String[] args) throws IOException{
        if(args.length == 0){
            System.out.println("No Port Value, Please try again inputting" +
            " any numerical value ranging from 1024 t0 49151.");
            return;
        }

        int port = Integer.parseInt(args[0]);
        Server.start(port, new Handler());
    }
}

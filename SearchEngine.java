import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
    String[] data_storage = new String[10];
    int size  = 0;

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Hello there");
        } 
        else {
            if (url.getPath().contains("/add")) {

                String[] parameters = url.getQuery().split("=");

                if (parameters[0].equals("s")) {

                    if(size < data_storage.length){
                        data_storage[size] = parameters[1];
                    }
                    else{
                        String[] temp_data_storage = data_storage;
                        data_storage = new String[temp_data_storage.length * 2];
                        for(int i = 0; i< temp_data_storage.length; i++){
                            data_storage[i] = temp_data_storage[i];
                        }
                        data_storage[size] = parameters[1];
                    }
                    size++;
                    return String.format("You have successfull added %s to the list", 
                    parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")){
                ArrayList<String> print_list = new ArrayList<String>();
                String[] parameters = url.getQuery().split("=");

                if (parameters[0].equals("s")) {
                    for(int i = 0; i < size; i++){
                        if(data_storage[i].indexOf(parameters[1]) >= 0){
                            print_list.add(data_storage[i]);
                        }
                    }
                }
                return print_list.toString();
            }
            return "404 Not Found!";
        }     
    }  
}   

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}


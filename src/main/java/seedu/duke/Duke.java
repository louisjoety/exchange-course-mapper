package seedu.duke;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

public class Duke {
    public static void main(String[] args) throws IOException { // Declare IOException in the method signature
        String filePath = "C:\\Users\\Louis Joe\\IdeaProjects\\tp\\data\\database.json"; // Change this to your actual file path

        JsonReader jsonReader = Json.createReader(new FileReader(filePath)); // No try-catch block
        JsonObject jsonObject = jsonReader.readObject();

        Set<String> universityNames = jsonObject.keySet();
        for (String universityName : universityNames) {
            System.out.println(universityName);
        }
    }
}

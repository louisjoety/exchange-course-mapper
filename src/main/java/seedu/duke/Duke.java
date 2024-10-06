package seedu.duke;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Set;

public class Duke {
    public static void main(String[] args) {
        String jsonString = "{\n" +
                "  \"Chulalongkorn University\": {\n" +
                "    \"courses\": [\n" +
                "      {\n" +
                "        \"pu_course_code\": \"ICE2190472\",\n" +
                "        \"pu_course_name\": \"Netcentric Architecture\",\n" +
                "        \"nus_course_code\": \"CS2105\",\n" +
                "        \"nus_course_name\": \"Introduction to Computer Networks\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"The University of Melbourne\": {\n" +
                "    \"courses\": [\n" +
                "      {\n" +
                "        \"pu_course_code\": \"COMP30019\",\n" +
                "        \"pu_course_name\": \"Graphics and Interaction\",\n" +
                "        \"nus_course_code\": \"CS3241\",\n" +
                "        \"nus_course_name\": \"Computer Graphics\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"The Australian National University\": {\n" +
                "    \"courses\": [\n" +
                "      {\n" +
                "        \"pu_course_code\": \"COMP3670\",\n" +
                "        \"pu_course_name\": \"Introduction to Machine Learning\",\n" +
                "        \"nus_course_code\": \"CS3244\",\n" +
                "        \"nus_course_name\": \"Machine Learning\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = jsonReader.readObject();

            Set<String> universityNames = jsonObject.keySet();
            for (String universityName : universityNames) {
                System.out.println(universityName);
            }
        }
    }
}

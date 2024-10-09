package seedu.exchangecoursemapper.ui;
import seedu.exchangecoursemapper.parser.Parser;

public class UI {
    public void displayGreeting() {
        String greetingMessage = "LOREM IPSUM";
        System.out.println(greetingMessage);;
    }

    public void runChat(Parser parser) {
        String userInput;
        do {
            userInput = parser.getUserInput();
            parser.processUserInput(userInput);
        } while (!userInput.equalsIgnoreCase("bye"));
    }
}
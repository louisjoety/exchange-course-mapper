package seedu.exchangecoursemapper.ui;

import seedu.exchangecoursemapper.constants.Logs;
import seedu.exchangecoursemapper.courses.Course;
import seedu.exchangecoursemapper.parser.CourseValidator;
import seedu.exchangecoursemapper.parser.Parser;
import seedu.exchangecoursemapper.storage.Storage;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_CODE_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.NUS_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.JsonKey.PU_COURSE_NAME_KEY;
import static seedu.exchangecoursemapper.constants.Messages.MAPPED_MODULES_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.NO_MODULES_MESSAGE;
import static seedu.exchangecoursemapper.constants.Messages.LINE_SEPARATOR;
import static seedu.exchangecoursemapper.constants.Messages.LIST_RELEVANT_PU;
import static seedu.exchangecoursemapper.constants.Messages.PARTNER_UNIVERSITY_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.PARTNER_UNIVERSITY_COURSE_CODE_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.DELETE_COURSE_PLAN_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.COMMANDS_LIST;
import static seedu.exchangecoursemapper.constants.Messages.INVALID_INPUT_FORMAT;
import static seedu.exchangecoursemapper.constants.Messages.COMPARISON_RESULTS_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.COMMON_MAPPINGS_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.NO_COMMON_MAPPINGS;
import static seedu.exchangecoursemapper.constants.Messages.UNIQUE_MAPPINGS_HEADER;
import static seedu.exchangecoursemapper.constants.Messages.NO_UNIQUE_MAPPINGS;
import static seedu.exchangecoursemapper.constants.Messages.NO_MATCH_FOUND;


import static seedu.exchangecoursemapper.constants.Commands.BYE;



public class UI {
    private static final Logger logger = Logger.getLogger(CourseValidator.class.getName());

    public void displayGreeting() {
        String greetingMessage = "Welcome to ExchangeCourseMapper! The easiest way to plan your exchange courses.";
        String banner = """
                 ________ _______ _____   ______ _______ _______ _______\s
                |  |  |  |    ___|     |_|      |       |   |   |    ___|
                |  |  |  |    ___|       |   ---|   -   |       |    ___|
                |________|_______|_______|______|_______|__|_|__|_______|
                """;
        System.out.println(banner);
        System.out.println(greetingMessage);
    }

    public void displayExitMessage() {
        String exitMessage = "All the best in planning for your exchange, hope we helped!";
        System.out.println(exitMessage);
    }


    /**
     * Prints out the Partner University (PU) name and PU course code that is
     * mappable to a user specified NUS course code.
     *
     * @param universityName a String representing the PU's name.
     * @param course a JsonObject that stores the PU course information.
     */
    public void printMappableCourse(String universityName, JsonObject course) {
        String puCourseCode = course.getString(PU_COURSE_CODE_KEY);
        System.out.println(PARTNER_UNIVERSITY_HEADER + universityName);
        System.out.println(PARTNER_UNIVERSITY_COURSE_CODE_HEADER + puCourseCode);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints out all relevant partner universities available in our database.
     */
    public void displayPartnerUniversities() {
        System.out.println(Logs.INVALID_UNIVERSITY_INPUT);

        logger.log(Level.INFO, Logs.DISPLAY_PARTNER_UNIVERSITIES);
        System.out.println(LINE_SEPARATOR);
        System.out.println(LIST_RELEVANT_PU);
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints add courses success statement.
     *
     * @param course a Course object that user successfully adds to storage.
     */
    public void printAddMessage(Course course) {
        System.out.println("You have successfully added the course: " + course.formatOutput());
    }


    /**
     * Prints all relevant course mappings for the user's PU input when an incorrect course mapping
     * is passed from the isValidCourseMapping() method.
     *
     * @param courses a JsonArray containing all the relevant course mappings from the user's PU input.
     * @param pu a string containing the user's PU input.
     */
    public void displayAvailableMappings(JsonArray courses, String pu) {
        System.out.println("The available mappings for " + pu + " are:");
        System.out.println(LINE_SEPARATOR);

        for (int i = 0; i < courses.size(); i++) {
            JsonObject course = courses.getJsonObject(i);
            String puCourseCode = course.getString(PU_COURSE_CODE_KEY).toLowerCase();
            String nusCourseCode = course.getString(NUS_COURSE_CODE_KEY).toLowerCase();
            String nusCourseName = course.getString(NUS_COURSE_NAME_KEY).toLowerCase();
            String puCourseName = course.getString(PU_COURSE_NAME_KEY).toLowerCase();

            System.out.println(nusCourseCode + " " + nusCourseName + " | " + puCourseCode
                    + " " + puCourseName + System.lineSeparator());
        }
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Prints delete course success statement.
     *
     * @param deleteCourse a Course object that contains details of the course to e deleted from the Personal Tracker
     */
    public void printDeleteMessage(Course deleteCourse) {
        System.out.println(DELETE_COURSE_PLAN_HEADER + deleteCourse.formatOutput());
    }

    /**
     * Runs the main chat loop of the application. It continuously takes user input
     * and processes it through the {@code Parser} until the user types "bye".
     *
     * @param parser The {@code Parser} object that processes user input.
     */
    public void runChat(Parser parser, Storage storage) {
        String userInput;
        do {
            userInput = parser.getUserInput();
            parser.processUserInput(userInput, storage);
        } while (!userInput.equalsIgnoreCase(BYE));
    }

    public void printListUniCoursesCommand(JsonObject courseObject) {
        String puCourseCode = courseObject.getString(PU_COURSE_CODE_KEY);
        String puCourseName = courseObject.getString(PU_COURSE_NAME_KEY);
        String nusCourseCode = courseObject.getString(NUS_COURSE_CODE_KEY);
        String nusCourseName = courseObject.getString(NUS_COURSE_NAME_KEY);

        System.out.println(puCourseCode + ": " + puCourseName);
        System.out.println(nusCourseCode + ": " + nusCourseName);
        System.out.println(LINE_SEPARATOR);
    }

    public void printCommandsList(){
        System.out.println(LINE_SEPARATOR);
        System.out.println(COMMANDS_LIST);
        System.out.println(LINE_SEPARATOR);
    }

    public void printNoMappedModules(){
        System.out.println(NO_MODULES_MESSAGE);
    }

    public void printMappedModules(List<String> mappedModules){
        System.out.println(MAPPED_MODULES_HEADER);
        System.out.println(LINE_SEPARATOR);
        int moduleIndex = 1;
        for (String module : mappedModules) {
            System.out.println(moduleIndex + ". " + module);
            moduleIndex += 1;
        }
        System.out.println(LINE_SEPARATOR);
    }

    public void printInvalidInputFormat(){
        System.out.println(INVALID_INPUT_FORMAT);
    }

    public void printCommonMappings(String university1, String university2, Set<String> commonCourseCodes,
                                    List<String> uni1Modules, List<String> uni2Modules){
        System.out.println(COMPARISON_RESULTS_HEADER + university1 + " and " + university2 + ":");

        System.out.println("\n" + COMMON_MAPPINGS_HEADER);
        System.out.println(LINE_SEPARATOR);
        if (commonCourseCodes.isEmpty()) {
            System.out.println(NO_COMMON_MAPPINGS);
        } else {
            for (String courseCode : commonCourseCodes) {
                uni1Modules.stream()
                        .filter(module -> module.startsWith(courseCode + " | "))
                        .forEach(System.out::println);

                uni2Modules.stream()
                        .filter(module -> module.startsWith(courseCode + " | "))
                        .forEach(System.out::println);
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

    public void printUniqueMappings(String university, List<String> modules, Set<String> uniqueCourseCodes){
        System.out.println("\n" + UNIQUE_MAPPINGS_HEADER + university + ":");
        System.out.println(LINE_SEPARATOR);
        if (uniqueCourseCodes.isEmpty()) {
            System.out.println(NO_UNIQUE_MAPPINGS + university);
        } else {
            for (String courseCode : uniqueCourseCodes) {
                modules.stream()
                        .filter(module -> module.startsWith(courseCode + " | "))
                        .forEach(System.out::println);
            }
        }
        System.out.println(LINE_SEPARATOR);
    }

    public void printFoundCourses(String foundCourse) {
        System.out.println(foundCourse);
    }

    public void printLineSeparator() {
        System.out.println(LINE_SEPARATOR);
    }

    public void printEmptyList() {
        System.out.println("The list is empty.\nPlease make sure there is mapped courses in your tracker.");
    }
}

package seedu.exchangecoursemapper.storage;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.exchangecoursemapper.courses.Course;

public class Storage {
    private static final String MYLIST_RESOURCE_PATH = "/myList.json";  // Path inside the JAR
    private static final String USER_HOME = System.getProperty("user.home");
    private static final String MYLIST_FILE_PATH = USER_HOME + "/myList.json";  // User's writable file path
    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final List<String> cachedCourses;

    public Storage() {
        this.cachedCourses = new ArrayList<>();
        loadInitialCourses();
        loadCoursesFromFile();
    }

    private void loadInitialCourses() {
        try (InputStream inputStream = getClass().getResourceAsStream(MYLIST_RESOURCE_PATH)) {
            if (inputStream == null) {
                logger.log(Level.SEVERE, "Resource myList.json not found in JAR");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cachedCourses.add(line);
                }
                logger.log(Level.INFO, "Loaded initial courses from embedded myList.json, total courses: {0}", cachedCourses.size());
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load initial courses from myList.json resource", e);
        }
    }

    private void loadCoursesFromFile() {
        Path path = Paths.get(MYLIST_FILE_PATH);
        if (Files.exists(path)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(MYLIST_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    cachedCourses.add(line);
                }
                logger.log(Level.INFO, "Loaded courses from myList.json file, total courses: {0}", cachedCourses.size());
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Failed to load courses from myList.json file", e);
            }
        }
    }

    public void addCourse(Course course) {
        assert course != null : "Course cannot be null";
        String courseEntry = formatCourseEntry(course);
        cachedCourses.add(courseEntry); // Add to cached list
        saveAllCourses(); // Save to file
        logger.log(Level.INFO, "Successfully added course: {0}", courseEntry);
    }

    public void deleteCourse(int index) {
        if (index < 0 || index >= cachedCourses.size()) {
            logger.log(Level.WARNING, "Invalid index for deletion: {0}", index);
            return;
        }

        cachedCourses.remove(index);
        logger.log(Level.INFO, "Deleted course at index {0}", index);
        saveAllCourses(); // Save updated list to file
    }

    public Course getCourse(int index) {
        if (index < 0 || index >= cachedCourses.size()) {
            throw new IndexOutOfBoundsException("Course index out of bounds");
        }

        String courseLine = cachedCourses.get(index);
        logger.log(Level.INFO, "Retrieved course at index {0}: {1}", new Object[]{index, courseLine});

        String[] parts = courseLine.split(" \\| ");
        assert parts.length == 3 : "Course entry must contain exactly 3 parts";
        return new Course(parts[2], parts[0], parts[1]);
    }

    public List<String> getAllCourses() {
        return new ArrayList<>(cachedCourses); // Return a copy to avoid external modifications
    }

    private void saveAllCourses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MYLIST_FILE_PATH))) {
            for (String course : cachedCourses) {
                writer.write(course);
                writer.newLine();
            }
            logger.log(Level.INFO, "Saved all courses to myList.json, total courses: {0}", cachedCourses.size());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to save courses to myList.json file", e);
        }
    }

    private String formatCourseEntry(Course course) {
        assert course != null : "Course cannot be null";
        String formattedEntry = course.getNusCourseCode() + " | "
                + course.getPartnerUniversity() + " | "
                + course.getPuCourseCode();
        logger.log(Level.INFO, "Formatted course entry: {0}", formattedEntry);
        return formattedEntry;
    }
}

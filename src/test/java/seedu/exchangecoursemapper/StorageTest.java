package seedu.exchangecoursemapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.exchangecoursemapper.courses.Course;
import seedu.exchangecoursemapper.storage.Storage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageTest {

    private static final String TEST_FILE_PATH = "./data/test_myList.json";
    private Storage storage;

    @BeforeEach
    void setUp() {
        // Initialize Storage with a temporary test file
        storage = new Storage(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Delete the temporary test file after each test
        Files.deleteIfExists(Path.of(TEST_FILE_PATH));
    }

    @Test
    void addCourse_validCourse_courseAddedSuccessfully() {
        Course course = new Course("INFO20003", "CS2102", "The University of Melbourne");
        storage.addCourse(course);

        // Verify the course is saved correctly
        List<String> courses = storage.getAllCourses();
        assertEquals(1, courses.size());
        assertEquals("CS2102 | The University of Melbourne | INFO20003", courses.get(0));
    }

    @Test
    void getCourse_validIndex_correctCourseReturned() {
        Course course = new Course("INFO20003", "CS2102", "The University of Melbourne");
        storage.addCourse(course);

        // Retrieve and check the course details
        Course retrievedCourse = storage.getCourse(0);
        assertEquals("CS2102", retrievedCourse.getNusCourseCode());
        assertEquals("The University of Melbourne", retrievedCourse.getPartnerUniversity());
        assertEquals("INFO20003", retrievedCourse.getPuCourseCode());
    }

    @Test
    void deleteCourse_validIndex_courseDeletedSuccessfully() {
        Course course1 = new Course("INFO20003", "CS2102", "The University of Melbourne");
        Course course2 = new Course("COMP3670", "CS3244", "The Australian National University");
        storage.addCourse(course1);
        storage.addCourse(course2);

        // Delete first course and verify the second one shifts up
        storage.deleteCourse(0);
        List<String> courses = storage.getAllCourses();
        assertEquals(1, courses.size());
        assertEquals("CS3244 | The Australian National University | COMP3670", courses.get(0));
    }

    @Test
    void getCourse_invalidIndex_throwsIndexOutOfBoundsException() {
        // Verify exception is thrown for invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> storage.getCourse(0));
    }

    @Test
    void initializeMyList_nonexistentFile_createsNewFile() throws Exception {
        // Check if file is created if it doesn’t exist
        Files.deleteIfExists(Path.of(TEST_FILE_PATH)); // Ensure the file does not exist initially
        storage = new Storage(TEST_FILE_PATH);
        assertTrue(Files.exists(Paths.get(TEST_FILE_PATH)));
    }
}

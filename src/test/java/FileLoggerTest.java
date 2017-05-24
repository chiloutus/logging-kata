import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.glynam.www.DateProvider;
import org.glynam.www.FileLogger;
import org.glynam.www.FileSystem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileLoggerTest {


    @Mock
    private FileSystem fileSystem;

    @Mock
    private DateProvider dateProvider;

    private static final String MESSAGE = "This is a test";
    private static final String CURRENT_DATE_FORMATTED = "20170523";
    private static final String FILE_NAME = "log" + CURRENT_DATE_FORMATTED + ".txt";
    private static final String WEEKEND_FILE = "weekend.txt";

    private FileLogger fileLogger;

    @Before
    public void setUp() throws Exception {
        fileLogger = new FileLogger(fileSystem, dateProvider);
        when(dateProvider.getFormattedDate()).thenReturn(CURRENT_DATE_FORMATTED);
    }

    @Test
    public void testFileCreatedIfNotExists() {
        when(fileSystem.fileExists(FILE_NAME)).thenReturn(false);
        fileLogger.log(MESSAGE);
        verify(fileSystem).create(FILE_NAME);
    }

    @Test
    public void testFileIsAppendedAfterCreation() {
        when(fileSystem.fileExists(FILE_NAME)).thenReturn(false);
        fileLogger.log(MESSAGE);
        verify(fileSystem).append(MESSAGE);
    }

    @Test
    public void testFileIsNotRecreatedIfExists() {
        when(fileSystem.fileExists(FILE_NAME)).thenReturn(true);
        fileLogger.log(MESSAGE);
        verify(fileSystem, never()).create(FILE_NAME);
    }

    @Test
    public void testFileIsAppendedWhenExists() {
        when(fileSystem.fileExists(FILE_NAME)).thenReturn(true);
        fileLogger.log(MESSAGE);
        verify(fileSystem).append(MESSAGE);
    }

    @Test
    public void testWeekendsFileCheckedOnWeekends() {
        when(dateProvider.isWeekend()).thenReturn(true);
        fileLogger.log(MESSAGE);
        verify(fileSystem).fileExists(WEEKEND_FILE);
    }

    @Test
    public void testLogOnWeekends() {
        when(dateProvider.isWeekend()).thenReturn(true);
        fileLogger.log(MESSAGE);
        verify(fileSystem).append(MESSAGE);
    }

}

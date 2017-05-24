package org.glynam.www;

public class FileLogger {

    private final FileSystem fileSystem;
    private final DateProvider dateProvider;
    private static final String FILE_PREFIX = "log";
    private static final String FILE_TYPE = ".txt";
    private static final String WEEKEND_FILE = "weekend.txt";


    public FileLogger(FileSystem fileSystem, DateProvider dateProvider) {
        this.fileSystem = fileSystem;
        this.dateProvider = dateProvider;
    }

    public void log(String message) {

        if(fileSystem.fileExists(buildFileName())) {
            fileSystem.append(message);
        } else {
            fileSystem.create(buildFileName());
            fileSystem.append(message);
        }


    }

    private String buildFileName() {
        if(dateProvider.isWeekend()) {
            return WEEKEND_FILE;
        }
        return FILE_PREFIX + dateProvider.getFormattedDate() + FILE_TYPE;
    }
}

package org.glynam.www;

public interface FileSystem {

    boolean fileExists(String fileName);

    boolean create(String fileName);

    void append(String message);
}

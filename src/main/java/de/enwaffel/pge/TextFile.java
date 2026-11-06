package de.enwaffel.pge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public record TextFile(File file) {

    public TextFile(String path) {
        this(new File(path));
    }

    public String readAll() {
        try {
            return Files.readString(file.toPath());
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public List<String> readLines() {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

}

package org.testcontainers;

import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public abstract class AbstractJarFileTest {

    public static Path root;

    static {
        try {
            Path jarFilePath = Paths.get(System.getProperty("jarFile"));
            URI jarFileUri = new URI("jar", jarFilePath.toUri().toString(), null);
            FileSystem fileSystem = FileSystems.newFileSystem(jarFileUri, Collections.emptyMap());
            root = fileSystem.getPath("/");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

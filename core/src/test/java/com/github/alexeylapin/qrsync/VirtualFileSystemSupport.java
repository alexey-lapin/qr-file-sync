package com.github.alexeylapin.qrsync;

import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.file.FileSystem;

public abstract class VirtualFileSystemSupport {

    protected FileSystem fileSystem;

    @BeforeEach
    void setUp() {
        fileSystem = Jimfs.newFileSystem();
    }

    @AfterEach
    void tearDown() throws IOException {
        fileSystem.close();
    }

    protected void fillSequenced(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
    }

}

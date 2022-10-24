package com.danifoldi.microbase.util;

import com.danifoldi.dml.DmlParser;
import com.danifoldi.dml.exception.DmlParseException;
import com.danifoldi.dml.type.DmlObject;
import com.danifoldi.dml.type.DmlValue;
import com.danifoldi.microbase.Microbase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {
    public static @NotNull DmlValue ensureDmlFile(final @NotNull Path folder, final @NotNull String fileName) throws IOException, DmlParseException {
        ensureFolder(folder);
        final @NotNull Path dest = folder.resolve(fileName);

        if (Files.notExists(dest)) {
            try (final @Nullable InputStream stream = FileUtil.class.getResourceAsStream("/" + fileName)) {
                Files.copy(stream, dest);
            }
        }

        return DmlParser.parse(dest);
    }

    public static boolean ensureFolder(final @NotNull Path folder) throws IOException {
        if (Files.notExists(folder)) {
            Files.createDirectories(folder);
            return true;
        }
        return false;
    }

    private FileUtil() {
        throw new UnsupportedOperationException();
    }
}

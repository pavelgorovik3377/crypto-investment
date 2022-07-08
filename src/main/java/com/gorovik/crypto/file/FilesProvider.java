package com.gorovik.crypto.file;

import com.gorovik.crypto.exception.FileParsingException;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilesProvider {

    private final String path;

    public FilesProvider(String path) {
        this.path = path;
    }

    public List<File> getResourceFiles() {
        try {
            List<File> filenames = new ArrayList<>();
            InputStream in = getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String resource;

            while ((resource = br.readLine()) != null) {
                filenames.add(getFile(resource));
            }

            return filenames;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFile(String fileName) {
        URL resource = getResource(path + "/" + fileName);
        try {
            if (resource == null) {
                throw new FileParsingException();
            }
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new FileParsingException(e);
        }
    }

    private InputStream getResourceAsStream(String resource) {
        InputStream in = getContextClassLoader().getResourceAsStream(resource);
        return in == null ? getClass().getResourceAsStream(resource) : in;
    }

    private URL getResource(String resource) {
        URL in = getContextClassLoader().getResource(resource);
        return in == null ? getClass().getResource(resource) : in;
    }

    private ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}

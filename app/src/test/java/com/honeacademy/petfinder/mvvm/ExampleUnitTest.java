package com.honeacademy.petfinder;

import android.util.Log;

import com.honeacademy.petfinder.model.webservice.Petfinder;

import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Serializer serializer = new Persister();
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("petfinder.xml");
        File file = new File(resource.getPath());

        try {
            Petfinder example = serializer.read(Petfinder.class, file);
            example.getHeader();
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
    }
}
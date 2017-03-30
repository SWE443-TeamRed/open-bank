package org.sdmlib.openbank;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by daniel on 3/29/17.
 */
public class JsonPersistencyTest {
    @Test
    public void test1() throws Exception {
        JsonPersistency jsonPersistency = new JsonPersistency();
        jsonPersistency.toJson();
        jsonPersistency.fromJson();
    }

}
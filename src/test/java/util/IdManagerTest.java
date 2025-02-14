package util;

import org.heeqw.util.IdManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IdManagerTest {
    private IdManager idManager;

    @BeforeEach
    void setUp(){
        idManager = IdManager.getInstance();
        idManager.clear();
    }

    @Test
    void testIdRegistration(){
        assertTrue(idManager.isIdAvailable("test"));
        idManager.registerId("test");
        assertFalse(idManager.isIdAvailable("test"));
    }


}

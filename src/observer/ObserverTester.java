// v2
package observer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ObserverTester {
    @Test
    public void LoadMazeEventTest() {
        assertEquals(4,MazeEvent.Statuses.size());
        assertEquals(9,LoadMazeEvent.Statuses.size());
        MazeEventManager TestMazeEventManager = new MazeEventManager();
        LoadMazeEvent TestLoadMazeEvent = new LoadMazeEvent(TestMazeEventManager, "test");
        TestLoadMazeEvent.setStatus("OK");
        TestLoadMazeEvent.setStatus("LOAD_MAZE_OPENING_ERROR");
        boolean noExceptionThrown = true;
        try {
            TestLoadMazeEvent.setStatus("eegeseg");
        } catch (Exception e) {
            noExceptionThrown = false;
        }
        assertEquals(false, noExceptionThrown);
    }
} 
    
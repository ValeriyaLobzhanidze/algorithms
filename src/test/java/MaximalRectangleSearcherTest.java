import com.fasterxml.jackson.databind.ObjectMapper;
import max_rectange_searcher.MaximalRectangleSearcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MaximalRectangleSearcherTest {

    private MaxRectangleTestEntry[] testEntries;
    private final MaximalRectangleSearcher maximalRectangleSearcher = new MaximalRectangleSearcher();

    @Before
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/rectangles.json");
        testEntries = mapper.readValue(is, MaxRectangleTestEntry[].class);
    }

    @Test
    public void shouldReturnExpectedAnswer() {
        for (int i = 0; i < testEntries.length; i++) {
            int maxSquare = maximalRectangleSearcher.maximalRectangle(testEntries[i].getRectangle());
            Assert.assertEquals("Test on " + i + " entry failed",
                    maxSquare, testEntries[i].getMaxSquare());
        }
    }
}

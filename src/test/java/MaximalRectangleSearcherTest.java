import com.fasterxml.jackson.databind.ObjectMapper;
import max_rectange_searcher.MaximalRectangleSearcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MaximalRectangleSearcherTest {

    private TestEntry[] testRectangles;
    private final MaximalRectangleSearcher maximalRectangleSearcher = new MaximalRectangleSearcher();

    @Before
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getResourceAsStream("/rectangles.json");
        testRectangles = mapper.readValue(is, TestEntry[].class);
    }

    @Test
    public void shouldReturnMaxSquare() {
        for (int i = 0; i < testRectangles.length; i++) {
            int maxSquare = maximalRectangleSearcher.maximalRectangle(testRectangles[i].getRectangle());
            Assert.assertEquals("Test on " + i + " rectangle failed",
                    maxSquare, testRectangles[i].getMaxSquare());
        }
    }
}

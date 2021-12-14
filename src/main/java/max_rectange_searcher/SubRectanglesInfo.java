package max_rectange_searcher;

import java.util.HashMap;
import java.util.Map;

/**
 * */
public class SubRectanglesInfo {
    private final Map<Integer, RectangleParams> baseToRectangleParams = new HashMap<>();

    public Map<Integer, RectangleParams> getBaseToRectangleParams() {
        return baseToRectangleParams;
    }
}

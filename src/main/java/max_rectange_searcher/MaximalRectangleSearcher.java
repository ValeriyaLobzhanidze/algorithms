package max_rectange_searcher;

import java.util.Map;
import java.util.logging.Logger;

/**
 * My decision for task https://leetcode.com/problems/maximal-rectangle/
 *
 * For being able to find max rectangle, first of all we should teach our algorithm
 * how to search edges of rectangles.
 *
 * I consider edge as one of several conditions:
 *
 * 1) "1", "1", "0" <-- last "1" in this row is edge
 *
 * 2) "1", "1", "0"
 *    "1", "1", "0" <-- last "1" in this row is edge
 *
 * 3) "1", "1", "0"
 *    "1", "1", "1" <-- second "1" in this row is edge, last "1" in this row is also edge
 *
 * 4) "1", "1", "1"
 *    "1", "1", "0" <-- last "1" in this row is edge
 *
 * 5) "1", "0", "1"
 *    "1", "1", "1" <-- second "1" in this row is edge
 *
 * 6) "1", "0", "1"
 *    "1", "1", "0" <-- second "1" in this row is edge
 *
 * After we will find edge, we should:
 *
 * 1) save current length of the sequence, which led us to this edge in special auxiliary matrix
 * (every time we found new "1", we save current length of sequence by adding
 * value from previous cell).
 *
 * 2) fill data about all sub rectangles for current cell in another auxiliary matrix
 *  2.1) if i == 0, we just save in current cell one sub rectangle with square == currentSequenceLength
 *      and base == currentSequenceLength (base is the width of rectangle)
 *  2.2) if i != 0, we extract info about sub rectangles from upper cell:
 *      2.2.1) for all sub rectangle which has base <= currentSequenceLength we += their base and store as a
 *             sub rectangle in cur cell
 *      2.2.2) for all sub rectangles which has base >= currentSequenceLength we found its height and
 *             *= currentSequenceLength
 *
 * 3) every time we found new sub rectangle we check if its square > maxRectangle and update if it is
 */
public class MaximalRectangleSearcher {

    private static final Logger LOGGER = Logger.getLogger(MaximalRectangleSearcher.class.getName());

    public int maximalRectangle(char[][] matrix) {
        int maxRectangle = 0;
        int[][] lenOfSequenceMatrix = new int[matrix.length][matrix[0].length];
        SubRectanglesInfo[][] subRectanglesInfoMatrix = new SubRectanglesInfo[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {

                if (matrix[i][j] == '1') {
                    lenOfSequenceMatrix[i][j] = 1;
                    if (j != 0) {
                        lenOfSequenceMatrix[i][j] = lenOfSequenceMatrix[i][j] + lenOfSequenceMatrix[i][j - 1];
                    }
                }

                if (isEdgeOfRectangle(i, j, matrix)) {
                    int curRectangleBase = lenOfSequenceMatrix[i][j];
                    SubRectanglesInfo curCell = new SubRectanglesInfo();
                    curCell.getBaseToRectangleParams().put(curRectangleBase,
                            new RectangleParams(curRectangleBase, curRectangleBase));

                    LOGGER.info("Found new line with len: " + curRectangleBase + "\ni = " + i + ";j = " + j);

                    int newSquare = curRectangleBase;
                    if (newSquare > maxRectangle) {
                        maxRectangle = newSquare;
                        LOGGER.info("Current max rectangle: " + maxRectangle);
                    }

                    SubRectanglesInfo upCell = i != 0 ? subRectanglesInfoMatrix[i - 1][j] : null;
                    if (upCell != null) {
                        Map<Integer, RectangleParams> baseToRectangleParams = upCell.getBaseToRectangleParams();

                        for (Map.Entry<Integer, RectangleParams> upRectangle : baseToRectangleParams.entrySet()) {
                            int upRectangleBase = upRectangle.getKey();
                            int upRectangleSquare = upRectangle.getValue().getSquare();

                            if (upRectangleBase <= curRectangleBase) {
                                newSquare = upRectangleSquare + upRectangleBase;
                                curCell.getBaseToRectangleParams()
                                        .put(upRectangleBase, new RectangleParams(upRectangleBase, newSquare));

                            } else {
                                int upRectangleHeight = upRectangleSquare / upRectangleBase;
                                newSquare = (upRectangleHeight + 1) * curRectangleBase;
                                curCell.getBaseToRectangleParams()
                                        .put(curRectangleBase, new RectangleParams(curRectangleBase, newSquare));
                            }

                            if (newSquare > maxRectangle) {
                                maxRectangle = newSquare;
                                LOGGER.info("Current max rectangle: " + maxRectangle);
                            }
                        }
                    }

                    subRectanglesInfoMatrix[i][j] = curCell;
                }
            }
        }

        LOGGER.info("Found max rectangle: " + maxRectangle + "\n\n");
        return maxRectangle;
    }

    private boolean isEdgeOfRectangle(int i, int j, char[][] matrix) {
        return isEdgeInsideFirstRow(i, j, matrix)
                || isEdgeUnderZero(i, j, matrix)
                || isEdgeUnderNonZero(i, j, matrix);
    }

    private boolean isEdgeUnderZero(int i, int j, char[][] matrix) {
        return isCurCellNonZero(i, j, matrix) && isUpCellZero(i, j, matrix);
    }

    private boolean isEdgeUnderNonZero(int i, int j, char[][] matrix) {
        return (isCurCellNonZero(i, j, matrix) && isUpCellNonZero(i, j, matrix))
                && (isNextCellZeroOrEndOfRow(i, j, matrix) || isUpRightCellZeroOrEndOfRow(i, j, matrix));
    }

    private boolean isEdgeInsideFirstRow(int i, int j, char[][] matrix) {
        return i == 0 && isCurCellNonZero(i, j, matrix) && isNextCellZeroOrEndOfRow(i, j, matrix);
    }

    private boolean isNextCellZeroOrEndOfRow(int i, int j, char[][] matrix) {
        return j == matrix[0].length - 1 || matrix[i][j + 1] == '0';
    }

    private boolean isUpCellZero(int i, int j, char[][] matrix) {
        return i != 0 && matrix[i - 1][j] == '0';
    }

    private boolean isUpCellNonZero(int i, int j, char[][] matrix) {
        return i != 0 && matrix[i - 1][j] == '1';
    }

    private boolean isUpRightCellZeroOrEndOfRow(int i, int j, char[][] matrix) {
        return i != 0 && j != matrix[0].length - 1 && matrix[i - 1][j + 1] == '0';
    }

    private boolean isCurCellNonZero(int i, int j, char[][] matrix) {
        return matrix[i][j] == '1';
    }

}

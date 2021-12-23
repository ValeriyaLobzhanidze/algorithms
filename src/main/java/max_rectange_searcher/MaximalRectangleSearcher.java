package max_rectange_searcher;

import java.util.ArrayList;
import java.util.List;

/**
 * My decision for task https://leetcode.com/problems/maximal-rectangle/
 */
public class MaximalRectangleSearcher {

    public int maximalRectangle(char[][] matrix) {
        int maxRectangle = 0;

        List<Rectangle> prevRectangleList = new ArrayList<>();
        List<Rectangle> curRectangleList = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {

            Rectangle curRectangle = null;
            for (int j = 0; j < matrix[0].length; j++) {

                if (isStartOfLine(i, j, matrix)) {
                    curRectangle = new Rectangle();
                    curRectangle.setStartX(j);
                }

                if (isEndOfLine(i, j, matrix)) {
                    assert curRectangle != null;
                    curRectangle.setEndX(j);
                    curRectangle.setHeight(1);

                    int lineLength = curRectangle.getWidth();
                    if (lineLength > maxRectangle) {
                        maxRectangle = lineLength;
                    }

                    curRectangleList.add(curRectangle);

                    for (Rectangle upperRectangle : prevRectangleList) {
                        if (upperRectangle.intersectsWith(curRectangle)) {
                            Rectangle newRectangle;
                            if (upperRectangle.inside(curRectangle) || curRectangle.inside(upperRectangle)) {
                                Rectangle minRectangle = upperRectangle.getWidth() < curRectangle.getWidth() ? upperRectangle : curRectangle;
                                newRectangle = new Rectangle(minRectangle.getStartX(), minRectangle.getEndX(), upperRectangle.getHeight() + 1);

                            } else {
                                Rectangle rightRectangle = curRectangle.getEndX() >= upperRectangle.getEndX() ? curRectangle : upperRectangle;
                                Rectangle leftRectangle = rightRectangle.equals(curRectangle) ? upperRectangle : curRectangle;
                                int overlap = leftRectangle.getEndX() - rightRectangle.getStartX();
                                newRectangle = new Rectangle(rightRectangle.getStartX(), rightRectangle.getStartX() + overlap, upperRectangle.getHeight() + 1);
                            }

                            curRectangleList.add(newRectangle);
                            if (newRectangle.hasTheSameXCordsWith(curRectangle)) {
                                curRectangleList.remove(curRectangle);
                            }

                            if (newRectangle.getSquare() > maxRectangle) {
                                maxRectangle = newRectangle.getSquare();
                            }
                        }
                    }
                }
            }

            prevRectangleList = curRectangle != null ? curRectangleList : new ArrayList<>();
            curRectangleList = new ArrayList<>();
        }

        return maxRectangle;
    }

    private boolean isEndOfLine(int i, int j, char[][] matrix) {
        return matrix[i][j] == '1' && (j == matrix[0].length - 1 || matrix[i][j + 1] == '0');
    }

    private boolean isStartOfLine(int i, int j, char[][] matrix) {
        return matrix[i][j] == '1' && (j == 0 || matrix[i][j - 1] == '0');
    }
}

/**
 * A 2D cartesian plane implemented as with an array. Each (x,y) coordinate can
 * hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 */
public class ArrayCartesianPlane<T> implements CartesianPlane<T> {

    // A 2D cartesian plane can hold a single item of type <T>
    private T[][] arrayCartesianPlane;
    private int minX, maxX, minY, maxY;

    /**
     * Constructs a new ArrayCartesianPlane object with given minimum and
     * maximum bounds.
     *
     * Note that these bounds are allowed to be negative.
     *
     * @param minimumX A new minimum bound for the x values of
     *         elements.
     * @param maximumX A new maximum bound for the x values of
     *         elements.
     * @param minimumY A new minimum bound for the y values of
     *         elements.
     * @param maximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max)
     */
    public ArrayCartesianPlane(int minimumX, int maximumX, int minimumY,
            int maximumY) throws IllegalArgumentException {
        // TODO: implement the constructor
        minX = minimumX;
        minY = minimumY;
        maxX = maximumX;
        maxY = maximumY;
        int width = maximumX - minimumX + 1;
        int height = maximumY - minimumY + 1;

        if(width >= 0 && height >= 0) {
            arrayCartesianPlane = (T[][])new Object[width][height];

        } else {
            throw new IllegalArgumentException();
        }

    }

    // TODO: you are to implement all of ArrayCartesianPlanes's methods here

    /**
     * Add an element at a fixed position, overriding any existing element
     * there.
     *
     * @param x The x-coordinate of the element's position
     * @param y The y-coordinate of the element's position
     * @param element The element to be added at the indicated
     *         position
     * @throws IllegalArgumentException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public void add(int x, int y, T element) throws IllegalArgumentException {
        int tfx = x - minX;
        int tfy = y - minY;


        if (x < minX || x > maxX || y < minY || y > maxY) {
            throw new IllegalArgumentException();
        } else {
            arrayCartesianPlane[tfx][tfy] = element;
        }

    }

    /**
     * Returns the element at the indicated position.
     *
     * @param x The x-coordinate of the element to retrieve
     * @param y The y-coordinate of the element to retrieve
     * @return The element at this position, or null is no elements exist
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public T get(int x, int y) throws IndexOutOfBoundsException {
        int tfx = x - minX;
        int tfy = y - minY;

        if (x < minX || x > maxX || y < minY || y > maxY) {
            throw new IndexOutOfBoundsException();
        } else {
            return arrayCartesianPlane[tfx][tfy];
        }

    }

    /**
     * Removes the element at the indicated position.
     *
     * @param x The x-coordinate of the element to remove
     * @param y The y-coordinate of the element to remove
     * @return true if an element was successfully removed, false if no element
     *         exists at (x, y)
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public boolean remove(int x, int y) throws IndexOutOfBoundsException {
        int tfx = x - minX;
        int tfy = y - minY;


        if (x < minX || x > maxX || y < minY || y > maxY) {
            throw new IndexOutOfBoundsException();
        } else {
            if (arrayCartesianPlane[tfx][tfy] == null) {
                return false;
            } else {
                arrayCartesianPlane[tfx][tfy] = null;
                return true;
            }
        }
    }

    /**
     * Removes all elements stored in the grid.
     */
    public void clear() {

        for (int i = 0; i < arrayCartesianPlane.length; i++) {
            for (int a = 0; a < arrayCartesianPlane[i].length; a++) {
                arrayCartesianPlane[i][a] = null;
            }
        }
    }

    /**
     * Changes the size of the grid. Existing elements should remain at the
     * same (x, y) coordinate If a resizing operation has invalid dimensions or
     * causes an element to be lost, the grid should remain unmodified and an
     * IllegalArgumentException thrown
     *
     * @param newMinimumX A new minimum bound for the x values of
     *         elements.
     * @param newMaximumX A new maximum bound for the x values of
     *         elements.
     * @param newMinimumY A new minimum bound for the y values of
     *         elements.
     * @param newMaximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max) or if an element
     *         would be lost after this resizing operation
     */
    public void resize(int newMinimumX, int newMaximumX, int newMinimumY,
                       int newMaximumY) throws IllegalArgumentException {

        if (newMinimumX > newMaximumX || newMinimumY > newMaximumY) {
            throw new IllegalArgumentException();
        }

        T[][] copy;
        int width = newMaximumX - newMinimumX + 1;
        int height = newMaximumY - newMinimumY + 1;


        if (width >= 0 && height >= 0) {
            copy = (T[][])new Object[maxX - minX + 1][maxY - minY + 1];
        } else {
            throw new IllegalArgumentException();
        }


        for (int i = 0; i < arrayCartesianPlane.length; i++) {
            for (int a = 0; a < arrayCartesianPlane[i].length; a++) {
                if (i >= width || a >= height) {
                    if (arrayCartesianPlane[i][a] != null) {
                        throw new IllegalArgumentException();
                    } else {
                        copy[i][a] = arrayCartesianPlane[i][a];
                    }
                } else {
                    copy[i][a] = arrayCartesianPlane[i][a];
                }
            }
        }

        arrayCartesianPlane = (T[][])new Object[width][height];
        minX = newMinimumX;
        minY = newMinimumY;
        maxX = newMaximumX;
        maxY = newMaximumY;

        for (int i = 0; i < arrayCartesianPlane.length; i++) {
            for (int a = 0; a < arrayCartesianPlane[i].length; a++) {
                if (i >= copy.length || a >= copy[i].length) {
                    arrayCartesianPlane[i][a] = null;
                } else {
                    arrayCartesianPlane[i][a] = copy[i][a];
                }
            }
        }
    }
}


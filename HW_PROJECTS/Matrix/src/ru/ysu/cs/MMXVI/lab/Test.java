package ru.ysu.cs.MMXVI.lab;

/**
 * Created by ilavrentev on 12.10.2016.
 */
public class Test {

    private static void assertQq(Object o1, Object o2){
        if(!o1.equals(o2)){
            throw new RuntimeException("TEST FAILED!!!");
        }
    }
    private static void assertQqDouble(Object o1, Object o2){
        if(Math.abs((Double)o1 - (Double)o2) >= 0.000000000001){
            throw new RuntimeException("TEST FAILED!!!");
        }
    }

    public static void main(String[] args) throws MatrixIsNotSquareException, MatrixZeroDeterminantException {
        testMatrix1();
        testMatrix2();
        testMatrix3();
        testMatrix4();
        testMatrix5();

        System.out.println("All tests passed!");
    }

    private static void testMatrix1(){
        Matrix m = new Matrix(100,200);
        assertQq(m.getRowsCount(), 100);
        assertQq(m.getColumnsCount(), 200);
    }
    private static void testMatrix2(){
        Matrix m = new Matrix(100,100);
        m.setElement(10,25, 25.02);
        assertQq(m.getRowsCount(), 100);
        assertQq(m.getColumnsCount(), 100);
        assertQqDouble(m.getElement(10,25), 25.02);
        assertQqDouble(m.getElement(10,26), 0.00);
    }
    private static void testMatrix3() throws MatrixIsNotSquareException {
        Matrix m = new Matrix(2,2);
        m.setElement(1,1, 2d);
        m.setElement(1,2, 3d);
        m.setElement(2,1, 4d);
        m.setElement(2,2, 7d);
        Matrix sq = MatrixFunc.squaredOf(m);
        assertQqDouble(sq.getElement(1,1), 16d);
        assertQqDouble(sq.getElement(1,2), 27d);
        assertQqDouble(sq.getElement(2,1), 36d);
        assertQqDouble(sq.getElement(2,2), 61d);
        Matrix cb = MatrixFunc.cubedOf(m);
        assertQqDouble(cb.getElement(1,1), 140d);
        assertQqDouble(cb.getElement(1,2), 237d);
        assertQqDouble(cb.getElement(2,1), 316d);
        assertQqDouble(cb.getElement(2,2), 535d);
    }
    private static void testMatrix4() throws MatrixIsNotSquareException {
        Matrix m = new Matrix(2,2);
        m.setElement(1,1, 2d);
        m.setElement(1,2, 3d);
        m.setElement(2,1, 4d);
        m.setElement(2,2, 7d);
        assertQq(MatrixFunc.detOf(m), 2d);
    }
    private static void testMatrix5() throws MatrixIsNotSquareException, MatrixZeroDeterminantException {
        Matrix m = new Matrix(2,2);
        m.setElement(1,1, 2d);
        m.setElement(1,2, 3d);
        m.setElement(2,1, 4d);
        m.setElement(2,2, 7d);
        Matrix inv = MatrixFunc.inversedOf(m);
        assertQqDouble(inv.getElement(1,1), 7d/2);
        assertQqDouble(inv.getElement(1,2), -3d/2);
        assertQqDouble(inv.getElement(2,1), -2d);
        assertQqDouble(inv.getElement(2,2), 1d);
    }
}

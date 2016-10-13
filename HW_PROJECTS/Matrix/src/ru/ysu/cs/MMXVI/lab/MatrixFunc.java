package ru.ysu.cs.MMXVI.lab;

/**
 * Created by ilavrentev on 12.10.2016.
 */
public class MatrixFunc {

    public static Matrix squaredOf(Matrix src) throws MatrixIsNotSquareException{
        try {
            Matrix res = mul(src, src);
            return res;
        } catch(MatrixDimensionNotMatchException x) {
            throw new MatrixIsNotSquareException("Matrix is not square dimension");
        }
    }
    public static Matrix cubedOf(Matrix src) throws MatrixIsNotSquareException{
        try {
            Matrix res = mul(mul(src, src), src);
            return res;
        } catch(MatrixDimensionNotMatchException x) {
            throw new MatrixIsNotSquareException("Matrix is not square dimension");
        }
    }
    public static Double detOf(Matrix src) throws MatrixIsNotSquareException{
        if(src.getRowsCount()!=src.getColumnsCount()){
            throw new MatrixIsNotSquareException("Matrix is not square dimension");
        }
        return det(src);
    }

    private static Double det(Matrix m){
        if(m.getColumnsCount()==1 && m.getRowsCount()==1){
            return m.getElement(1,1);
        } else {
            Double det = 0.0;
            for(int j = 1; j <= m.getColumnsCount(); j++){
                Double m_1j = m.getElement(1, j);
                det += (j%2==0?-1:1) * m_1j * det(m.crissCrossRowCol(1, j));
            }
            return det;
        }
    }

    public static Matrix inversedOf(Matrix src) throws MatrixIsNotSquareException, MatrixZeroDeterminantException{
        Double d = detOf(src);
        if(Matrix.isDoublesEq(d, 0.0)){
            throw new MatrixZeroDeterminantException("Inversed matrix is not exist. Determinant is 0");
        }
        Matrix res = new Matrix(src.getRowsCount(), src.getRowsCount());
        for(int i = 1; i <= src.getRowsCount(); i++){
            for(int j = 1; j <= src.getColumnsCount(); j++){
                Double A_ij = ((i+j)%2==0?1:-1) * det(src.crissCrossRowCol(i,j));
                res.setElement(j, i, A_ij/d);
            }
        }
        return res;
    }


    private static Matrix mul(Matrix a, Matrix b) throws MatrixDimensionNotMatchException{
        int an = a.getRowsCount();
        int am = a.getColumnsCount();
        int bn = b.getRowsCount();
        int bm = b.getColumnsCount();
        if(am != bn){
            throw new MatrixDimensionNotMatchException(
                    String.format("Matrix with dimensions %d x %d can not be multiply by matrix with dimensions %d x %d", an, am, bn, am)
            );
        }
        Matrix res = new Matrix(an, bm);
        for(int i = 1; i <= an; i++){
            for(int j = 1; j <= bm; j++){
                Double sum = 0.0;
                for(int k = 1; k <= am; k++){
                    sum += (a.getElement(i, k) * b.getElement(k, j));
                }
                res.setElement(i,j, sum);
            }
        }
        return res;
    }
}

package ru.ysu.cs.MMXVI.lab;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ilavrentev on 12.10.2016.
 */
public class Matrix {

    private static final Double EPS = 0.000000000001;

    private Map<Indicies, Double> storage;
    private int rowsCount;
    private int columnsCount;

    public Matrix(int rowsCount, int columnsCount){
        this.storage = new HashMap<Indicies, Double>();
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
    }

    public int getRowsCount(){
        return this.rowsCount;
    }

    public int getColumnsCount(){
        return this.columnsCount;
    }

    public void setElement(int i, int j, Double value){
        if(isDoublesEq(value, 0.0)){
            storage.remove(Indicies.createIndicies(i,j));
        } else {
            storage.put(Indicies.createIndicies(i,j), value);
        }
    }

    public Double getElement(int i, int j){
        Double val =  storage.get(Indicies.createIndicies(i, j));
        if(val == null){
            return 0.0;
        } else {
            return val;
        }
    }

    public Double[][] toArray(){
        Double[][] res = new Double[this.rowsCount][this.columnsCount];
        for(int i = 1; i <= rowsCount; i++){
            for(int j = 1; j <= this.columnsCount; j++){
                res[i-1][j-1] = getElement(i,j);
            }
        }
        return res;
    }

    public Matrix crissCrossRowCol(int row, int col){
        Matrix res = new Matrix(this.rowsCount-1, this.columnsCount-1);
        for(int i = 1 ; i <= this.rowsCount; i++) {
            int to_i;
            if (i < row) {
                to_i = i;
            } else if (i == row) {
                continue;
            } else {
                to_i = i - 1;
            }
            for (int j = 1; j <= this.columnsCount; j++) {
                int to_j;
                if (j < col) {
                    to_j = j;
                } else if (j == col) {
                    continue;
                } else {
                    to_j = j - 1;
                }
                res.setElement(to_i, to_j, this.getElement(i, j));
            }
        }
        return res;
    }

    public static boolean isDoublesEq(Double a, Double b){
        if(Math.abs(a-b) < EPS){
            return true;
        } else {
            return false;
        }
    }

    private static class Indicies{
        private Integer i;
        private Integer j;

        public static Indicies createIndicies(Integer i, Integer j){
            Indicies ind = new Indicies();
            ind.i = i;
            ind.j = j;
            return ind;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null){
                return false;
            } else {
                if(obj instanceof  Indicies){
                    Indicies t = (Indicies)obj;
                    if(t.i.equals(this.i) && t.j.equals(this.j)){
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        @Override
        public int hashCode() {
            return (String.format("%10d", this.i) + String.format("%10d", this.j)).hashCode();
        }
    }
}

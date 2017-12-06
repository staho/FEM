package Model.Maths;

import java.util.Vector;

public class Matrix {
    int n;      //height of matrix
    int m;      //width of matrix

    Vector<Vector<Double>> matrix = null;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;

        matrix = new Vector<>(m);
        for(int i = 0; i < m; i++) matrix.add(new Vector<>(n));

    }

    public double getElement(int x, int y){
        return matrix.get(x).get(y);
    }

    public void setElementOfIndex(int x, int y, double val){
        matrix.get(x).set(y, val);
    }

    public void display(){
       for(int i = 0; i < m; i++){
           for(int j = 0; j < n; j++){
               System.out.println();
           }
       }
    }

    public double determinant(){
        if(m == n){
            switch (m){
                case 1:
                    return getElement(0, 0);
                case 2:
                    return ((getElement(0,0) * getElement(1,1)) - (getElement(1,0) * getElement(0,1)));
                case 3:
                    double el1 = getElement(0,0) * getElement(1,1) * getElement(2,2);
                    double el2 = getElement(0,1) * getElement(1,2) * getElement(2,0);
                    double el3 = getElement(0,2) * getElement(1,0) * getElement(2,1);
                    double el4 = getElement(0,2) * getElement(1,1) * getElement(2,1);
                    double el5 = getElement(1,2) * getElement(2,1) * getElement(0,0);
                    double el6 = getElement(2,2) * getElement(0,1) * getElement(1,0);
                    return el1 + el2 + el3 - el4 - el5 - el6;
                default:
                    return 0;

            }
        }else{
            throw new ArithmeticException("Matrix is not square");
        }
    }

    public Matrix multiply(Matrix matrixA, Matrix matrixB){
        if(matrixA.getWidth() == matrixB.getHeight()){

            Matrix matrixC = new Matrix(matrixA.getHeight(), matrixB.getWidth());
            double sum = 0;

            for(int i = 0; i < matrixA.getHeight(); i++){
                for(int j = 0; j < matrixB.getWidth(); j++){
                    for(int k = 0; k < matrixA.getWidth(); k++)  sum += matrixA.getElement(i, j) * matrixB.getElement(j, k);

                    matrixC.setElementOfIndex(i, j, sum);
                    sum = 0;
                }
            }
            return matrixC;
        }
        else{
            throw new ArithmeticException("Cannot multiply those arrays");
        }
    }

    public void multiplyByScalar(double x){
        for(Vector<Double> row: matrix){
            for (Double element : row){
                element *= x;
            }
        }
    }
    public Matrix add(Matrix matrixA, Matrix matrixB){
        if((matrixA.getWidth() == matrixB.getWidth()) && (matrixA.getHeight() == matrixB.getHeight())){
            Matrix sumMatrix = new Matrix(matrixA.getHeight(), matrixB.getWidth());
            for(int i = 0; i < matrixA.getHeight(); i++){
                for(int j = 0; j < matrixA.getWidth(); j++){
                    sumMatrix.setElementOfIndex(i,j, matrixA.getElement(i,j) + matrixB.getElement(i,j));
                }
            }
            return sumMatrix;
        } else {
            throw new ArithmeticException("Matrixes have different dimensions");
        }
    }

    public int getHeight() {
        return n;
    }

    public int getWidth() {
        return m;
    }
}

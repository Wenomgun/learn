package ru.ysu.cs.MMXVI.lab;

import java.io.*;

/**
 * Created by ilavrentev on 12.10.2016.
 */
public class App {

    public static void main(String[] args){

        String inFile = null;
        String outFile = null;

        if(args.length==0){
            printHelp();
            return;
        }
        if(args.length>=1){
            inFile = args[0];
        }
        if(args.length >= 2){
            outFile = args[1];
        }
        try {
            Matrix inputMtrx = readMatrixFromFile(inFile);
            String outStrings = prepareResult(inputMtrx);
            if(outFile==null){
                printResultToConsole(outStrings);
            } else {
                writeResultsToFile(outFile, outStrings);
            }
            System.out.println("Operation success.");
        } catch(MatrixException x){
            printError(x.getMessage());
        }
    }


    private static void printHelp(){
        System.out.println(" Examle run string:");
        System.out.println(" java App \"<input_file_path>\" [\"<output_file_path>\"]");
        System.out.println(" Format of <input_file>:");
        System.out.println(" ------------------------");
        System.out.println(" N;M");
        System.out.println(" i1;j1;val_i1j1");
        System.out.println(" i2;j2;val_i2j2");
        System.out.println(" ...");
        System.out.println(" ------------------------");
        System.out.println(" Where N,M - matrix rows and columns count,");
        System.out.println(" iK;jK;val_iKjK - value of element (iK,jK)");
    }

    private static void printError(String message){
        int len = message.length();
        int sl = 52;
        int cnt = len/sl+1;
        System.out.println("*******************ERROR*************************");
        for(int i = 0 ; i < cnt; i++){
            System.out.println(" "+message.substring(sl*i, Math.min(message.length(), sl*(i+1))));
        }
        System.out.println("*************************************************");
    }


    private static Matrix readMatrixFromFile(String file) throws MatrixInputException {

        try(FileReader fr = new FileReader(new File(file));
            BufferedReader br = new BufferedReader(fr);){
            String dimensions = br.readLine();
            if(dimensions==null || dimensions.trim().equals("")){
                throw new MatrixInputException("There is not dimensions in an input file");
            }
            String[] dims = dimensions.split(";");
            if(dims.length!=2){
                throw new MatrixInputException("Wrong dimensions format in an input file");
            }
            int n = Double.valueOf(dims[0].trim()).intValue();
            int m = Double.valueOf(dims[1].trim()).intValue();
            Matrix res = new Matrix(n, m);
            String s;
            while((s=br.readLine())!=null){
                String[] val = s.split(";");
                if(val.length!=3){
                    throw new MatrixInputException("Wrong element data format in an input file");
                }
                int i = Double.valueOf(val[0].trim()).intValue();
                int j = Double.valueOf(val[1].trim()).intValue();
                if(i > n || j > m){
                    throw new MatrixInputException("Element index out of bound in an input file");
                }
                Double v = Double.valueOf(val[2].trim());
                res.setElement(i, j, v);
            }
            return res;
        } catch(FileNotFoundException x){
            throw new MatrixInputException(String.format("File '%s' not found", file));
        } catch(IOException x){
            throw new MatrixInputException(String.format("Error while read file '%s'", file));
        }
    }
    private static void writeResultsToFile(String file, String result) throws MatrixInputException{
        try(FileWriter fw = new FileWriter(new File(file));
            BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(result);
        } catch (IOException x){
            throw new MatrixInputException(String.format("Error while write output file '%s'", file));
        }
    }
    private static void printResultToConsole(String result){
        System.out.println(result);
    }
    private static String prepareResult(Matrix m) throws MatrixIsNotSquareException, MatrixZeroDeterminantException {
        Matrix sq = MatrixFunc.squaredOf(m);
        Matrix cb = MatrixFunc.cubedOf(m);
        Double dt = MatrixFunc.detOf(m);

        StringBuilder sb = new StringBuilder("");
        sb.append("Source matrix is:\n");
        sb.append(fmtMatrix(m.toArray()));
        sb.append("\nSquared matrix is:\n");
        sb.append(fmtMatrix(sq.toArray()));
        sb.append("\nCubed matrix is:\n");
        sb.append(fmtMatrix(cb.toArray()));
        sb.append("\nDeterminant of source matrix is: " + String.format("%10.4f", dt));
        sb.append("\nInversed matrix is:\n");
        try {
            Matrix iv = MatrixFunc.inversedOf(m);
            sb.append(fmtMatrix(iv.toArray()));
        } catch(MatrixException x){
            sb.append(x.getMessage());
        } finally {
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String fmtMatrix(Double[][] dm){
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i < dm.length; i++){
            for(int j = 0; j< dm[i].length; j++){
                sb.append(String.format("%12.4f", dm[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

package com.itmoEdu.vmlab1;

import java.io.*;
import java.util.Random;
import java.util.StringTokenizer;
public class Main {
    public static void main(String[] args) throws IOException {
        boolean test = true;
        String num = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while(test){
            System.out.println("""
                    Выберите функцию для ввода данных:\s
                    #1 - Ввод с консоли.\s
                    #2 - Чтение файла.\s
                    #3 - Случайные знанчения для теста программы.""");
            num =  in.readLine();
            if ((num.equals("1")) || (num.equals("2")) || (num.equals("3"))){
                test = false;
            }
        }
        switch (num){
            case ("1"):
                double[][] M;
                double[] B;
                System.out.println("Введите кол-во неизвестных в уравнение:");
                int n = Integer.parseInt(in.readLine());
                M = new double[n][n];
                B = new double[n];
                System.out.println("Введите ожидаемую погрешность:");
                double eps = Double.parseDouble(in.readLine());
                System.out.println("Введите рассшириную матрицу A|B:");
                for(int i = 0; i < n; i++){
                    StringTokenizer inputMatrix = new StringTokenizer(in.readLine());
                    while (inputMatrix.hasMoreTokens())
                        for (int j = 0; j < n+1 && inputMatrix.hasMoreTokens(); j++)
                            if(j == n){
                                B[i] = Double.parseDouble(inputMatrix.nextToken());
                            }else{
                            M[i][j] = Double.parseDouble(inputMatrix.nextToken());}
                }
                Gauss_Seidel gausSeidel = new Gauss_Seidel(eps);
                gausSeidel.solve(M, B);
                break;
            case ("2"):
                System.out.println("Вы выбрали чтение");
                BufferedReader reader = new BufferedReader(new FileReader("/Users/Konstantin/dev/javaEdu/ComputationalMathematics/Lab1/untitled/src/test.txt"));
                int l = Integer.parseInt(reader.readLine());
                M = new double[l][l];
                B = new double[l];
                for(int i= 0;i < l; i++){
                    StringTokenizer inputMatrix = new StringTokenizer(reader.readLine());
                    while (inputMatrix.hasMoreTokens())
                        for (int j = 0; j < l + 1 && inputMatrix.hasMoreTokens(); j++){
                            if(j == l){
                                B[i] = Double.parseDouble(inputMatrix.nextToken());
                            }else{
                            M[i][j] = Double.parseDouble(inputMatrix.nextToken());
                            }
                        }
                }
                System.out.println("Введите допустимую погрешность");
                double eps1 = Double.parseDouble(in.readLine());
                Gauss_Seidel gausSeidel1 = new Gauss_Seidel(eps1);
                gausSeidel1.solve(M, B);
                break;
            case ("3"):
                System.out.println("Вы выбрали рандом, введите кол-во неизвестных:");
                float sum;
                int m = Integer.parseInt(in.readLine());
                M =new double[m][m];
                B = new double[m];
                //проверка на преобладание диагонали
                Random r=new Random();
                for(int i=0;i<m;i++) {
                    for (int j = 0; j < m + 1; j++) {
                        if(j == m){
                            B[i] = r.nextDouble();
                        }else{
                        M[i][j] = r.nextDouble();
                    }}
                }
                for(int i=0;i<m;i++){
                    sum = 0;
                    for(int j=0;j<m;j++){
                        if(i != j){ sum += M[i][j];}
                    }
                    if(Math.abs(M[i][i]) <= Math.abs(sum)){
                        while(Math.abs(M[i][i]) <= Math.abs(sum)){
                            M[i][i] = r.nextDouble(Math.abs(sum)/m, (Math.abs(sum)/m)+M[i][i]*1.2);
                        }
                    }
                }
                int n3 = M.length;
                System.out.println("Ваша матрица:");
                for (int i = 0; i < n3; i++)
                {
                    for (int j = 0; j < n3 + 1; j++)
                        if(j == m){
                            System.out.print(B[i] + " ");
                        }else{
                        System.out.print(M[i][j] + " ");}
                    System.out.println();
                }
                System.out.println(M.length);
                System.out.println("Введите допустимую погрешность:");
                double eps2 = Double.parseDouble(in.readLine());
                Gauss_Seidel gausSeidel2 = new Gauss_Seidel(eps2);
                gausSeidel2.solve(M, B);
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
    }
}
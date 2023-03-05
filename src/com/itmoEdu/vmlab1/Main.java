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
                try {
                    double[][] M;
                    double[] B;
                    int n;
                    System.out.println("Введите кол-во неизвестных в уравнение:");
                    n = Integer.parseInt(in.readLine());
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
                        M = new double[n][n];
                        B = new double[n];
                        Gauss_Seidel gausSeidel = new Gauss_Seidel(eps);
                        gausSeidel.solve(M, B);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                        try {
                            in.close();
                        } catch (IOException r) {
                            System.out.println(r.getMessage());
                        }
                        System.out.println("Ошибка ввода");
                }
                break;
            case ("2"):
                try{
                    double[][] M;
                    double[] B;
                    int n;
                    System.out.println("Вы выбрали чтение");
                    BufferedReader reader = new BufferedReader(new FileReader("/Users/Konstantin/dev/javaEdu/ComputationalMathematics/Lab1/untitled/src/test.txt"));
                    n = Integer.parseInt(reader.readLine());
                    M = new double[n][n];
                    B = new double[n];
                    for(int i= 0;i < n; i++){
                        StringTokenizer inputMatrix = new StringTokenizer(reader.readLine());
                        while (inputMatrix.hasMoreTokens())
                            for (int j = 0; j < n + 1 && inputMatrix.hasMoreTokens(); j++){
                                if(j == n){
                                    B[i] = Double.parseDouble(inputMatrix.nextToken());
                                }else{
                                M[i][j] = Double.parseDouble(inputMatrix.nextToken());
                                }
                            }
                    }
                    System.out.println("Введите допустимую погрешность");
                    double eps = Double.parseDouble(in.readLine());
                    Gauss_Seidel gausSeidel1 = new Gauss_Seidel(eps);
                    gausSeidel1.solve(M, B);
                } catch (FileNotFoundException e) {
                    System.out.println("File not found!");
                    try{
                        in.close();
                    }catch (IOException b){
                        throw new IOException(b.getMessage());
                    }
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case ("3"):
                try {
                    double[][] M;
                    double[] B;
                    int n;
                    System.out.println("Вы выбрали рандом, введите кол-во неизвестных:");
                    float sum;
                    n = Integer.parseInt(in.readLine());
                    M = new double[n][n];
                    B = new double[n];
                    //проверка на преобладание диагонали
                    Random r = new Random();
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n + 1; j++) {
                            if (j == n) {
                                B[i] = r.nextDouble();
                            } else {
                                M[i][j] = r.nextDouble();
                            }
                        }
                    }
                    for (int i = 0; i < n; i++) {
                        sum = 0;
                        for (int j = 0; j < n; j++) {
                            if (i != j) {
                                sum += M[i][j];
                            }
                        }
                        if (Math.abs(M[i][i]) <= Math.abs(sum)) {
                            while (Math.abs(M[i][i]) <= Math.abs(sum)) {
                                M[i][i] = r.nextDouble(Math.abs(sum) / n, (Math.abs(sum) / n) + M[i][i] * 1.2);
                            }
                        }
                    }
                    int n3 = M.length;
                    System.out.println("Ваша матрица:");
                    for (int i = 0; i < n3; i++) {
                        for (int j = 0; j < n3 + 1; j++)
                            if (j == n) {
                                System.out.print(B[i] + " ");
                            } else {
                                System.out.print(M[i][j] + " ");
                            }
                        System.out.println();
                    }
                    System.out.println(M.length);
                    System.out.println("Введите допустимую погрешность:");
                    double eps;
                    eps = Double.parseDouble(in.readLine());

                    Gauss_Seidel gausSeidel2 = new Gauss_Seidel(eps);
                    gausSeidel2.solve(M, B);
                }catch (RuntimeException e) {
                    System.out.println("Error!");
                    in.close();
                    return;
                } catch (IOException e) {
                    System.out.println("Error!");
                    in.close();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
    }
}
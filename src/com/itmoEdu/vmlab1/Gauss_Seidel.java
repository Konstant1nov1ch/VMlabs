package com.itmoEdu.vmlab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Gauss_Seidel {
    private static double[][] valA;
    private static double[] valB;
    private final double eps;
    double[] x;
    double[] p;
    int iter = 0;
    public Gauss_Seidel(double expectedly) {

        eps = expectedly;
    }
    public static boolean checkDominant(double[][] M, int n) {
        int i, j, k = 1;
        double sum;
        for(i = 0; i < n; i ++){
            sum = 0;
            for(j = 0; j < n; j ++){
                sum += Math.abs(M[i][j]);
            }
            sum-=Math.abs(M[i][i]);
            if(sum>Math.abs(M[i][i])){
                k = 0;
            }
        }if(k == 0){
            k = 1;
            for(i = 0; i < n; i ++){
                sum = 0;
                for (j = M.length - 1; j >= 0; j--) {
                    sum += Math.abs(M[i][M.length - 1 - j]);
                    sum -= Math.abs(M[j][M.length - 1 - j]);
                    if(sum>Math.abs(M[j][M.length - 1 - j])){
                        k = 0;
                    }
                }
            }
            if(k == 1){
                double [] temp;
                for(int l = 0; l < M.length/2; l++){
                    temp = M[l];
                    M[l] = M[M.length - l - 1];
                    M[M.length - l - 1] = temp;
                }
            }
        }
        return (k == 1);
    }
    private boolean tryToRewriteString(double[][] M, double[] B, int n) {
        if(n>=M.length-1){
            if(checkDominant(M, M.length)) {
                valA = new double[M.length][M.length];
                valB = new double[B.length];
                for (int i = 0; i < M.length; i++) {
                    for (int j = 0; j < M.length + 1; j++) {
                        if (j != M.length) {
                            valA[i][j] = M[i][j];
                        } else {
                            valB[i] = B[i];
                        }
                    }
                }
            }return true;
        }else{
            for (int i = n; i < M.length; i++) {
                double[] t = M[n];
                double b = B[n];
                B[n] = B[i];
                B[i]=b;
                M[n] = M[i];
                M[i] = t;
                if(valA == null){
                    tryToRewriteString(M,B, n + 1);
                }else{
                    t = M[n];
                    b = B[n];
                    B[n] = B[i];
                    B[i] = b;
                    M[n] = M[i];
                    M[i] = t;
                    return true;
                }
            }
        }
    return false;
    }
    boolean checkRes(double[] x, double[] p, int n, double eps) {
        double norm = 0;
        for(int i = 0; i < n; i++){
            norm+=(x[i] - p[i])*(x[i] - p[i]);
        }
        return (Math.sqrt(norm) <= eps);
    }
    double okr(double x, double eps){
        int i = 0;
        double newEps = eps;
        while (newEps < 1){
            i++;
            newEps*=10;
        }
        int okr = (int) Math.pow(10.0, i);
        x = (int)(x*okr + 0.5)/(double)(okr);
        return x;
    }
    public void solve(double[][] M,double[] B) throws IOException {
    //решение
        x = new double[M.length];
        p = new double[M.length];
        for(int i = 0 ; i <M.length; i ++){
            x[i] = 1;}
        if(checkDominant(M, M.length)){
            while(!checkRes(x, p, M.length, eps)){
                System.arraycopy(x, 0, p, 0, M.length);
                for(int j = 0; j < M.length; j ++){
                    double var = 0;
                    for(int i = 0; i < M.length; i ++){
                        if(i!=j){
                            var+=(M[j][i])*x[i];}}
                    x[j] = (B[j] - var)/M[j][j];}
                iter++;
                if(iter >= 100000){
                    System.out.println("Решение не достигнуто спустя 100000 иттераций(");
                    System.exit(0);
                    break;
                }
            }
            System.out.println("Решение системы:");
            System.out.println("Иттераций: " + iter);
            for(int i = 0; i < M.length; i++){
                System.out.println("x" + i + " = " + okr(x[i], eps));
            }

        }else{
            String num = "";
            boolean test = true;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while(test){
                System.out.println("""
                        Нет диагонального преобладания:\s
                        #1 - Переставить строки.\s
                        #2 - Выход из программы.""");
                num = in.readLine();
                if (num.equals("1") || num.equals("2")){
                    test = false;
                }
            }
            switch (num){
                case ("1"):
                    if(tryToRewriteString(M, B, 0)){
                        solve(valA, valB);
                    }else{
                        System.out.println("Не получилось( ");
                    }
                    break;
                case ("2"):
                    System.out.println("Отмена");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + num);
            }
        }
    }
}
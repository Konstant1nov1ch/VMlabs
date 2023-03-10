package edu.itmoEdu.vmLab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SeidelMethod {
    static boolean test = true;
    final float eps;
    float[] VectorOfAnswers;
    float[] p;
    int iter = 0;
    public SeidelMethod(float expectedly) {
        eps = expectedly;
    }
    public static boolean checkDominant(float[][] M, int n) {
        int i, j, k = 1;
        float sum;
        for(i = 0; i < n; i ++){
            sum = 0;
            for(j = 0; j < n; j ++){
                sum += Math.abs(M[i][j]);
            }
            sum-=Math.abs(M[i][i]);
            if(sum>Math.abs(M[i][i])){
                k = 0;
                break;
            }
        }if(k == 0) {
            k = 1;
            for (i = 0; i < n; i++) {
                sum = 0;
                for (j = M.length - 1; j >= 0; j--) {
                    sum += Math.abs(M[i][M.length - 1 - j]);
                }
                sum -= Math.abs(M[i][M.length - 1 - i]);
                if (sum > Math.abs(M[i][M.length - 1 - i])) {
                    k = 0;
                    break;
                }
            }
            if (k == 1) {
                float[] temp;
                for (int l = 0; l < M.length / 2; l++) {
                    temp = M[l];
                    M[l] = M[M.length - l - 1];
                    M[M.length - l - 1] = temp;
                }
            }
        }
        return (k == 1);
    }
    private boolean tryToRewriteString(float[][] M, float[] B) {
            for(int i = 0; i < M.length; i++){
                float LocalMax = Math.abs(M[i][0]);
                int CountMax;
                for(int j = i; j < M.length; j++){
                    if(LocalMax < Math.abs(M[j][i])) {
                        LocalMax = Math.max(LocalMax, Math.abs(M[j][i]));
                        CountMax = j;
                        float[] t = M[i];
                        float b = B[i];
                        M[i] = M[CountMax];
                        M[CountMax] = t;
                        B[i] = B[CountMax];
                        B[CountMax] = b;
                    }
                }
            }
    return checkDominant(M, M.length);
    }
    boolean checkRes(float[] VectorOfAnswers, float[] p, int n, float eps) {
        float norm = 0;
        for(int i = 0; i < n; i++){
            norm+=(VectorOfAnswers[i] - p[i])*(VectorOfAnswers[i] - p[i]);
        }
        return (Math.sqrt(norm) <= eps);
    }
    float roundingAnswers(float VectorOfAnswers, float eps){
        int i = 0;
        float newEps = eps;
        while (newEps < 1){
            i++;
            newEps*=10;
        }
        int rounding = (int) Math.pow(10.0, i);
        VectorOfAnswers = (int)(VectorOfAnswers*rounding + 0.5)/(float)(rounding);
        return VectorOfAnswers;
    }
    public void solveMatrix(float[][] M,float[] B) throws IOException {
        //решение
        VectorOfAnswers = new float[M.length];
        p = new float[M.length];
        for(int i = 0 ; i <M.length; i ++){
            VectorOfAnswers[i] = 1;}
        if(checkDominant(M, M.length)){
            while(!checkRes(VectorOfAnswers, p, M.length, eps)){
                System.arraycopy(VectorOfAnswers, 0, p, 0, M.length);
                for(int j = 0; j < M.length; j ++){
                    float var = 0;
                    for(int i = 0; i < M.length; i ++){
                        if(i!=j){
                            var+=(M[j][i])*VectorOfAnswers[i];}}
                    VectorOfAnswers[j] = (B[j] - var)/M[j][j];}
                iter++;
                if(iter >= 1000){
                    System.out.println("Решение не достигнуто спустя 1000 иттераций(");
                    System.exit(0);
                    break;
                }
            }
            System.out.println("Решение системы:");
            System.out.println("Иттераций: " + iter);
            for(int i = 0; i < M.length; i++){
                System.out.println("x" + i + " = " + roundingAnswers(VectorOfAnswers[i], eps));
            }
        }else{
            String num = "";
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
            switch (num) {
                case ("1") -> {
                    if (tryToRewriteString(M, B)) {
                        solveMatrix(M, B);
                    } else {
                        System.out.println("Не получилось добиться диагонального преобладания");
                    }
                }
                case ("2") -> System.out.println("Отмена");
                default -> System.out.println(" ");
            }
        }
    }
}
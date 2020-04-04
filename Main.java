package com.company;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        Scanner s = new Scanner(System.in);

        System.out.println("Enter a File Name (e.g dataset1.csv)");
        String filename = s.next();
        final double THRESHOLD = .0001;
        List<List<String>> t = new ArrayList<>();
        List<Double> datapoints = new ArrayList<>();
        Vector<Point> convexHull = new Vector<>();


        Jarvis j = new Jarvis();

        try {
            File input = new File(filename);
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line = " ";

            int counter = 0;

            while((line = br.readLine()) != null){
                String[] a = line.split(",");
                t.add(Arrays.asList(a));

            }
            List <String> temp;

            for(int i =0;i<t.size();i++){
                temp = t.get(i);
                datapoints.add(Double.parseDouble(temp.get(0)));
                datapoints.add(Double.parseDouble(temp.get(1)));

            }

            Point point[] = new Point[datapoints.size()/2];

            for(int i = 0;i<datapoints.size();i+=2){
                point[counter] = new Point(datapoints.get(i), datapoints.get(i+1));
                counter++;
            }


            //Finding left most point
            int lmIndex = 0;
            int nextIndex = 0;
            for(int i = 0;i<point.length;i++) {
                if (Double.compare(point[lmIndex].x, point[i].x) > 0) {
                    nextIndex = lmIndex;
                    lmIndex = i;
                } else if(Math.abs(point[lmIndex].x-point[i].x)<THRESHOLD){
                    nextIndex = i;
                }
            }
           j.convexHull(point,point[lmIndex],point[nextIndex], lmIndex, nextIndex, lmIndex, convexHull);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("File not found");
            System.exit(-1);
        }





    }
}

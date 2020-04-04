package com.company;
import java.awt.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Jarvis {

    //Find the cross product of two vectors
    public double crossProduct(Point p, Point q, Point r){
        double x1 = p.x-q.x;
        double x2 = p.x-r.x;
        double y1 = p.y-q.y;
        double y2 = p.y-r.y;

        double cross = (y2*x1) - (y1*x2);
        return cross;
    }


    public void convexHull(Point point[], Point p, Point q, int currIndex, int nextIndex, int lmIndex, Vector<Point> hull) {

        double cross;
        hull.add(p);
        //Finds next point most counter clockwise away from current vector
        for (int i = 0; i < point.length; i++) {
            if (i == currIndex || i == nextIndex) {
                continue;
            } else {
                Point r = point[i];
                cross = crossProduct(p, q, r);
                if (cross > 0) {
                    q = point[i];
                    currIndex = i;

                }
            }
        }
        if(!hull.contains(q)){
            hull.add(q);
        }

        //Choose random next point to use in next recursion
        boolean done = false;
        do {
            int random = ThreadLocalRandom.current().nextInt(0, 7);
            if (!hull.contains(point[random])) {
               Point r = point[random];

                if (hull.contains(point[random])) {
                    continue;
                }
                //When the path around the points reaches the start point, end it
                    if(Math.abs(point[currIndex].x-point[lmIndex].x) == 0 && Math.abs(point[currIndex].y-point[lmIndex].y) == 0){
                        for (int i = 0;i<hull.size();i++) {
                            int counter = 0;
                            //Delete dupicates
                            hull = removeDuplicates(hull);
                            hull.add(point[lmIndex]);
                            System.out.println("Points on the Convex Hull:");
                            for(Point temp:hull){
                                System.out.println(temp.x + " " + temp.y);
                            }
                            Plot plot = new Plot(point, hull);
                            plot.createGUI(point, hull);
                            return;
                        }
                    }else {
                        hull.add(p);
                        convexHull(point, q, r, currIndex, random, lmIndex, hull);
                        done = true;
                    }
                }

            } while(done != true);

    }

    private Vector<Point> removeDuplicates(Vector<Point> p){
        Vector<Point> noDup = new Vector<>();

        for(Point temp:p){
            if(!noDup.contains(temp)){
                noDup.add(temp);
            }
        }


        return noDup;
    }

}


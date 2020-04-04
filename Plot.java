package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Plot extends JPanel{
    private int width = 1025;
    private int height = 500;
    private int padding = 0;
    private int topPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private Vector<Point> convexHull;
    private List<Double> xHatch = new ArrayList<>();
    private List<Double> yHatch = new ArrayList<>();
    private static DecimalFormat df = new DecimalFormat("0.00");
    private Point[] point;

    public Plot(Point[] point, Vector convexHull) {
        this.point=point;
        this.convexHull=convexHull;
    }

    @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double xScale = (maxX(point) + 10) * 2;
        double yScale = (maxY(point) + 10) * 2;
        List<Point> graphPoints = new ArrayList<>();

        for (Point temp : convexHull) {
            double x1 = temp.x;
            double y1 = temp.y;
            graphPoints.add(new Point(x1, y1));
        }

        double intervalY;
        intervalY = maxY(point) / 5;

        g2.setColor(Color.WHITE);
        g2.fillRect(topPadding + topPadding, topPadding, (int) xScale, (int) yScale);


        //Hatch marks and grid lines for Y
        for (int i = 0; i < numberYDivisions / 2; i++) {
            if (point.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(topPadding + (100 * i), topPadding, topPadding + (100 * i), 1000);
                g2.setColor(Color.BLACK);
                String yLabel = df.format(maxY(point) - (intervalY * i)) + "";
                double yh = maxY(point) - (intervalY * i);
                yHatch.add(yh);
                g2.drawLine(515, topPadding + (100 * i), 535, topPadding + (100 * i));
                g2.drawString(yLabel, 460, 25 + (100 * i));

            }
        }
        for (int i = 0; i <= numberYDivisions / 2; i++) {
            if (point.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(500 + topPadding + (100 * i), topPadding, 500 + topPadding + (100 * i), 1000);
                g2.setColor(Color.BLACK);
                if (i != 0) {
                    String yLabel = df.format(0 - (intervalY * i)) + "";
                    double yh = 0 - (intervalY * i);
                    yHatch.add(yh);
                    g2.drawLine(515, 500 + (100 * (i)) +topPadding, 535, 500 + (100 * (i)) + topPadding);
                    g2.drawString(yLabel, 460, 500 + (100 * (i)) - 25);
                }
            }
        }

        Double intervalX;
        intervalX = maxX(point) / 5;
        //Hatch marks and grid lines for x;
        for (int i = 0; i < numberYDivisions / 2; i++) {
            if (point.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(topPadding, topPadding + (100 * i), 1000, topPadding + (100 * i));
                g2.setColor(Color.BLACK);
                String xLabel = df.format(-maxX(point) + (intervalX * i)) + "";
                double xh = -maxX(point) + (intervalX * i);
                xHatch.add(xh);
                g2.drawLine(topPadding + (100 * i), 515, topPadding + (100 * i), 535);
                g2.drawString(xLabel, 25 + (100 * i), 480);
            }
        }
        for (int i = 0; i <= numberYDivisions/2; i++) {
            if (point.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(topPadding, 500 + topPadding + (100 * i), 1000, 500 + topPadding + (100 * i));
                String xLabel = df.format(0 + (intervalX * i)) + "";
                double xh = 0 + (intervalX * i);
                xHatch.add(xh);
                if (i != 0) {
                    g2.setColor(Color.BLACK);
                    g2.drawLine(500 + (100 * i) +topPadding, 515, 500 + (100 * i) +topPadding, 535);
                    g2.drawString(xLabel, 500 + (100 * i) - 25, 480);
                }
            }
        }
        //x axis
        g2.drawLine(topPadding, 500+topPadding, 1025 - topPadding, 500+topPadding);
        //y axis
        g2.drawLine(500 + topPadding, topPadding, 500 + topPadding, 1025 - topPadding);

        //Plotting Points
        for(int i =0;i<point.length;i++){
            double x;
            double y;
            x = Math.ceil(point[i].x * (500 / maxX(point))+500 + topPadding);
            y = Math.ceil(-point[i].y * (500 / maxY(point)) + topPadding + 500);
            /*System.out.println("-----------------------------------");
            System.out.println(point[i].x + " " + point[i].y);
            System.out.println(x + " " + y);
            System.out.println("-----------------------------------");*/

            if(point[i].y<0) {
                y = y - topPadding;
            }
           ;
            g2.fillOval((int) x, (int) y, 7,7);
        }

        Stroke stroke = new BasicStroke(3f);
        g2.setColor(Color.BLUE);
        g2.setStroke(stroke);
        for(int i = 0;i<convexHull.size()-1;i++){
            int x0 = (int) Math.ceil(convexHull.get(i).x * (500 / maxX(point)) + 500 + topPadding);
            int y0 = (int) Math.ceil(-convexHull.get(i).y * (500 / maxY(point)) + topPadding + 500);
            int x1 = (int)Math.ceil(convexHull.get(i+1).x* (500/maxX(point))+500 + topPadding);
            int y1 = (int)Math.ceil(-convexHull.get(i+1).y *(500/maxY(point)) + topPadding + 500);

            if(convexHull.get(i).y<0){
                y0 = y0 - topPadding;
            }
            if(convexHull.get(i+1).y<0){
                y1 = y1 - topPadding;
            }


            g2.drawLine(x0,y0,x1,y1);
        }

    }

    private double maxX(Point[] p){
        Double Max = Double.MIN_VALUE;
        for(int i = 0;i<p.length;i++){
            if(Max<p[i].x){
                Max = p[i].x;
            }
        }
        return Max;
    }

    private double minX(Point[] p){
        Double min = Double.MAX_VALUE;
        for(int i = 0;i<p.length;i++){
            if(min>p[i].x){
                min = p[i].x;
            }
        }
        return min;
    }

    private double maxY(Point[] p){
        Double Max = Double.MIN_VALUE;
        for(int i = 0;i<p.length;i++){
            if(Max<p[i].y){
                Max = p[i].y;
            }
        }
        return Max;
    }

    private double minY(Point[] p){
        Double min = Double.MAX_VALUE;
        for(int i = 0;i<p.length;i++){
            if(min>p[i].y){
                min = p[i].y;
            }
        }
        return min;
    }
    public void createGUI(Point[] point, Vector convexHull){
        Plot mainpanel = new Plot(point,convexHull);
        mainpanel.setPreferredSize(new Dimension(1100,1025));
        JFrame frame = new JFrame("Convex Hull");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }


}

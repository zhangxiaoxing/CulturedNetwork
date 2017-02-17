/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.circuitTable;

import iodegree.lib.D;
import iodegree.lib.pattern.TriPattLib;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import javax.swing.JPanel;

/**
 *
 * @author Libra
 */
public class PatternCanvas extends JPanel {

    private Graphics2D g2d;
    final private Font idFont = new Font("Serif", Font.PLAIN, 72);
    final private Font idFontBold = new Font("Serif", Font.BOLD, 84);
    final private Font dataFont = new Font("Serif", Font.PLAIN, 20);
    private int[][] connType;
    private double prob;
    private int obs;
    private double exp;
    private int id;
    private boolean boolDetails;
    private boolean dashed;
    final private DecimalFormat df = new DecimalFormat("#.####");
    final private float[] dash = {20.0f};
    final private BasicStroke dashStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 20.0f, dash, 0.0f);
    final private BasicStroke basicStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
    final private BasicStroke wideStroke = new BasicStroke(10.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    public void setObs(int obs) {
        this.obs = obs;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBoolDetails(boolean boolDetails) {
        this.boolDetails = boolDetails;
    }

    public PatternCanvas() {
        connType = new TriPattLib().getTripleList();
        id = 0;
        prob = 0;
        obs = 0;
        exp = 0;
        boolDetails = true;
    }

    @Override
    public boolean isOpaque() {
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
//        System.out.println("painted");
        g2d = (Graphics2D) g;
        setDefaultPara();


        drawBG();
        drawPattern();
        drawID();
        if (boolDetails) {
//            drawRatio();
//            drawRatioFig();
//            drawP();
        }
    }

    private void setDefaultPara() {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setStroke(basicStroke);
    }

    private void drawBG() {

//        if (boolDetails && prob < 0.05) {
//            g2d.setColor(Color.PINK);
//        } else {
        g2d.setColor(Color.WHITE);
//        }
        g2d.fillRect(0, 0, 320, 320);
    }

    private void drawPattern() {

        Point2D.Double c1 = new Point2D.Double(160, 15);
        Point2D.Double c2 = new Point2D.Double(8, 264);
        Point2D.Double c3 = new Point2D.Double(312, 264);

        double dd = 30;
        double rr = dd / 2;
        if (boolDetails) {
            g2d.setColor(Color.BLACK);
            g2d.draw(new Ellipse2D.Double(c1.getX() - rr, c1.getY() - rr, dd, dd));
            g2d.draw(new Ellipse2D.Double(c2.getX() - rr, c2.getY() - rr, dd, dd));
            g2d.draw(new Ellipse2D.Double(c3.getX() - rr, c3.getY() - rr, dd, dd));
        }

        if (dashed) {
            g2d.setStroke(dashStroke);
        }
        drawArrow(c2, c1, connType[id][0]);
        drawArrow(c1, c2, connType[id][1]);
        drawArrow(c1, c3, connType[id][2]);
        drawArrow(c3, c1, connType[id][3]);
        drawArrow(c3, c2, connType[id][4]);
        drawArrow(c2, c3, connType[id][5]);
    }

    private void drawID() {
//        D.tp(id,prob);
        g2d.setColor(Color.BLACK);
        if (prob < 0.05) { //Multiple hypothesis fixed
            g2d.setFont(idFontBold);
            g2d.drawString(Integer.toString(id), 122, 192);
        } else {
            g2d.setFont(idFont);
            g2d.drawString(Integer.toString(id), 126, 190);
        }

    }

    private void drawRatio() {
        g2d.setColor(Color.BLACK);

        g2d.setFont(idFont);
//        }

//        g2d.drawString("Observed " + Integer.toString(obs), 160, 30);
        g2d.drawString("Ob:" + Integer.toString(obs), 240, 30);
//        g2d.drawString("Expected " + df.format(exp), 20, 290);

//        g2d.drawString("Act:Sim " + df.format(obs / exp), 20, 310);
    }

    private void drawRatioFig() {
        double xx = 280;
        double yTop = 80;
        double yBot = 200;
        float width = 25;
        double yMid;
        Point2D.Double topPt = new Point2D.Double(xx, yTop);
        Point2D.Double botPt = new Point2D.Double(xx, yBot);
        Point2D.Double midPt;

        g2d.setStroke(wideStroke);

        yMid = yBot - (yBot - yTop) * (exp / (obs + exp));
//        System.out.println(yBot+","+yMid+","+yTop);
        midPt = new Point2D.Double(xx, yMid);
        drawFigPart(botPt, midPt, Color.GREEN);
        drawFigPart(midPt, topPt, Color.BLUE);
    }

    private void drawFigPart(Point2D.Double fromPt, Point2D.Double toPt, Color c) {
        g2d.setColor(c);
        g2d.draw(new Line2D.Double(fromPt, toPt));
    }

    private void drawP() {
        g2d.setColor(Color.BLACK);
        g2d.setFont(idFont);

//        g2d.drawString(significance(pLess) + "pLess " + Double.toString(pLess), 160, 20);
        g2d.drawString("p:" + df.format(prob), 30, 290);
    }

//    private String significance(double d) {
//        if (d < 0.005) {
//            return "*** ";
//        } else if (d < 0.01) {
//            return " ** ";
//        } else if (d < 0.05) {
//            return "  * ";
//        } else {
//            return "    ";
//        }
//    }
    private Point2D.Double biasPt(Point2D.Double fromPt, Point2D.Double toPt, boolean returnFrom) {
        double crossD = 10;
        double trim = 20;

        double lineRad = Math.atan2(fromPt.getY() - toPt.getY(), fromPt.getX() - toPt.getX());

        double trimX = trim * Math.cos(lineRad);
        double trimY = trim * Math.sin(lineRad);

        double biasX = crossD * Math.sin(lineRad);
        double biasY = crossD * Math.cos(lineRad);

        Point2D.Double newFromPt, newToPt;

        newFromPt = new Point2D.Double(fromPt.getX() - biasX - trimX, fromPt.getY() + biasY - trimY);
        newToPt = new Point2D.Double(toPt.getX() - biasX + trimX, toPt.getY() + biasY + trimY);

        return returnFrom ? newFromPt : newToPt;
    }

    private void drawArrow(Point2D.Double fromPt, Point2D.Double toPt, int type) {
        Point2D.Double transX = biasPt(fromPt, toPt, true);
        Point2D.Double transY = biasPt(fromPt, toPt, false);

        switch (type) {
            case 0:
                break;
            case 1:
                g2d.setColor(Color.RED);
                g2d.draw(new Line2D.Double(transX, transY));
                drawArrowHead(transX, transY);
                break;
            case 2:
                g2d.setColor(Color.GREEN);
                g2d.draw(new Line2D.Double(transX, transY));
                drawArrowHead(transX, transY);
                break;
        }
    }

    private void drawArrowHead(Point2D fromPt, Point2D toPt) {

        double arrowLength = 45;
        double arrowDeg = 20;

        double arrowRad = arrowDeg / 180 * 3.14;
        Point2D.Double cwP, ccwP;
        double lineDeg, cwDeg, ccwDeg;

        lineDeg = Math.atan2(fromPt.getY() - toPt.getY(), fromPt.getX() - toPt.getX());
        cwDeg = lineDeg + arrowRad;
        ccwDeg = lineDeg - arrowRad;

        cwP = new Point2D.Double(toPt.getX() + arrowLength * Math.cos(cwDeg),
                toPt.getY() + arrowLength * Math.sin(cwDeg));
        ccwP = new Point2D.Double(toPt.getX() + arrowLength * Math.cos(ccwDeg),
                toPt.getY() + arrowLength * Math.sin(ccwDeg));

        g2d.draw(new Line2D.Double(toPt, cwP));
        g2d.draw(new Line2D.Double(toPt, ccwP));

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author Librizzy
 */
public class CommonsLib {

    static public String getDefaultFile() {

        String[] paths = {"I:\\My  Paper\\GAD0.accdb"};
        for (String path : paths) {
            File f = new File(path);
            if (f.exists() && f.length() > 1000) {
                return f.getAbsolutePath();
            }
        }
        javax.swing.JFileChooser DataFileChooser = new javax.swing.JFileChooser();

        int returnVal = DataFileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           return DataFileChooser.getSelectedFile().getAbsolutePath();
        }
        throw new IllegalArgumentException("Wrong file");
    }

    static public void writeMatrix(String pathToFile, double[][] mat) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(pathToFile))) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length - 1; j++) {
                    w.write(mat[i][j] + ",");
                }
                w.write(mat[i][mat[i].length - 1] + "\n");
            }
            w.flush();
        } catch (IOException e) {
        }
    }

    static public void writeMatrix(String pathToFile, Object[][] mat) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(pathToFile))) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length - 1; j++) {
                    w.write(mat[i][j].toString() + ",");
                }
                w.write(mat[i][mat[i].length - 1] + "\n");
            }
            w.flush();
        } catch (IOException e) {
        }
    }

    static public void writeMatrix(String pathToFile, float[][] mat) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(pathToFile))) {
            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[i].length - 1; j++) {
                    w.write(mat[i][j] + ",");
                }
                w.write(mat[i][mat[i].length - 1] + "\n");
            }
            w.flush();
        } catch (IOException e) {
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iodegree.dataStrucure;

import java.util.Arrays;

/**
 *
 * @author Libra
 */
public class ConnGrp {

    private int size;
    private int gadCount;
    private int gluCount;
    private boolean[][] arrGluConn;
    private boolean[][] arrGABAConn;
    private int baseId;

    public int countRecip(boolean fwGlu, boolean revGlu) {
        boolean[][] fw = fwGlu ? arrGluConn : arrGABAConn;
        boolean[][] rev = revGlu ? arrGluConn : arrGABAConn;
        int count = 0;

        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                if (fw[i][j] && rev[j][i]) {
                    count++;
                }
            }
        }
        return count;
    }

    public ConnGrp(int s, int Glu) {
        size = s;
        gluCount = Glu;
        gadCount = s - gluCount;
        arrGluConn = new boolean[s][s];
        arrGABAConn = new boolean[s][s];
    }

    public ConnGrp(int s, int Glu, int base) {
        size = s;
        gluCount = Glu;
        gadCount = s - gluCount;
        arrGluConn = new boolean[s][s];
        arrGABAConn = new boolean[s][s];
        baseId = base;
    }

    public int countGluSlots() {
        return gluCount * (size - 1);
    }

    public int countGABASlots() {
        return gadCount * (size - 1);
    }

    public int countGluGluSlots() {
        return gluCount * (gluCount - 1);
    }

    public int countGluGABASlots() {
        return gluCount * gadCount;
    }

    public int countGABAGABASlots() {
        return gadCount * (gadCount - 1);
    }

    public int countGluPairs() {
        return gluCount * (gluCount - 1) / 2;
    }

    public int countHybrPairs() {
        return gluCount * gadCount;
    }

    public int countGABAPairs() {
        return gadCount * (gadCount - 1) / 2;
    }

    /*
     * First cells are always glu i starts with 0
     *
     */
    public int[] add1Glu(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = 0; post < size; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGluConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 1};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] add1GluGlu(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = 0; post < gluCount; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGluConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 1};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] add1GluGABA(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = gluCount; post < size; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGluConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 1};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] add1GABAGABA(int i) {
        for (int pre = gluCount, j = 0; pre < size; pre++) {
            for (int post = gluCount; post < size; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGABAConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 2};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -2};
        return ret;
    }

    public int[] addGluUni(int i, float f) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = pre + 1; post < gluCount; post++) {
                if (i == j) {
                    if (f < 0.5) {
                        arrGluConn[pre][post] = true;
                        int[] ret = {baseId + pre, baseId + post, 1};
                        return ret;
                    } else {
                        arrGluConn[post][pre] = true;
                        int[] ret = {baseId + post, baseId + pre, 1};
                        return ret;
                    }
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                }
                j++;
            }

        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addGABAUni(int i, float f) {
        for (int pre = gluCount, j = 0; pre < size; pre++) {
            for (int post = pre + 1; post < size; post++) {
                if (i == j) {
                    if (f < 0.5) {
                        arrGABAConn[pre][post] = true;
                        int[] ret = {baseId + pre, baseId + post, 2};
                        return ret;
                    } else {
                        arrGABAConn[post][pre] = true;
                        int[] ret = {baseId + post, baseId + pre, 2};
                        return ret;
                    }
//                        System.out.print("Glu, " + pre + ", " + post + "\n");

                }
                j++;
            }

        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addGluGABAUni(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = gluCount; post < size; post++) {
                if (i == j) {
                    arrGluConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                    int[] ret = {baseId + pre, baseId + post, 1};
                    return ret;
                }
                j++;
            }

        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addGABAGluUni(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = gluCount; post < size; post++) {
                if (i == j) {
                    arrGABAConn[post][pre] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                    int[] ret = {baseId + post, baseId + pre, 2};
                    return ret;
                }
                j++;
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addHybrReci(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = gluCount; post < size; post++) {
                if (i == j) {
                    arrGluConn[pre][post] = true;
                    arrGABAConn[post][pre] = true;

//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                    int[] ret = {baseId + pre, baseId + post, 1};
                    return ret;
                }
                j++;
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addGluReci(int i) {
        for (int pre = 0, j = 0; pre < gluCount; pre++) {
            for (int post = pre + 1; post < gluCount; post++) {
                if (i == j) {
                    arrGluConn[pre][post] = true;
                    arrGluConn[post][pre] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                    int[] ret = {baseId + pre, baseId + post, 1};
                    return ret;
                }
                j++;
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] addGABAReci(int i) {
        for (int pre = gluCount, j = 0; pre < size; pre++) {
            for (int post = pre + 1; post < size; post++) {
                if (i == j) {
                    arrGABAConn[pre][post] = true;
                    arrGABAConn[post][pre] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                    int[] ret = {baseId + pre, baseId + post, 2};
                    return ret;
                }
                j++;
            }
        }
        int[] ret = {-baseId, -i, -1};
        return ret;
    }

    public int[] add1GABAGlu(int i) {
        for (int pre = gluCount, j = 0; pre < size; pre++) {
            for (int post = 0; post < gluCount; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGABAConn[pre][post] = true;
//                        System.out.print("Glu, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 2};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -2};
        return ret;
    }

    public int[] add1GABA(int i) {
        for (int pre = gluCount, j = 0; pre < size; pre++) {
            for (int post = 0; post < size; post++) {
                if (pre != post) {
                    if (i == j) {
                        arrGABAConn[pre][post] = true;
//                        System.out.print("GABA, " + pre + ", " + post + "\n");
                        int[] ret = {baseId + pre, baseId + post, 2};
                        return ret;
                    }
                    j++;
                }
            }
        }
        int[] ret = {-baseId, -i, -2};
        return ret;
    }

    public int[] getIo(boolean glu, boolean input) {
        int[] io = new int[4];
        int count = 0;
        if (glu) {
            if (input) {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < gluCount; j++) {
                        if (arrGluConn[j][i]) {
                            count++;
                        }
                    }
                    io[count]++;
                    count = 0;
                }
            } else {
                for (int i = 0; i < gluCount; i++) {
                    for (int j = 0; j < size; j++) {
                        if (arrGluConn[i][j]) {
                            count++;
                        }
                    }
                    io[count]++;
                    count = 0;
                }
            }
        } else {
            if (input) {
                for (int i = 0; i < size; i++) {
                    for (int j = gluCount; j < size; j++) {
                        if (arrGABAConn[j][i]) {
                            count++;
                        }
                    }
                    io[count]++;
                    count = 0;
                }
            } else {
                for (int i = gluCount; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (arrGABAConn[i][j]) {
                            count++;
                        }
                    }
                    io[count]++;
                    count = 0;
                }
            }
        }

        return io;
    }

    public int[] getGluOnlyIo(boolean input) {
        int[] io = new int[4];
        int count = 0;

        if (input) {
            for (int i = 0; i < gluCount; i++) {
                for (int j = 0; j < gluCount; j++) {
                    if (arrGluConn[j][i]) {
                        count++;
                    }
                }
                io[count]++;
                count = 0;
            }
            io[0] += gadCount;
        } else {
            for (int i = 0; i < gluCount; i++) {
                for (int j = 0; j < gluCount; j++) {
                    if (arrGluConn[i][j]) {
                        count++;
                    }
                }
                io[count]++;
                count = 0;
            }
        }

        return io;
    }

    public void reInit() {
        for (boolean[] row : arrGluConn) {
            Arrays.fill(row, false);
        }
        for (boolean[] row : arrGABAConn) {
            Arrays.fill(row, false);
        }
    }
}

/* DO NOT EDIT THIS FILE. THIS FILE WAS GENERATED FROM flob/CompassView.zob,
 * EDIT THAT FILE INSTEAD!
 * All changes to this file will be lost.
 */
/*
CompassRaster.zob
 *
 *    You may use and distribute under the terms of either the GNU Lesser
 *    General Public License, either version 2 of the license or,
 *    at your choice, any later version. Alternatively, you may use and
 *    distribute under the terms of the XPL.
 *
 *    See the LICENSE.lgpl and LICENSE.xpl files for the specific terms of
 *    the licenses.
 *
 *    This software is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the README
 *    file for more details.
 *
 */
/*
 * Written by Tuukka Hastrup
 */


package org.gzigzag.flob;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;
import org.gzigzag.*;


public class CompassView implements FlobView, ZOb {
    public static final String rcsid = "$Id$";
    public static boolean dbg = true;

    static final void p(String s) {
        if (dbg) System.out.println(s);
    }

    static final void pa(String s) {
        System.out.println(s);
    }


    /**
     * UNDOCUMENTED.
     *
     * <p>Default value: <PRE> new Font("SansSerif", Font.PLAIN, 12);</PRE>.
     *
     * @structparam 1
     */

    public Font dfont = new Font("SansSerif", Font.PLAIN, 12);


    /* AUTOGENERATED! */
    static final private int fullmask = 1;

    /* AUTOGENERATED! */
    public String readParams(ZZCell start) {
        int m = 0;
        try {
            if (start!=null) m = readParams(start, 0);
        } catch (Throwable t) {
            ZZLogger.exc(t);
        } finally {
            init__zob();
        }
        if ((m & fullmask)!=fullmask) {
            // not all parameters present - no problem right now.
        }
        return "";
    }

    /* AUTOGENERATED! */
    private int readParams(ZZCell start, int mask) {
        ZZCell n = start;
        while (n!=null) {
            String s = n.getText();
            // Tests autogenerated from members.

            if (s.equals("dfont")) {
                mask |= 1;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    {
                        ZZCell c1 = c.s("d.1");
                        int size = 12;
                        int style = Font.PLAIN;
                        if (c1!=null) {
                            size = Integer.parseInt(c1.getText());
                            ZZCell c2 = c1.s("d.1");
                            if (c2!=null) {
                                String sty = c2.getText();
                                if (sty.equals("BOLD")) style = Font.BOLD;
                                if (sty.equals("ITALIC")) style = Font.ITALIC;
                                else {
                                    // error...
                                }
                            }
                        }
                        dfont = new Font(s, style, size);
                    }

                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else {
            } // grab that last "else"
            ZZCell h = n.h("d.3");
            if (h!=null && h!=n) {
                // recurse
                mask |= readParams(h, mask);
            }
            n = n.s("d.2");
        }
        return mask;
    }


    int dfonth;
    FontMetrics dfm;

    protected void init__zob() {
        dfm = (ScalableFont.fmComp.getFontMetrics(dfont));
        dfonth = dfm.getHeight();
    }


    public void raster(FlobSet into, FlobFactory fact, ZZCell view, String[] vds, ZZCell accursed) {

        Dimension d = into.getSize();
        int midx = d.width / 2;
        int midy = d.height / 2;
        Dimension c = fact.getSize(null, 1);
        int cw = c.width;
        int ch = c.height;

        ZZCell stdim = ZZCursorReal.get(view.s("d.dims", 1));

/*	ZZConnection[] conns = accursed.getConns();
	for (int i = 0; i < conns.length; i++)
		System.out.println("Conn #" + i + ": " +
				   conns[i].c1.getID() + "-<" +
				   conns[i].c2.getID() + (conns[i].dir == -1 ?
							  "-" : "") +
				   conns[i].dim + ">-" +
				   conns[i].c2.getID());*/

        Vector dims = new Vector(); // Dims to be drawn
        Vector pos = new Vector(); // Cells to positive directions to be drawn
        Vector neg = new Vector(); // Cells to negative directions to be drawn

        ZZCell dim, pc, nc;
        dim = stdim;
        do { // Go through the DimList starting from the x-axis dimension
            String cdim = dim.getText();
            p("Found dim: " + cdim);
            pc = accursed.s(cdim, 1);
            nc = accursed.s(cdim, -1);
            if (nc!=null || pc!=null) {
                dims.addElement(cdim);
                pos.addElement(pc);
                neg.addElement(nc);
            }
            dim = dim.s("d.2", 1);
        } while (dim!=stdim); // Do until loop closes

        int numdirs = pos.size() + neg.size(); // Number of cells and thus sectors
        if (numdirs==0) return; // No cells to draw
        double angle = 2 * Math.PI / (numdirs);
        int l = (int) ((d.height < d.width ? d.height:d.width) / 2 * 0.5);

        fact.makeFlob(into, accursed, accursed, 1, (d.width - cw) / 2, (d.height - ch) / 2, 1, cw, ch);

        int i = 0;
        LineDecor.Builder nldb = new LineDecor.Builder(into, Color.black);
        LineDecor.Builder pldb = new LineDecor.Builder(into, Color.red);
        nldb.startl(numdirs / 2 * 4, 12);
        pldb.startl(numdirs / 2 * 4, 12);

        double a = (double) -2 / 3 * Math.PI; // Begin placing positive cells top left
        while (i < numdirs / 2) {
            int dx = x(a, l);
            int dy = y(a, l);

            if (pos.elementAt(i)!=null) {
                fact.makeFlob(into, (ZZCell) pos.elementAt(i), (ZZCell) pos.elementAt(i), (float) 0.7, midx + dx - cw / 2, midy + dy - ch / 2, 1, cw, ch);
                pldb.l(midx, midy, midx + dx, midy + dy);
                into.add(new TextDecor(midx + dx + cw / 2 - dfm.stringWidth("+" + (String) dims.elementAt(i)), midy + dy - ch / 2, "+" + (String) dims.elementAt(i), Color.red, dfont));
            }
            if (neg.elementAt(i)!=null) {
                fact.makeFlob(into, (ZZCell) neg.elementAt(i), (ZZCell) neg.elementAt(i), (float) 0.7, midx - dx - cw / 2, midy - dy - ch / 2, 1, cw, ch);
                nldb.l(midx, midy, midx - dx, midy - dy);
                into.add(new TextDecor(midx - dx - cw / 2, midy - dy + ch / 2 + dfonth, "-" + (String) dims.elementAt(i), Color.black, dfont));
            }

            i++;
            a += angle; // Rotate clockwise
        }
        nldb.endl();
        pldb.endl();
    }

    protected int x(double angle, int length) {
        return (int) (Math.cos(angle) * length);
    }

    protected int y(double angle, int length) {
        return (int) (Math.sin(angle) * length);
    }
}

// vim: set syntax=java :

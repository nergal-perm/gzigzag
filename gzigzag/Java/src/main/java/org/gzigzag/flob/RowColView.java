/* DO NOT EDIT THIS FILE. THIS FILE WAS GENERATED FROM flob/RowColView.zob,
 * EDIT THAT FILE INSTEAD!
 * All changes to this file will be lost.
 */
/*   
RowColRaster.zob
 *    
 *    Copyright (c) 2000, Ted Nelson and Tuomas Lukka
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
 * Written by Tuomas Lukka
 */


package org.gzigzag.flob;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Hashtable;
import org.gzigzag.*;

public class RowColView implements FlobView, ZOb {
    public static final String rcsid = "$Id$";
    public static boolean dbg = false;

    static final void p(String s) {
        if (dbg) System.out.println(s);
    }

    static final void pa(String s) {
        System.out.println(s);
    }


    /**
     * UNDOCUMENTED.
     *
     * <p>Default value: <PRE> false;</PRE>.
     *
     * @structparam 1
     */

    public boolean row = false;
    ZOb geom = new MagGrid();


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

            if (s.equals("row")) {
                mask |= 1;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    if (s.equals("true")) row = true;
                    else if (s.equals("false")) row = false;
                    else { /* XXX ??? */ }

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


    void place(FlobFactory fact, FlobSet f, ZZCell c, Dimension ideal, Rectangle where) {
        float fract = (float) (where.height / (float) ideal.height);
        fact.makeFlob(f, c, c, fract, where.x, where.y, 1, where.width, where.height);

    }

    FlobDecorator linker = new StdLinks();

    public void raster(final FlobSet into, final FlobFactory fact, ZZCell view, String[] dims, ZZCell accursed) {
        final GridGeom geom = (GridGeom) this.geom;
        final Dimension ideal = fact.getSize(null, 1);
        final Dimension gs = geom.setSizes(ideal, into.getSize());

        final Hashtable done = new Hashtable();
        final Rectangle rect = new Rectangle();

        final int cx = gs.width / 2;
        final int cy = gs.height / 2;

        final ZZCell[] col = new ZZCell[row ? gs.height:gs.width];

        final int cmain = (row ? cy:cx);
        final int cother = (row ? cx:cy);

        // Do the single row/column
        ZZIter.alternate(accursed, true, dims[(row ? 1:0)], 1, new ZZIter.NIter() {
            public boolean go(ZZCell c, int nth) {
                nth += cmain;
                if (nth < 0 || nth >= col.length) return false;
                if (done.get(c)!=null) return false;
                col[nth] = c;
                done.put(c, c);
                if (row) geom.getCell(cother, nth, rect);
                else geom.getCell(nth, cother, rect);
                place(fact, into, c, ideal, rect);
                return true;
            }
        });

        final int lother = (row ? gs.width:gs.height);

        for (int i = 0; i < col.length; i++) {
            final int coord = i;
            ZZIter.alternate(col[i], false, dims[(row ? 0:1)], 1, new ZZIter.NIter() {
                public boolean go(ZZCell c, int nth) {
                    nth += cother;
                    if (nth < 0 || nth >= lother) return false;
                    if (done.get(c)!=null) return false;
                    done.put(c, c);
                    if (row) geom.getCell(nth, coord, rect);
                    else geom.getCell(coord, nth, rect);
                    place(fact, into, c, ideal, rect);
                    return true;
                }
            });
        }

        linker.decorate(into, "", view);

        // Do the dimensions.
        if (true) ZZUtil.showFlobDims(into, fact, view, 3);
    }


}
// vim: set syntax=java :

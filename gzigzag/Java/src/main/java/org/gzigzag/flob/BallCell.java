/*   
BallCell.java
 *    
 *    Copyright (c) 1999-2000, Ted Nelson and Tuomas Lukka
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

import java.awt.*;
import org.gzigzag.ZZCell;
import org.gzigzag.ZZCursorVirtual;
import org.gzigzag.ZZUtil;

/**
 * A ball-and-stick graphical representation of a cell.
 */

public class BallCell extends Flob implements Colorer {
    public static final String rcsid = "$Id: BallCell.java,v 1.1 2000/10/27 13:55:48 tjl Exp $";
    public static final boolean dbg = true;

    String s;
    Font f;
    // XXX Should this *really* need to be stored here?
    FontMetrics fm;

    /**
     * Font height.
     */
    int fh;
    int fasc;
    /**
     * String width.
     */
    int sw;

    ZZCell strCell;

    int xoffs = 3;
    int yoffs = 3;

    Color ballColor;
    Color bgColor;

    public boolean addColor(Color c) {
        if (ballColor==null) ballColor = c;
        return true;
    }

    static public final Dimension getSize(FontMetrics fm, String str,
                                          int xoffs, int yoffs) {
        int w = fm.stringWidth(str) + xoffs * 2 + 2;
        int h = fm.getHeight() + 2 * yoffs;
        w += h; // Room for the ball.
        return new Dimension(w, h);
    }

    public int getStrX(int ind) {
        if (fm==null) throw new Error("No fontmetrics");
        if (ind > s.length()) {
            // XXX What to do?
            return x + xoffs + h + fm.stringWidth(s);
        }
        return x + xoffs + h + fm.stringWidth(s.substring(0, ind));
    }

    public void render(Graphics g, int mx, int my, int md,
                       int mw, int mh) {
        int ty = my + (mh - fh) / 2 + fasc;
        Color bu = g.getColor();
        g.setColor(ballColor!=null ? ballColor:Color.white);
        g.fillOval(mx + yoffs, my + yoffs, mh - 2 * yoffs, mh - 2 * yoffs);
        g.setFont(f);
        g.setColor(bgColor);
        g.drawString(s, mx + xoffs + h - 1, ty - 1);
        g.drawString(s, mx + xoffs + h + 2, ty + 2);
        g.setColor(Color.white);
        g.drawString(s, mx + xoffs + h + 1, ty + 1);
        g.setColor(bu);
        g.drawString(s, mx + xoffs + h, ty);
        g.drawOval(mx + yoffs, my + yoffs, mh - 2 * yoffs, mh - 2 * yoffs);
    }

    public BallCell(int x, int y, int d, int w, int h,
                    ZZCell c, ZZCell strCell, String s,
                    Font f, FontMetrics fm, FlobSet fs) {
        super(x, y, d, w, h, c);

        // Not true, since c can be the flob cell and d the referring
        // cell.
        // this.s = c.getText();

        this.s = (s==null ? "":s);
        this.strCell = strCell;

        this.f = f;
        this.fm = fm;

        this.fh = fm.getAscent() + fm.getDescent();
        this.fasc = fm.getAscent() + fm.getLeading();
        this.sw = fm.stringWidth(this.s);

        this.bgColor = fs.getBackground();
    }

    public Object hit(int x0, int y0) {
        if (!insideRect(x0, y0)) return null;
        // Search for the right position in the string.
        // We return a virtual cursor.
        int ind = ZZUtil.findStringHit(s, x0 - x - xoffs - h, fm);

        return new ZZCursorVirtual(strCell, ind);
    }

    public Point getCenter(Point p) {
        p.x = x + h / 2;
        p.y = y + h / 2;
        return p;
    }

}



/* DO NOT EDIT THIS FILE. THIS FILE WAS GENERATED FROM flob/LevelView.zob,
 * EDIT THAT FILE INSTEAD!
 * All changes to this file will be lost.
 */
/*   
LevelRaster.zob
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
 * Written by Benjamin Fallenstein
 */

package org.gzigzag.flob;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.gzigzag.ZOb;
import org.gzigzag.ZZCell;
import org.gzigzag.ZZLogger;

/**
 * A raster for multiple-level structures.
 * Interprets two dimensions as many-to-many relationship. Also interprets
 * third dimension as one-to-one relationships which are shown the same way
 * as the many-to-many relationships. For many-to-many relationships with
 * link cells, shows the link cells.
 */

/* Some XXX'es:
 - fix the damn occupiedbreadth bug!!!
 - should move the parentlinks in the right place
 - primitive actions for leveled visualization (including move)
 - loose linking so that new links can be created? or: new link creates
   new target, too? or maybe: in loose mode, links w/o target are shown as
   that, links w/o target? yeah, way to go. (anyways, should new link also
   create new target or not? pbly yes, eh?)
 - gaps that shrink; think depthwise gapping through again
 */

public class LevelView implements FlobView, ZOb {
    public static final String rcsid = "$Id$";
    public static boolean dbg = false;

    static final void p(String s) {
        if (dbg) System.out.println(s);
    }

    static final void pa(String s) {
        System.out.println(s);
    }


    /**
     * The size factor applied to cells.
     * <p>Default value: <PRE> (float)1.6;</PRE>.
     *
     * @structparam 1
     */

    public
    float initmul
            = (float) 1.6;
    /**
     * How many recursion levels shall we render (on both sides of the
     * root)?
     * <p>Default value: <PRE> 4;</PRE>.
     *
     * @structparam 1
     */

    public
    int maxdepth
            = 4;
    /**
     * The breadth distance between neigbouring cells.
     * <p>Default value: <PRE> 15;</PRE>.
     *
     * @structparam 1
     */

    public
    int bgap
            = 15;
    /**
     * The depth distance between a cell and a link
     * <p>Default value: <PRE> 10;</PRE>.
     *
     * @structparam 1
     */

    public
    int dgap
            = 10;
    /**
     * The factor of enlargement of cells as they move away
     * depthwise from the accursed cell.  Element 0 is
     * horizontal, element 1 is vertical.
     * <p>Default value: <PRE> (float)0.7;</PRE>.
     *
     * @structparam 1
     */

    public
    float shrink
            = (float) 0.7;
    /**
     * The enlargement factor applied to link cells. Think linkshrink!
     * <p>Default value: <PRE> (float)0.25;</PRE>.
     *
     * @structparam 1
     */

    public
    float linkshrink
            = (float) 0.25;
    /**
     * Should the tree be shown "depthhorizontal", ie. depth rightwards?
     * <p>Default value: <PRE> false;</PRE>.
     *
     * @structparam 1
     */

    public
    boolean depthhorizontal
            = false;
    /**
     * Should the headcell be used on both dimensions?
     * Normally on target dim, tailcell is used.
     * <p>Default value: <PRE> false;</PRE>.
     *
     * @structparam 1
     */

    public
    boolean bothhead
            = false;
    /**
     * Are links without targets interpreted as targets?
     * If two elements are given, the first is used for upstream- and the
     * second for downstream connections.
     * <p>Default value: <PRE> new boolean[] { false, false };</PRE>.
     *
     * @structparam 1
     */

    public
    boolean[] loose // 1..2
            = new boolean[]{false, false};


    /* AUTOGENERATED! */
    static final private int fullmask = 511;

    /* AUTOGENERATED! */
    public String readParams(ZZCell start) {
        int m = 0;
        try {
            if (start!=null)
                m = readParams(start, 0);
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

            if (s.equals("initmul")) {
                mask |= 1;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    initmul = (Float.valueOf(s)).floatValue();
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("maxdepth")) {
                mask |= 2;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    maxdepth = Integer.parseInt(s);
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("bgap")) {
                mask |= 4;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    bgap = Integer.parseInt(s);
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("dgap")) {
                mask |= 8;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    dgap = Integer.parseInt(s);
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("shrink")) {
                mask |= 16;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    shrink = (Float.valueOf(s)).floatValue();
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("linkshrink")) {
                mask |= 32;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    linkshrink = (Float.valueOf(s)).floatValue();
                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("depthhorizontal")) {
                mask |= 64;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    if (s.equals("true")) depthhorizontal = true;
                    else if (s.equals("false")) depthhorizontal = false;
                    else { /* XXX ??? */ }

                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("bothhead")) {
                mask |= 128;
                try {
                    ZZCell c = n.s("d.1");
                    s = c.getText();
                    if (s.equals("true")) bothhead = true;
                    else if (s.equals("false")) bothhead = false;
                    else { /* XXX ??? */ }

                } catch (Exception e) {
                    ZZLogger.exc(e);
                }
            } else if (s.equals("loose")) {
                mask |= 256;
                try {

                    // count rank
                    int i = -1;
                    for (ZZCell c = n; c!=null; c = c.s("d.1")) i++;
                    // XXX check range
                    loose = new boolean[i];
                    i = 0;
                    for (ZZCell c = n.s("d.1");
                         c!=null; c = c.s("d.1")) {
                        s = c.getText();

                        if (s.equals("true")) loose[i] = true;
                        else if (s.equals("false")) loose[i] = false;
                        else { /* XXX ??? */ }
                        ;
                        i++;
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


    protected void init__zob() {
        if (loose.length==1)
            loose = new boolean[]{loose[0], loose[0]};
    }

    DimVector[] dims;
    Coordinates treegap; // gap converted to a Coordinates instance
    Coordinates linkgap; // same with linkshrink applied
    Coordinates[] cellsizes;
    boolean looseup, loosedown; // XXX ???


    // A "direction" in the ZZ space
    static class DimVector {
        String dim;
        int dir;
        DimVector rev;  // The reverse vector

        DimVector(String dim, int dir) {
            this.dim = dim;
            this.dir = dir;
            rev = new DimVector(this);
        }

        DimVector(DimVector rev) {
            dim = rev.dim;
            dir = -rev.dir;
            this.rev = rev;
        }

        ZZCell get(ZZCell c) {
            return c.s(dim, dir);
        }

        ZZCell end(ZZCell c) {
            return c.h(dim, dir);
        }

        ZZCell end(ZZCell c, boolean ensuremove) {
            return c.h(dim, dir, ensuremove);
        }

        ZZCell[] rank(ZZCell c, boolean includeThis) {
            return c.readRank(dim, dir, includeThis);
        }
    }

    class Coordinates {
        public int breadth;
        public int depth;

        public int x() {
            return depthhorizontal ? depth:breadth;
        }

        public int y() {
            return depthhorizontal ? breadth:depth;
        }

        public void set(int setx, int sety) {
            if (depthhorizontal) {
                depth = setx;
                breadth = sety;
            } else {
                breadth = setx;
                depth = sety;
            }
        }

        Coordinates(Dimension d) {
            set(d.width, d.height);
        }

        Coordinates(int b, int d) {
            breadth = b;
            depth = d;
        }

        Coordinates(int i, int j, boolean treecoords) {
            if (treecoords) {
                breadth = i;
                depth = j;
            } else set(i, j);
        }

        Coordinates(Coordinates c, float fract) {
            breadth = (int) (c.breadth * fract);
            depth = (int) (c.depth * fract);
        }
    }

    DimVector[] directeddims(boolean upwards) {
        return new DimVector[]{
                upwards ? dims[1].rev:dims[0],
                upwards ? dims[0].rev:dims[1],
                upwards ? dims[2].rev:dims[2]
        };
    }

    // STEP 1: Tree generation. This may be overridden
    // Note that "children" are cells away from root, and "parents" are
    // cells towards root. Only the links to the parents are drawn.
    CellInfo connectCell(ZZCell c, ZZCell linkc, ZZCell parentc,
                         int steps, boolean upwards) {
        CellInfo r = new CellInfo(c, linkc, parentc, steps, upwards);
        boolean more = steps < maxdepth - 1;

        DimVector[] ds = directeddims(upwards);
        DimVector linkdim = ds[0];
        DimVector targetdim = ds[1];
        DimVector tunneldim = ds[2];

        ZZCell target, parent;
        if ((target = tunneldim.get(c))!=null && more)
            r.childinfo.addElement(connectCell(target, null, c,
                    steps + 1, upwards));
        if ((parent = tunneldim.rev.get(c))!=null)
            r.parentlinks.addElement(parent);

        ZZCell[] links = linkdim.rank(c, true);
        for (int i = 0; more && i < links.length; i++) {
            target = targetdim.end(links[i], !loose[upwards ? 0:1]);
            if (target==null) continue;
            if (target==links[i])
                r.childinfo.addElement(connectCell(target, null, c,
                        steps + 1, upwards));
            else
                r.childinfo.addElement(connectCell(target, links[i], c,
                        steps + 1, upwards));
        }

        if ((parent = linkdim.rev.end(c))!=null && parent!=c)
            r.parentlinks.addElement(parent);

        ZZCell[] parentlinks = targetdim.rev.rank(c, false);
        for (int i = 0; i < parentlinks.length; i++)
            if (loose[upwards ? 1:0] || linkdim.rev.get(parentlinks[i])!=null)
                r.parentlinks.addElement(parentlinks[i]);

        return r;
    }

    class CellInfo {
        // Stuff set in step 1 (tree generation)
        ZZCell c, linkc, parentc;          // Linkcell CAN be null
        Vector childinfo = new Vector();
        Vector parentlinks = new Vector(); // can include linkless parents
        int steps;                         // from root
        boolean upwards;

        // Stuff set in step 2 (size & breadth computing)
        Coordinates size;
        int occupiedbreadth = 0;

        CellInfo(ZZCell c, ZZCell linkc, ZZCell parentc, int steps,
                 boolean upwards) {
            this.c = c;
            this.linkc = linkc;
            this.parentc = parentc;
            this.steps = steps;
            this.upwards = upwards;
        }

        // STEP 2: Compute the size of cell and breadth of tree part
        public int computesize() {
            size = cellsizes[steps];
            for (Enumeration e = childinfo.elements(); e.hasMoreElements(); )
                occupiedbreadth += ((CellInfo) e.nextElement()).computesize()
                                           + treegap.breadth;
            if (occupiedbreadth < size.breadth)
                occupiedbreadth = size.breadth;
            return occupiedbreadth;
        }

        // STEP 3: Put the cell and its parentlinks into a continuum
        public void putall(Continuum co, int bstart, int dstart) {
            int bcoord = bstart, dcoord = dstart;
            if (upwards) dcoord -= size.depth;
            Coordinates pos = new Coordinates(
                    bcoord + occupiedbreadth / 2 - size.breadth / 2,
                    dcoord);
            boolean newlypositioned = co.put(c, pos, size, steps, false);
            if (parentc!=null) {
                if (upwards) co.putlink(c, linkc, parentc);
                else co.putlink(parentc, linkc, c);
            }

            if (!newlypositioned && parentc!=null) return;

            int childdcoord = dcoord + treegap.depth * (upwards ? -1:1);
            if (!upwards) childdcoord += size.depth;
            bcoord += treegap.breadth / 2;
            for (Enumeration e = childinfo.elements(); e.hasMoreElements(); ) {
                CellInfo ci = (CellInfo) e.nextElement();
                ci.putall(co, bcoord, childdcoord);
                bcoord += ci.occupiedbreadth + treegap.breadth;
            }

            if (parentc==null) return;

            int bmiddle = pos.breadth + size.breadth / 2;
            Coordinates linksize = new Coordinates(size, linkshrink);
            int plnkbreadth = linkgap.breadth + linksize.breadth;
            bcoord = bmiddle - (int) (plnkbreadth * parentlinks.size() / 2);
            bcoord += linkgap.breadth / 2;
            dcoord += linkgap.depth * (upwards ? 1:-2);
            dcoord += size.depth * (upwards ? 1:0);

            for (Enumeration e = parentlinks.elements(); e.hasMoreElements(); ) {
                Coordinates lpos = new Coordinates(bcoord, dcoord);
                ZZCell plinkc = (ZZCell) e.nextElement();
                if (co.put(plinkc, lpos, linksize, steps, true))
                    co.putsmalllink(c, plinkc, upwards);
                bcoord += linksize.breadth + linkgap.breadth;
            }
        }
    }

    class Continuum {
        class Link {
            ZZCell fromc, linkc, toc;
        }

        // Information used for rastering
        Hashtable posns = new Hashtable(); // POSitionNS
        Hashtable sizes = new Hashtable();
        Hashtable depths = new Hashtable();
        Vector links = new Vector();
        Hashtable linksmalls = new Hashtable();

        // Information used only while generating the continuum
        Hashtable smalls = new Hashtable();
        Hashtable linkbigs = new Hashtable();

        Coordinates getpos(ZZCell c) {
            return (Coordinates) posns.get(c);
        }

        Coordinates getsize(ZZCell c) {
            return (Coordinates) sizes.get(c);
        }

        int getdepth(ZZCell c) {
            return ((Integer) depths.get(c)).intValue();
        }

        // Return false if the cell has already been positioned
        public boolean put(ZZCell c, Coordinates pos, Coordinates size,
                           int steps, boolean small) {
            if (posns.containsKey(c) && (small || !smalls.containsKey(c)))
                return false;
            posns.put(c, pos);
            sizes.put(c, size);
            depths.put(c, new Integer(steps + 1));
            if (small) smalls.put(c, c);
            return true;
        }

        public void putsmalllink(ZZCell fromc, ZZCell linkc, boolean up) {
            if (linkbigs.containsKey(linkc)) return;
            Link link = new Link();
            link.linkc = linkc;
            if (up) {
                link.fromc = fromc;
                link.toc = linkc;
            } else {
                link.fromc = linkc;
                link.toc = fromc;
            }
            linksmalls.put(linkc, link);
        }

        public void putlink(ZZCell fromc, ZZCell linkc, ZZCell toc) {
            Link link = new Link();
            link.fromc = fromc;
            link.linkc = linkc;
            link.toc = toc;
            links.addElement(link);
            if (linkc!=null && linksmalls.containsKey(linkc))
                linksmalls.remove(linkc);
            if (linkc!=null) linkbigs.put(linkc, link);
        }

        // STEP 4: Position link cells
        public void positionlinkcells() {
            for (Enumeration e = links.elements(); e.hasMoreElements(); ) {
                Link link = (Link) e.nextElement();

                if (link.linkc==null) continue; // only draw a line

                int fdepth = getdepth(link.fromc);
                int tdepth = getdepth(link.toc);
                int depth = fdepth > tdepth ? fdepth:tdepth;
                Coordinates size = new Coordinates(cellsizes[depth - 1],
                        linkshrink);
                Coordinates fpos = getpos(link.fromc);
                Coordinates fsize = getsize(link.fromc);
                Coordinates tpos = getpos(link.toc);
                Coordinates tsize = getsize(link.toc);
                Coordinates pos = new Coordinates(
                        (fpos.breadth + (fsize.breadth / 2) +
                                 tpos.breadth + (tsize.breadth / 2) - size.breadth) / 2,
                        (fpos.depth + fsize.depth + tpos.depth - size.depth) / 2);
                boolean succ = put(link.linkc, pos, size, depth, false);
                p((succ ? "S":"Uns") + "uccessfully positioned link cell: " +
                          link.linkc.getText());
            }
        }

        // STEP 5: Raster the continuum into a flob set
        public void raster(FlobSet into, FlobFactory fact, ZZCell view) {
            for (Enumeration e = posns.keys(); e.hasMoreElements(); ) {
                ZZCell c = (ZZCell) e.nextElement();
                Coordinates pos = getpos(c);
                Coordinates size = getsize(c);
                int depth = getdepth(c);
                fact.makeFlob(into, c, c, 1, pos.x(), pos.y(), depth,
                        size.x(), size.y());
            }
            for (Enumeration e = linksmalls.elements(); e.hasMoreElements(); ) {
                Link link = (Link) e.nextElement();
                putlink(link.fromc, null, link.toc);
            }
            for (Enumeration e = links.elements(); e.hasMoreElements(); ) {
                Link link = (Link) e.nextElement();
                int fdepth = getdepth(link.fromc);
                int tdepth = getdepth(link.toc);
                int depth = fdepth > tdepth ? fdepth:tdepth;
                Coordinates fpos = getpos(link.fromc);
                Coordinates fsize = getsize(link.fromc);
                Coordinates tpos = getpos(link.toc);
                Coordinates tsize = getsize(link.toc);

                Coordinates from = new Coordinates(
                        fpos.breadth + fsize.breadth / 2, fpos.depth + fsize.depth);
                Coordinates to = new Coordinates(
                        tpos.breadth + tsize.breadth / 2, tpos.depth);

                into.add(new LineDecor(from.x(), from.y(), to.x(), to.y(),
                        Color.red, depth));
            }
        }
    }


    public void initdims(String[] dims) {
        this.dims = new DimVector[]{
                new DimVector(dims[0], 1),
                new DimVector(dims[1], bothhead ? -1:1),
                new DimVector(dims[2], 1),
        };
    }

    public ZZCell look(ZZCell c, int x, int y) {
        Coordinates co = new Coordinates(x, y, false);
        DimVector d = null, t = null;
        if (co.depth==0)
            return null;
        else if (co.depth < 0) {
            d = dims[0];
            t = dims[3];
        } else {
            d = dims[1].rev;
            t = dims[3].rev;
        }

        if (co.breadth < 0)
            return d.get(c);
        else if (co.breadth > 0)
            return d.end(c, true);
        else {
            if (t.get(c)!=null) return t.get(c);
            ZZCell[] rank = d.rank(c, false);
            if (rank.length==0) return null;
            return rank[rank.length / 2];
        }
    }


    public void raster(FlobSet into, FlobFactory fact,
                       ZZCell view, String[] dims, ZZCell c) {
        Dimension cs = fact.getSize(null, initmul);
        cellsizes = new Coordinates[maxdepth];
        cellsizes[0] = new Coordinates(cs);
        for (int i = 1; i < maxdepth; i++)
            cellsizes[i] = new Coordinates(cellsizes[i - 1], shrink);
        treegap = new Coordinates(bgap, cellsizes[0].depth + dgap * 4);
        linkgap = new Coordinates(bgap / 4, dgap);

        Coordinates fs = new Coordinates(into.getSize());
        p("Prepared canvas size");

        initdims(dims);
        DimVector linkdim = this.dims[0], targetdim = this.dims[1],
                tunneldim = this.dims[2];
        p("Prepared DimVectors");

        CellInfo uproot, downroot;
        boolean islink;

        // CALL 1: Tree generation. This may be overridden
        p("Starting LevelRaster call one");

        if (linkdim.rev.get(c)!=null && targetdim.get(c)!=null) {
            islink = true;
            uproot = connectCell(linkdim.rev.end(c), null, c, 1, true);
            downroot = connectCell(targetdim.end(c), null, c, 1, false);
        } else {
            islink = false;
            uproot = connectCell(c, null, null, 0, true);
            downroot = connectCell(c, null, null, 0, false);
        }

        // CALL 2: Compute the size of cell and breadth of tree part
        p("Starting LevelRaster call two");

        uproot.computesize();
        downroot.computesize();

        // CALL 3: Put the cell and its parentlinks into a continuum
        p("Starting LevelRaster call three");

        Continuum co = new Continuum();
        int upbstart = (fs.breadth - uproot.occupiedbreadth) / 2;
        int downbstart = (fs.breadth - downroot.occupiedbreadth) / 2;
        int updstart = (fs.depth + cellsizes[0].depth) / 2;
        int downdstart = (fs.depth - cellsizes[0].depth) / 2;
        if (islink) {
            co.put(c, new Coordinates((fs.breadth - cellsizes[0].breadth) / 2,
                            downdstart), cellsizes[0],
                    0, false);
            updstart -= treegap.depth / 4 * 3; //+ cellsizes[0].depth;
            downdstart += treegap.depth / 4 * 3;
        }
        downroot.putall(co, downbstart, downdstart);
        uproot.putall(co, upbstart, updstart);

        // CALL 4: Position link cells
        p("Starting LevelRaster call four");

        co.positionlinkcells();

        // CALL 5: Raster the continuum into a flob set
        p("Starting LevelRaster call five");

        co.raster(into, fact, view);
    }
}

// vim: set syntax=java :

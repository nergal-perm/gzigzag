/* DO NOT EDIT THIS FILE. THIS FILE WAS GENERATED FROM ../Modules/nile/Nile1View.zob,
 * EDIT THAT FILE INSTEAD!
 * All changes to this file will be lost.
 */
/*   
Nile1View.java
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
package org.gzigzag.module;
import org.gzigzag.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/** A view for nile streams.
 */

public class Nile1View implements FlobView, ZOb {
public static final String rcsid = "$Id: Nile1View.zob,v 1.4 2001/01/26 13:53:27 ajk Exp $";
    public static boolean dbg = false;
    static void p(String s) { if(dbg) System.out.println(s); }
    static void pa(String s) { System.out.println(s); }


    
    
	/** The default font to use.
	 
 * <p>Default value: <PRE> new Font("SansSerif", Font.PLAIN, 11);</PRE>. 
 
 * @structparam 1 
 */ 

 public 
	Font font 
	    = new Font("SansSerif", Font.PLAIN, 11);	/** The distance between paragraphs.
	 
 * <p>Default value: <PRE> 8;</PRE>. 
 
 * @structparam 1 
 */ 

 public 
	int paraskip
	    = 8;	/** The left-side margin for normal text.
	 * Headings hang left of this margin.
	 
 * <p>Default value: <PRE> 50;</PRE>. 
 
 * @structparam 1 
 */ 

 public 
	int lmargin
	    = 50;/** UNDOCUMENTED. 
 
 * <p>Default value: <PRE> new FTextLayouter();</PRE>. 
 
 * @structparam 1 
 */ 

 public 	ZOb ftextlayouter
	    = new FTextLayouter();	/** Whether to use a special color for span (transcluded) cells. 
 * <p>Default value: <PRE> false;</PRE>. 
 
 * @structparam 1 
 */ 

 public 
	boolean showcopy
	    = false;
    

    /* AUTOGENERATED! */
    static final private int fullmask = 31;

    /* AUTOGENERATED! */
    public String readParams(ZZCell start) {
	int m = 0;
	try {
	    if(start != null)
		m = readParams(start, 0);
	} catch(Throwable t) {
	    ZZLogger.exc(t);
	} finally {
	    init__zob();
	}
	if((m & fullmask) != fullmask) {
	    // not all parameters present - no problem right now.
	}
	return "";
    }

    /* AUTOGENERATED! */
    private int readParams(ZZCell start, int mask) {
	ZZCell n = start;
	while(n != null) {
	    String s = n.getText();
	    // Tests autogenerated from members.
	    
	    if(s.equals("font")) {
		mask |= 1;
		try {
		    ZZCell c = n.s("d.1"); s = c.getText(); 
		    {
			ZZCell c1 = c.s("d.1");
			int size = 12;
			int style = Font.PLAIN;
			if(c1 != null) {
			    size = Integer.parseInt(c1.getText());
			    ZZCell c2 = c1.s("d.1");
			    if(c2 != null) {
				String sty = c2.getText();
				if(sty.equals("BOLD"))
				    style = Font.BOLD;
				if(sty.equals("ITALIC"))
				    style = Font.ITALIC;
				else {
				    // error...
				}
			    }
			}
			font = new Font(s, style, size);
		    }
 
		} catch(Exception e) {
		    ZZLogger.exc(e);
		}
	    } else


	    if(s.equals("paraskip")) {
		mask |= 2;
		try {
		    ZZCell c = n.s("d.1"); s = c.getText(); paraskip = Integer.parseInt(s);  
		} catch(Exception e) {
		    ZZLogger.exc(e);
		}
	    } else


	    if(s.equals("lmargin")) {
		mask |= 4;
		try {
		    ZZCell c = n.s("d.1"); s = c.getText(); lmargin = Integer.parseInt(s);  
		} catch(Exception e) {
		    ZZLogger.exc(e);
		}
	    } else


	    if(s.equals("ftextlayouter")) {
		mask |= 8;
		try {
		    ZZCell c = n.s("d.1"); s = c.getText(); 
    {
    ftextlayouter = ZZDefaultSpace.readZOb(c);
    // ftextlayouter = ZZDefaultSpace.newZOb(s);
    // ftextlayouter.readParams(c.s("d.2"));

    }
 
		} catch(Exception e) {
		    ZZLogger.exc(e);
		}
	    } else


	    if(s.equals("showcopy")) {
		mask |= 16;
		try {
		    ZZCell c = n.s("d.1"); s = c.getText(); 
		    if(s.equals("true")) showcopy=true;
		    else if(s.equals("false")) showcopy=false;
		    else { /* XXX ??? */ }
 
		} catch(Exception e) {
		    ZZLogger.exc(e);
		}
	    } else


	    { } // grab that last "else"
	    ZZCell h = n.h("d.3");
	    if(h != null && h != n) {
		// recurse
		mask |= readParams(h, mask);
	    }
	    n = n.s("d.2");
	}
	return mask;
    }



    /** Bold and italic derivatives of the font given.
     * The first index is the level of the text i.e.
     * from paragraph to H1.
     */
    Font[][][] bifonts;
    FontMetrics[][][] bifontms;

    protected void init__zob()  {
	bifonts = new Font[7][2][2];
	bifontms = new FontMetrics[7][2][2];
	for(int l = 0; l < bifonts.length; l++) {
	    int size = font.getSize();
	    if(l > 0 && l < 5) size += l-1;
	    if(l == 5) size += 6;
	    if(l == 6) size += 9;
	    bifonts[l][1][0] = new Font(font.getName(), 
				    Font.BOLD, size);
	    bifonts[l][1][1] = new Font(font.getName(), 
				    Font.BOLD | Font.ITALIC, size);

	    if(l == 0) {
		bifonts[l][0][0] = new Font(font.getName(), 0, size);
		bifonts[l][0][1] = new Font(font.getName(), 
					Font.ITALIC, size);
	    } else { // All bold for headers
		bifonts[l][0][0] = bifonts[l][1][0];
		bifonts[l][0][1] = bifonts[l][1][1];
	    }
	    for(int b = 0; b<2; b++)
	     for(int i = 0; i<2; i++)
		bifontms[l][b][i] = (ScalableFont.fmComp.getFontMetrics(bifonts[l][b][i]));
	}
    }

    Color selcolor = Color.white;
    Color copycolor = Color.yellow.darker().darker().darker().darker();

    /** Return the formatted text object for one paragraph.
     */
    FText paragraph(ZZCell start, ZZCell endBefore, ZZCursor offsetOfC, 
			int[]into,
			ZZCursor selectEnd)
    {
	p("Paragraph! "+start+" "+endBefore+" ");
	ZZCell selsc = null;
	int selso = 0;
	if(offsetOfC != null) {
	    selsc = offsetOfC.get();
	    selso = offsetOfC.getOffs();
	}
	ZZCell offsetOf = selsc;
	ZZCell selec = null;
	int seleo = 0;
	if(selectEnd != null) {
	    selec = selectEnd.get();
	    seleo = selectEnd.getOffs();
	}
	boolean selected = (selsc == null && selec != null);

	if(selso == ZZCursor.NO_OFFSET || selec == null) {
	    selsc = null;
	    selec = null;
	    selected = false;
	}
	
	FText.Part part  = null;

	int level = Nile2Iter.getLevel(start);
	int curOffs = 0;
	int b = 0, i = 0;
	Vector parts = new Vector();
	LoopDetector ld = new LoopDetector();
	LoopDetector ld2 = new LoopDetector();
	for(ZZCell c = start; c != null && c != endBefore; c = c.s("d.nile")) {
	    ld.detect(c);
	    for(ZZCell fs = c.s("d.nile-fmt-start"); fs != null; 
		fs = fs.s("d.nile-fmt-start")) {
		ld.detect(fs);
		if(fs.getText().equals("B")) b++;
		else if(fs.getText().equals("I")) i++;
		else p("Invalid format: "+fs.getText());
	    }
	    int b0 = (b > 0 ? 1 : 0);
	    int i0 = (i > 0 ? 1 : 0);
	    int len = c.getText().length();
	    
	    if(c == offsetOf && into != null) into[0] = curOffs;
	    int internoffs = 0;
	    if(c == selsc) {
		if(selso > len) {
		    ZZLogger.log("Warning: start over length "+selso+" "+len);
		    selso = len;
		}
		part = new FText.CellPart( c, 0, selso, 
			    bifonts[level][b0][i0], bifontms[level][b0][i0],
			    null, c.getSpan() == null ? null : copycolor);
		parts.addElement(part);
		internoffs = selso;
		selected = true;
		p("Start selection: "+selso+" "+selsc);
	    }
	    if(c == selec && seleo >= internoffs) {
		if(seleo > len) {
		    ZZLogger.log("Warning: end over length "+seleo+" "+len);
		    seleo = len;
		}
		part = new FText.CellPart( c, internoffs, seleo-internoffs, 
			    bifonts[level][b0][i0], bifontms[level][b0][i0],
			    selcolor, c.getSpan() == null ? null : copycolor);
		parts.addElement(part);
		internoffs = seleo;
		selected = false;
		selectEnd.set((ZZCell)null);
		p("End selection: "+seleo+" "+selec);
	    }
	    
	    part = new FText.CellPart( c, internoffs, len-internoffs, 
			    bifonts[level][b0][i0], bifontms[level][b0][i0],
			    selected ? selcolor : null, 
			    c.getSpan() == null ? null : copycolor);

	    parts.addElement(part);
	    curOffs += len;

	    for(ZZCell fe = c.s("d.nile-fmt-end"); fe != null; 
		fe = fe.s("d.nile-fmt-start")) {
		ld2.detect(fe); // Can't use same: same cell is start&end
		if(fe.getText().equals("B")) b--;
		else if(fe.getText().equals("I")) i--;
		else p("Invalid format: "+fe.getText());
	    }
	}
	return new FText(parts);
    }

    Rectangle pmarg(Rectangle r, ZZCell para) {
	int level = Nile2Iter.getLevel(para);
	Rectangle ret = new Rectangle(r);
	int w = (6 - level) * lmargin / 6;
	ret.x += w; ret.width -= w;
	return ret;
    }

    boolean[] beginningSentence = new boolean[2];
    public Rectangle placeParagraph(FlobSet into, 
	FText ft, ZZCell para,Rectangle origrect,
	int direction, int initY, int middleOffs) {
	beginningSentence[0] = true;
	beginningSentence[1] = false;
	// Margins.
	Rectangle rect = pmarg(origrect, para);
	Rectangle ret = ((FTextLayouter)ftextlayouter).place(into, ft, rect,
				    1,
				    direction, initY, middleOffs, false, 
				    beginningSentence);
	// Now, do the cool thing with the section headings: include
	// previous and next section headings of the same level here.
	if(Nile2Iter.getLevel(para) > 0) {

	ZZCell strcell = para.s("d.nile-struct");
	if(strcell == null) return ret;

	for(int dir = -1; dir <= 1; dir += 2) {
	    ZZCell prevsec = strcell.s("nile1str:breadth",dir);
	    if(prevsec == null) continue;
	    prevsec = prevsec.h("nile1str:depth").s("d.nile-struct", -1);
	    if(prevsec == null) continue;
	    beginningSentence[0] = true;
	    beginningSentence[1] = false;
	    FText nft = paragraph(prevsec, Nile2Iter.findStruct(prevsec, 1, false),
				    null, null, null);
	    ((FTextLayouter)ftextlayouter).place(into, nft, rect, 200,
			    dir, ret.y + (dir < 0 ? 0 : ret.height), 
			    0, false, beginningSentence);
	}
	}
	return ret;
    }


    public void raster(FlobSet into, FlobFactory fact,
	    ZZCell view, String[] dims, ZZCell accursed) {
	int[] offs = new int[1];

	LoopDetector ld = new LoopDetector();

	ZZCursor selectEnd = null;
	ZZCell sele = view.s("d.nile-sel");
	if(sele != null) {
	    selectEnd = ZZCursorVirtual.createFromReal(sele);
	}

	// Do the center paragraph first.
	ZZCell firstpara = Nile2Iter.findStruct(accursed, -1, true);
	ZZCell nextpara = Nile2Iter.findStruct(accursed, 1, false);

	FText ft = paragraph(firstpara, nextpara, new ZZCursorReal(view), 
				offs, selectEnd);

	Dimension size = into.getSize();
	Rectangle rect = new Rectangle(0, 0, size.width, size.height);
	
	Rectangle ctr = placeParagraph(into, ft, firstpara, rect, 
	    0, rect.y+rect.height/2,
	    ZZCursorReal.getVisualTextOffset(view));

	// Go into positive direction.
	Rectangle r = ctr;
	while(nextpara != null) {
	    ld.detect(nextpara);
	    ZZCell next = Nile2Iter.findStruct(nextpara, 1, false);
	    ft = paragraph(nextpara, next, null, null, selectEnd);

	    r = placeParagraph(into, ft, nextpara, rect,
		1, r.y+r.height + paraskip, 0);

	    nextpara = next;

	    if(r.y + r.height > size.height) break;
	}

	// And negative.
	r = ctr;
	while(true) {
	    ZZCell prev = firstpara;
	    firstpara = Nile2Iter.findStruct(firstpara, -1, false);
	    if(firstpara == null) break;
	    ld.detect(firstpara);

	    ft = paragraph(firstpara, prev, null, null, null);

	    beginningSentence[0] = true;
	    r = placeParagraph(into, ft, firstpara, rect,
		-1, r.y - paraskip, 0);

	    if(r.y < 0) break;
	}
	// into.dump();
	Flob cursf = into.findFlob(null, accursed);
	// p("Cursf: "+cursf);
	if(cursf != null) 
	    ((SplitCellFlob1)cursf).addCurs(into, ZZCursorReal.getOffs(view), 
				    Color.red);
    }
}


// vim: set syntax=java :

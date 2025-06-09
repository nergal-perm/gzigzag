/*   
ZZClangOps.java
 *
 * GENERATED USING ZZClangObs.pl - DO NOT MODIFY THE JAVA CODE:
 * YOUR CHANGES WILL BE OVERWRITTEN. MODIFY THE GENERATING
 * PERL CODE IN ZZClangObs.pl instead.
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
package org.gzigzag.clang;
import org.gzigzag.*;
import java.util.*;

/** Some primitive operations for Clang.
 */

public class ZZClangOps {
public static final String rcsid = "$Id: ZZClangOps.pl,v 1.5 2000/11/03 08:25:16 ajk Exp $";
    public static final boolean dbg = false;
    static final void p(String s) { if(dbg) System.out.println(s); }

    /** Convert a cell to a dimension. */
    static final String d(ZZCell c) { return c.getText(); }

    
	/** dim n */
	static class Step implements ZZClangOp {
	    public String name() { return "step"; }
	     public void exec(ZZCell c, ZZCursor crs, ZZClangContext ctxt)  
	    {
		/* if(params.length != 2)
		    throw new ZZError("Wrong number of params for step, got "+
			params.length+", was expecting 2");
		*/
		
	int n = Integer.parseInt((ctxt.paramAsCell(c.s("d.1", 2, null))).getText());
	String dim = d((ctxt.paramAsCell(c.s("d.1", 1, null))));
	ZZCell it =  crs .get();
	it = it.s(dim, n);
	 crs .set(it);
    
	    }
	}
    
	/** cell */
	static class Set implements ZZClangOp {
	    public String name() { return "set"; }
	     public void exec(ZZCell c, ZZCursor crs, ZZClangContext ctxt)  
	    {
		/* if(params.length != 1)
		    throw new ZZError("Wrong number of params for set, got "+
			params.length+", was expecting 1");
		*/
		
	 crs .set((ctxt.paramAsCell(c.s("d.1", 1, null))));
    
	    }
	}
    
	/** cell0 dim cell1 */
	static class Connect implements ZZClangOp {
	    public String name() { return "connect"; }
	     public void exec(ZZCell c, ZZCursor crs, ZZClangContext ctxt)  
	    {
		/* if(params.length != 3)
		    throw new ZZError("Wrong number of params for connect, got "+
			params.length+", was expecting 3");
		*/
		
	(ctxt.paramAsCell(c.s("d.1", 1, null))).connect(d((ctxt.paramAsCell(c.s("d.1", 2, null)))), (ctxt.paramAsCell(c.s("d.1", 3, null))));
    
	    }
	}
    
	/** dim cell1 */
	static class Connect2 implements ZZClangOp {
	    public String name() { return "connect2"; }
	     public void exec(ZZCell c, ZZCursor crs, ZZClangContext ctxt)  
	    {
		/* if(params.length != 2)
		    throw new ZZError("Wrong number of params for connect2, got "+
			params.length+", was expecting 2");
		*/
		
	 crs .get().connect(d((ctxt.paramAsCell(c.s("d.1", 1, null)))), (ctxt.paramAsCell(c.s("d.1", 2, null))));
    
	    }
	}
    
    
    public static ZZClangOp[] getOps1() {
	return new ZZClangOp[] {
	     new Step() ,
 new Set() ,
 new Connect() ,
 new Connect2() 
	};
    }
}

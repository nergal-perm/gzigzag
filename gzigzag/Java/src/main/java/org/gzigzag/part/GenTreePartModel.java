/*   
GTreePartModel.java
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
package org.gzigzag.part;

import org.gzigzag.ZZCell;

/**
 * A tree model that provides enough information for GTreePart to
 * make a tree appear in the structure.
 * Assumes that a real (or at least not generated by this GTreePart) cell
 * exists for each tree node or leaf.
 * The depth in the tree is a constant, and depth is counted from leaves
 * upwards, starting from 0, and the largest depth(cell) is depth()-1.
 * <p>
 * This interface is heavily geared towards the thinking of having
 * a single rank of cells, onto which the information about the levels
 * of hierarchy is somehow connected.
 * The same cell should <em>not</em> be given at different depths so
 * using the marker cells is recommended.
 * <p>
 * These are NOT documented clearly enough - see the sample implementation
 * in ../Modules/nile or GTreePartTestModel.
 *
 * @see GenTreePart
 */

public interface GenTreePartModel {
    String rcsid = "$Id: GenTreePartModel.java,v 1.1 2000/11/16 00:25:10 tjl Exp $";

    /**
     * The depth of the whole tree.
     */
    int depth();

    /**
     * The depth of this cell.
     * This method should return -1 if the cell does not belong
     * to the tree properly.
     */
    int depth(ZZCell c);

    /**
     * Move N steps of at least this depth pos- or negwards.
     */
    ZZCell getNext(int mindepth, ZZCell c, int steps);

    /**
     * Disconnect negwards from the given cell.
     */
    void disconnectNeg(ZZCell c);

    /**
     * Connect the given cell poswards to the other.
     */
    void connect(ZZCell c, ZZCell d);

    /**
     * Set the depth of the given cell.
     * This method should ONLY touch the depth of the given cell.
     * Its virtual "children" will be handled by the GTreePart.
     */
    void setDepth(ZZCell c, int d);
}





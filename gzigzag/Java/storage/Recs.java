/*   
Recs.java
 *
 * GENERATED USING Recs.pl - DO NOT MODIFY THE JAVA CODE:
 * YOUR CHANGES WILL BE OVERWRITTEN. MODIFY THE GENERATING
 * PERL CODE IN Recs.pl instead.
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
package org.gzigzag;
import java.util.*;
import java.io.*;

/** A generated class that defines a number of binary record
 * types.
 */

public class Recs {
static final void p(String s) { System.out.println(s); }

    static short getMagic(long offset) {
        return (short)(23415 ^ (offset % 30411));
    }
    public static class InvalidMagic extends ZZError {
        public InvalidMagic(String s) { super(s); }
    }
    static Record readRecord(long offs, DataInputStream s) {
    try {
	short mag = s.readShort();
	if(mag != getMagic(offs))
	    throw new InvalidMagic("Invalid record magic!"+offs+" "+mag+" "+getMagic(offs)+" ");
	short typ = s.readShort();

	Record r = createRecord(typ);

	// p("Before rd:\n "+r);
	r.read_data(offs, s);
	// p("After rd:\n "+r);
	return r;

	// if(getTypeNo() != typ)
	  //   throw new ZZError("Invalid record type!");
    } catch(Exception e) {
	ZZLogger.exc(e);
	throw new ZZError("IO ERROR!");
    }
    }

    static ByteArrayOutputStream bos = new ByteArrayOutputStream();
    static DataOutputStream os = new DataOutputStream(bos);
    static void writeRecord(Record r, Writable w) {
	bos.reset();
	writeRecord(r, os);
	w.write(r.offset, bos.toByteArray());
    }
    static void writeRecord(Record r, DataOutputStream s) {
	r.write(s);
    }

    static public abstract class Record {
	long offset;
	public long getOffset() { return offset; }
	public int length() { return 4; } // magic + type
	public abstract short getTypeNo();
	public void write_data(DataOutputStream s) throws IOException {
	    // Unsymmetric: read back in readRecord
	    s.writeShort(getMagic(offset));
	    s.writeShort(getTypeNo());
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    offset = offs;
	    // Skip 4 bytes, as those were read by readRecord already.
	}
	public String toString() {
	    return "    Offset: "+offset+"\n";
	}
	public void write(DataOutputStream s) {
	    try {
		write_data(s);
	    } catch(Exception e) {
		ZZLogger.exc(e);
		throw new ZZError("IOERRROR");
	    }
	}
	public void read(long offs, DataInputStream s) {
	    try {
		read_data(offs, s);
	    } catch(Exception e) {
		ZZLogger.exc(e);
		throw new ZZError("IOERRROR");
	    }
	}
	
    }


    static public class TreeRecord extends Record {
	int hash; int prevwhash;
	public int length() { 
	try {
	    return  super.length() +4+4; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 42; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    s.writeInt(hash);s.writeInt(prevwhash);
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    hash = s.readInt();prevwhash = s.readInt();
	}
	public String toString() {
	    return "TreeRecord:\n" 
		    + "    hash: "+hash+"\n"     + "    prevwhash: "+prevwhash+"\n" 
		+ super.toString();
	}
    }

    static public class ConnRecord extends TreeRecord {
	String cell; short othoffs;
	public int length() { 
	try {
	    return  super.length() + (2 + cell.getBytes("UTF8").length) +2; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 43; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    {
		   byte[] bt = cell.getBytes("UTF8");
		   s.writeShort(bt.length);
		   s.write(bt, 0, bt.length);
		  }
		 s.writeShort(othoffs);
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    {
		   short l = s.readShort();
		   byte[] bt = new byte[l];
		   int n = s.read(bt);
		   if(n!=l) throw new ZZError("NOT ENOUGH DATA");
		   cell = new String(bt, "UTF8");
		  }
		 othoffs = s.readShort();
	}
	public String toString() {
	    return "ConnRecord:\n" 
		    + "    cell: "+cell+"\n"     + "    othoffs: "+othoffs+"\n" 
		+ super.toString();
	}
    }

    static public class ContentRecord extends TreeRecord {
	String cell; boolean span; String content;
	public int length() { 
	try {
	    return  super.length() + (2 + cell.getBytes("UTF8").length) +1+ (2 + content.getBytes("UTF8").length) ; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 44; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    {
		   byte[] bt = cell.getBytes("UTF8");
		   s.writeShort(bt.length);
		   s.write(bt, 0, bt.length);
		  }
		 s.writeBoolean(span);{
		   byte[] bt = content.getBytes("UTF8");
		   s.writeShort(bt.length);
		   s.write(bt, 0, bt.length);
		  }
		 
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    {
		   short l = s.readShort();
		   byte[] bt = new byte[l];
		   int n = s.read(bt);
		   if(n!=l) throw new ZZError("NOT ENOUGH DATA");
		   cell = new String(bt, "UTF8");
		  }
		 span = s.readBoolean();{
		   short l = s.readShort();
		   byte[] bt = new byte[l];
		   int n = s.read(bt);
		   if(n!=l) throw new ZZError("NOT ENOUGH DATA");
		   content = new String(bt, "UTF8");
		  }
		 
	}
	public String toString() {
	    return "ContentRecord:\n" 
		    + "    cell: "+cell+"\n"     + "    span: "+span+"\n"     + "    content: "+content+"\n" 
		+ super.toString();
	}
    }

    static public class HdrRecord extends Record {
	short nbits; int mask; long lasthash; long end; 
		    long id;
	public int length() { 
	try {
	    return  super.length() +2+4+8+8+8; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 45; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    s.writeShort(nbits);s.writeInt(mask);s.writeLong(lasthash);s.writeLong(end);s.writeLong(id);
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    nbits = s.readShort();mask = s.readInt();lasthash = s.readLong();end = s.readLong();id = s.readLong();
	}
	public String toString() {
	    return "HdrRecord:\n" 
		    + "    nbits: "+nbits+"\n"     + "    mask: "+mask+"\n"     + "    lasthash: "+lasthash+"\n"     + "    end: "+end+"\n"     + "    id: "+id+"\n" 
		+ super.toString();
	}
    }

    static public class HashRecord extends Record {
	short nbits;
	public int length() { 
	try {
	    return  super.length() +2; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 46; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    s.writeShort(nbits);
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    nbits = s.readShort();
	}
	public String toString() {
	    return "HashRecord:\n" 
		    + "    nbits: "+nbits+"\n" 
		+ super.toString();
	}
    }

    static public class RootRecord extends Record {
	long nextCell;
	public int length() { 
	try {
	    return  super.length() +8; 
	} catch(Exception e) {
	    ZZLogger.exc(e);
	    throw new ZZError("ARGH");
	}
	}
	public short getTypeNo() { return 47; }
	public void write_data(DataOutputStream s) throws IOException {
	    super.write_data(s);
	    s.writeLong(nextCell);
	}
	public void read_data(long offs, DataInputStream s) 
	    throws IOException {
	    super.read_data(offs, s);
	    nextCell = s.readLong();
	}
	public String toString() {
	    return "RootRecord:\n" 
		    + "    nextCell: "+nextCell+"\n" 
		+ super.toString();
	}
    }

    static public Record createRecord(int typ) {
	if(typ == 42) return new TreeRecord(); 
if(typ == 43) return new ConnRecord(); 
if(typ == 44) return new ContentRecord(); 
if(typ == 45) return new HdrRecord(); 
if(typ == 46) return new HashRecord(); 
if(typ == 47) return new RootRecord(); 

	throw new ZZError("Invalid record type "+typ);
    }
}

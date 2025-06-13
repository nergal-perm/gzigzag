package org.gzigzag;

public abstract class ZZBaseCell implements ICell {
    protected final String id;

    protected ZZBaseCell(String id) {
        this.id = id;
    }

    @Override
    public String id() {
        return this.id;
    }

}

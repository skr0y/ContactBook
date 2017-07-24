package model.entities;

abstract public class Entity {
    protected abstract String getToString();
    public String toString() {
        return getToString();
    }
}

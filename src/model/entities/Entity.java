package model.entities;

abstract public class Entity {
    public abstract String getToString();
    public String toString() {
        return getToString();
    }
}

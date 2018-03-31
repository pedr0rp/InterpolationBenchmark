package com.pedrooorp;

public class Point<T> {
    protected T x;
    protected T y;

    Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "X " + x + " Y " + y;
    }
}

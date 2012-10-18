package com.earlofmarch.reach.input;

/**
 * Models an ordered pair (A, B).
 * Immutable.
 * @author Ian Dewan
 */
public class Pair<A, B> {
	private A first;
	private B second;
	
	public Pair(A f, B s) {
		first = f;
		second = s;
	}
	
	public A getFirst() {
		return first;
	}
	
	public B getSecond() {
		return second;
	}
	 @Override
	 public String toString() {
		 return "(" + first + ", " + second + ")";
	 }
}

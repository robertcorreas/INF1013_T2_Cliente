package model;

public abstract class Comparator {
	public static boolean notEquals(Object o1, Object o2) {
		return !((o1 == null && o2 == null) || (o1!=null && o1.equals(o2)));
	}
}

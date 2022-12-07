package utils;

import java.util.Comparator;

import entity.Student;

//Name Comparator class
public class NameComparator implements Comparator<Student>{
	@Override
	public int compare(Student s1, Student s2) {
		return s1.getCol1_firstName().compareTo(s2.getCol1_firstName());
	}
}

package hu.neuron.java.web.beans;

import java.util.Comparator;

import org.primefaces.model.SortOrder;

import hu.neuron.java.service.vo.UserVO;
import java.lang.reflect.Field;

public class LazySorter implements Comparator<UserVO> {

	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	@Override
	public int compare(UserVO object1, UserVO object2) {
		try {
			Field field1 = object1.getClass().getDeclaredField(this.sortField);
			Field field2 = object2.getClass().getDeclaredField(this.sortField);
			field1.setAccessible(true);
			field2.setAccessible(true);
			Object value1 = field1.get(object1);
			Object value2 = field2.get(object2);

			int value = ((Comparable) value1).compareTo(value2);
			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
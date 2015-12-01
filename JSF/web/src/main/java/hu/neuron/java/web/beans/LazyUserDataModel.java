package hu.neuron.java.web.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import hu.neuron.java.service.vo.UserVO;
import java.lang.reflect.Field;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real
 * datasource like a database.
 */
public class LazyUserDataModel extends LazyDataModel<UserVO> {

	private static final long serialVersionUID = 1L;
	private List<UserVO> datasource;

	public LazyUserDataModel(List<UserVO> datasource) {
		this.datasource = datasource;
	}

	@Override
	public UserVO getRowData(String rowKey) {

		for (UserVO user : datasource) {

			if (user.getId().equals(rowKey)) {
				return user;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(UserVO user) {
		return user.getId();
	}

	@Override
	public List<UserVO> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		List<UserVO> data = new ArrayList<UserVO>();

		// filter
		for (UserVO user : datasource) {
			boolean match = true;

			if (filters != null) {
				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						Field field = user.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String value = String.valueOf(field.get(user));

						if (filterValue == null || value.contains(filterValue.toString())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
					}
				}
			}

			if (match) {
				data.add(user);
			}
		}

		// sort
		if (sortField != null) {
			Collections.sort(data, new LazySorter(sortField, sortOrder));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}
}
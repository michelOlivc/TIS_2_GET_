package dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface GenericDAO<T, K> {
	public T get(K id);
	public void add(T t);
	public void update(T t) throws NumberFormatException, IOException;
	public void delete(T t) throws NumberFormatException, IOException;
	public List<T> getAll() throws FileNotFoundException, NumberFormatException, IOException;
}

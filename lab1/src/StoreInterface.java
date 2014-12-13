import java.util.HashMap;


public interface StoreInterface<T> {
	public int compareTo(T anotherObject);
	public void set(HashMap<String, String> objectMap);
	public abstract String[] getFields();
}

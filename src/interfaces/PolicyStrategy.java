package interfaces;

import java.util.ArrayList;
import java.util.Queue;

public interface PolicyStrategy<E> {

	ArrayList<Integer> execute( Queue[] q);
	String getName();
	
}

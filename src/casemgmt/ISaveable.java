package casemgmt;

import java.sql.Connection;

public interface ISaveable {
	public boolean isSaveable();

	public void save() throws Exception;

	public void save(Connection conn) throws Exception;
}

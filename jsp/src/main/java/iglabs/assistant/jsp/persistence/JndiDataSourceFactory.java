package iglabs.assistant.jsp.persistence;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JndiDataSourceFactory implements DataSourceFactory {
	public DataSource getDataSource() {
		try {
			Class.forName("org.sqlite.JDBC");
			
	        Context ctx = new InitialContext();
	        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/assistantdb");
	        
	        return ds;
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}

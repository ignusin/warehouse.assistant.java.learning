package iglabs.assistant.jsp.persistence;

import javax.sql.DataSource;

public interface DataSourceFactory {
	DataSource getDataSource();
}

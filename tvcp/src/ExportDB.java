

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * 导出数据库schema
 * @author <a href="mailto:xinyang921@gmail.com">杨信</a>
 * @version 1.0
 * @since Jan 19, 2009 9:58:10 AM
 */
public class ExportDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Configuration cfg = new Configuration().configure();
        SchemaExport schemaExport= new SchemaExport(cfg);
        schemaExport.create(false, true);

	}
	

}

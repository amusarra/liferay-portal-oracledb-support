package it.dontesta.labs.liferay.portal.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBType;

import it.dontesta.labs.liferay.portal.dao.db.OracleDB;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Antonio Musarra
 */
public class OracleDBTest extends BaseDBTestCase {

	@Test
	public void testDBType() throws IOException {
		Assert.assertEquals(
				DBType.ORACLE,
				getDB().getDBType());
	}

	@Override
	protected DB getDB() {
		return new OracleDB(0, 0);
	}

}
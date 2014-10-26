package cc.johnwu.sql;

import java.awt.Frame;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import multilingual.Language;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.his.JPAUtil;
import org.his.dao.SettingDao;
import org.his.model.Setting;
import org.his.util.CustomLogger;

public class DBC {

	public final static String LOCALURL;
	public final static String LOCALNAME;
	public final static String LOCALPASSWD;

	public static boolean isMobile;
	public static String s_ServerHost;
	public static String s_ServerDBName;
	public static String s_ServerURL;
	public static String s_ServerName;
	public static String s_ServerPasswd;

	private static Connection s_LocalConn;
	private static Statement s_LocalStmt;
	private static ResultSet s_LocalRS;

	private static javax.swing.JFrame s_EnteredFrm;

	private static Logger logger = LogManager.getLogger(DBC.class.getName());

	static {
		// File file = new File("");
		// LOCALURL =
		// "jdbc:hsqldb:file:"+file.getCanonicalPath()+"/db/localDB;shutdown=true";
		LOCALURL = "jdbc:hsqldb:file:./db/localDB;shutdown=true";
		LOCALNAME = "SA";
		LOCALPASSWD = "";
	}

	public DBC() {
		initLocalDB();
	}

	public DBC(javax.swing.JFrame frm) {
		s_EnteredFrm = frm;
		initLocalDB();
	}

	private void initLocalDB() {
		if (!isClosed())
			return;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			Class.forName("com.mysql.jdbc.Driver");
			if (!getConnection()) {
				new Frm_SettingMySQL().setVisible(true);
			}
		} catch (ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(new Frame(), ex);
		}
	}

	public static Connection getConnectionExternel() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(s_ServerURL, s_ServerName,
					s_ServerPasswd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnectionExternel(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static boolean getConnection() {
		try {
			String host;
			s_LocalConn = DriverManager.getConnection(LOCALURL, LOCALNAME,
					LOCALPASSWD);
			s_LocalStmt = s_LocalConn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			s_LocalRS = s_LocalStmt.executeQuery("SELECT * FROM conn_info");
			s_LocalRS.next();
			
			System.out.println(s_LocalRS.getString("isMobile"));
			isMobile = s_LocalRS.getString("isMobile").compareTo("Y") == 0 ? true : false;
			if(isMobile)
				host = "localhost";
			else
				host = s_LocalRS.getString("host").trim();
			
			s_ServerHost = host;
			s_ServerDBName = s_LocalRS.getString("database").trim();
			
			s_ServerURL = "jdbc:mysql://" + host
					+ ":" + s_LocalRS.getString("port").trim() + "/"
					+ s_LocalRS.getString("database").trim();
			s_ServerURL += "?characterEncoding=utf8";
			s_ServerName = HISPassword.deCode(
					s_LocalRS.getString("user").trim()).trim();
			s_ServerPasswd = HISPassword.deCode(
					s_LocalRS.getString("passwd").trim()).trim();

			JPAUtil.setPassword(s_ServerPasswd);
			JPAUtil.setUser(s_ServerName);
			JPAUtil.setUrl(s_ServerURL);

			Connection conn = DriverManager.getConnection(s_ServerURL,
					s_ServerName, s_ServerPasswd);
			conn.close();

			JPAUtil.getEntityManager().createNativeQuery("select now()")
					.getSingleResult();

			try {
				SettingDao settingDao = new SettingDao();
				Setting setting = settingDao.QuerySettingById(1);
				Language.getInstance().setLocale(setting.getLanguage());
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (s_EnteredFrm != null) {
				((cc.johnwu.login.Frm_Login) s_EnteredFrm).initLanguage();
				s_EnteredFrm.setVisible(true);
				s_EnteredFrm = null;
			}

			return true;
		} catch (SQLException ex) {
			try {
				s_LocalStmt.execute("CREATE TABLE conn_info ("
						+ "host char(40) PRIMARY KEY, " + "port char(5), "
						+ "database char(40), " + "user char(32), "
						+ "passwd char(32)"
						+ ", isMobile char(1)" + ")");
			} catch (SQLException ex1) {
			}
			Object[] options = { "Yes", "No" };
			int response = JOptionPane.showOptionDialog(new Frame(),
					"Can not connect database!\nDo you want to reset?",
					"Error Message", JOptionPane.YES_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == 0)
				return false;
			else if (response == 1)
				System.exit(0);
		}
		return false;
	}

	public static boolean isClosed() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(s_ServerURL, s_ServerName,
					s_ServerPasswd);
			conn.close();
			return false;
		} catch (SQLException ex) {
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
			}
		}
		return true;
	}

	public static void closeConnection(ResultSet rs) throws SQLException {
		if (rs == null)
			return;
		Statement stmt = rs.getStatement();
		rs.close();
		closeConnection(stmt);
	}

	public static void closeConnection(Statement stmt) throws SQLException {
		if (stmt == null)
			return;
		Connection conn = stmt.getConnection();
		if (stmt != null)
			stmt.close();
		if (conn != null)
			conn.close();
	}

	public static void closeConnection(PreparedStatement pstmt)
			throws SQLException {
		if (pstmt == null)
			return;
		Connection conn = pstmt.getConnection();
		if (pstmt != null)
			pstmt.close();
		if (conn != null)
			conn.close();
	}

	/** 執行給定的 SQL 語句，該語句返回單個 ResultSet 物件。 */
	public static ResultSet executeQuery(String sql) throws SQLException {
		cc.johnwu.login.OnlineState.OnlineState();
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		CustomLogger.debug(logger, sql);
		return stmt.executeQuery(sql);
	}

	public static Statement getQueryStatement() throws SQLException {
		cc.johnwu.login.OnlineState.OnlineState();
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		return conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
	}

	public static ResultSet executeQueryLogin(String sql) throws SQLException {
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		CustomLogger.debug(logger, sql);
		return stmt.executeQuery(sql);
	}

	/**
	 * 執行給定 SQL 語句，該語句可能為 INSERT、UPDATE 或 DELETE 語句，或者不返回任何內容的 SQL 語句（如 SQL DDL
	 * 語句）。
	 */
	public static int executeUpdate(String sql) throws SQLException {
		cc.johnwu.login.OnlineState.OnlineState();
		int count = 0;
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		count = stmt.executeUpdate(sql);
		closeConnection(stmt);
		CustomLogger.debug(logger, sql);
		return count;
	}

	public static int executeUpdateLogin(String sql) throws SQLException {
		int count = 0;
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		count = stmt.executeUpdate(sql);
		closeConnection(stmt);
		CustomLogger.debug(logger, sql);
		return count;
	}

	public static PreparedStatement prepareStatement(String sql)
			throws SQLException {
		Connection conn = DriverManager.getConnection(s_ServerURL,
				s_ServerName, s_ServerPasswd);
		return conn.prepareStatement(sql);
	}

	/** 執行給定的 SQL 語句，該語句返回單個 ResultSet 物件。 */
	public synchronized static ResultSet localExecuteQuery(String sql)
			throws SQLException {
		Connection conn = DriverManager.getConnection(LOCALURL, LOCALNAME,
				LOCALPASSWD);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		CustomLogger.debug(logger, "[LocalDB] {}", sql);
		return stmt.executeQuery(sql);
	}

	/**
	 * 執行給定 SQL 語句，該語句可能為 INSERT、UPDATE 或 DELETE 語句，或者不返回任何內容的 SQL 語句（如 SQL DDL
	 * 語句）。
	 */
	public static int localExecuteUpdate(String sql) throws SQLException {
		int count = 0;
		Connection conn = DriverManager.getConnection(LOCALURL, LOCALNAME,
				LOCALPASSWD);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		count = stmt.executeUpdate(sql);
		closeConnection(stmt);
		CustomLogger.debug(logger, "[LocalDB] {}", sql);
		return count;
	}
	
	public static void SyncDBtoServer() throws IOException{
		Runtime rt = Runtime.getRuntime();
		String cmd = "mk-table-sync --execute --database " 
				+ s_ServerDBName + " h=localhost,u="
				+ s_ServerHost + ",p="
				+ s_ServerPasswd + " h="
				+ s_ServerHost + ",u=" 
				+ s_ServerName + ",p="
				+ s_ServerPasswd;
		System.out.println(cmd);
		Process pr = rt.exec(cmd);
	}
	
	public static void dumpDBtoLocalServer() throws IOException{
		Runtime rt = Runtime.getRuntime();
		String cmd = "mysqldump -h " + s_ServerHost 
				+ " --opt --user=" 
				+ s_ServerName 
				+ " --password " 
				+ s_ServerPasswd + " "
				+ s_ServerDBName + " > hospital.sql";
		System.out.println(cmd);
		rt.exec(cmd);
		cmd = "mysql -u "
				+ s_ServerName + " -p "
				+ s_ServerPasswd + " "
				+ s_ServerDBName + " < hospital.sql";
		System.out.println(cmd);
		rt.exec(cmd);
	}
	
	public static void localEnableMobileHealth() throws SQLException {
		localSetMobileHealth("Y");
	}
	
	public static void localDisableMobileHealth() throws SQLException {
		localSetMobileHealth("N");
	}
	
	public static int localSetMobileHealth(String mark) throws SQLException {
		int count = 0;
		String sql = "UPDATE conn_info SET isMobile='" + mark + "'";
		Connection conn = DriverManager.getConnection(LOCALURL, LOCALNAME,
				LOCALPASSWD);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		count = stmt.executeUpdate(sql);
		closeConnection(stmt);
		CustomLogger.debug(logger, "[LocalDB] {}", sql);
		return count;
	}

	public synchronized static boolean localExecute(String sql)
			throws SQLException {
		boolean flag = false;
		Connection conn = DriverManager.getConnection(LOCALURL, LOCALNAME,
				LOCALPASSWD);
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		flag = stmt.execute(sql);
		closeConnection(stmt);
		CustomLogger.debug(logger, "[LocalDB] {}", sql);
		return flag;
	}

	public synchronized static PreparedStatement localPrepareStatement(
			String sql) throws SQLException {
		Connection conn = DriverManager.getConnection(LOCALURL, LOCALNAME,
				LOCALPASSWD);
		return conn.prepareStatement(sql);
	}

}
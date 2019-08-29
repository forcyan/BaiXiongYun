package dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.DriverManager;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory;
//import org.apache.commons.dbcp2.BasicDataSourceFactory;


public abstract class BaseDao {
	public static String sqlDialect;
	public static DataSource dataSource;
	private static Properties properties = new Properties();

    static{
        try{

        	InputStream rs = BaseDao.class.getClassLoader().getResourceAsStream("dbcp.properties");
        	properties.load(rs);
            //properties.load(is);
            System.out.println("properties"+properties);
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
	
	/**
	 * ��ȡ���ݿ����Ӷ���
	 */
	public Connection getConnection() {
		Connection conn = null;// �������Ӷ���
		Context ctx = null;
		// ��ȡ���Ӳ������쳣
		try {
			conn = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();// �쳣����
		}
		return conn;// �������Ӷ���
	}

	/**
	 * �ر����ݿ����ӡ�
	 * 
	 * @param conn ���ݿ�����
	 * @param stmt Statement����
	 * @param rs   �����
	 */
	public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
		// �����������Ϊ�գ���ر�
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// ��Statement����Ϊ�գ���ر�
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// �����ݿ����Ӷ���Ϊ�գ���ر�
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void closeAll(Connection conn, Statement stmt) {
		closeAll(conn, stmt, null);
	}

	/**
	 * ����ɾ���Ĳ���
	 * 
	 * @param sql    sql���
	 * @param params ��������
	 * @return ִ�н��
	 */
	public int executeUpdate(String sql, Object... params) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			if (conn != null && !conn.isClosed()) {
				pstmt = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				System.out.println(pstmt);
				result = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt);
		}
		return result;
	}

	public Object executeQuery(RSProcessor processor, String sql, Object... params) {
		Object result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			if (conn != null && conn.isClosed() == false) {
				pstmt = conn.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.length; i++) {
						pstmt.setObject(i + 1, params[i]);
					}
				}
				rs = pstmt.executeQuery();
				result = processor.process(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs);
		}
		return result;
	}
}

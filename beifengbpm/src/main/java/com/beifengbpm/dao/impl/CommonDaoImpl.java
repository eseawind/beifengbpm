package com.beifengbpm.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;







import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.beifengbpm.dao.CommonDao;

public class CommonDaoImpl extends HibernateDaoSupport implements CommonDao {

	public void add(Object obj) throws SQLException {
		this.getHibernateTemplate().save(obj);
	}

	@Override
	public void del(Object obj) throws SQLException {
		this.getHibernateTemplate().delete(obj);
	}

	@Override
	public void update(Object obj) throws SQLException {
		this.getHibernateTemplate().update(obj);

	}

	@Override
	public Object queryObject(String hql) throws SQLException {
		Object obj = null;
		List<?> list = this.getHibernateTemplate().find(hql);
		if(list!=null&&list.size()>0){
			obj=list.get(0);
		}
		return obj;
	}

	@Override
	public List<?> queryList(String hql) throws SQLException {
		List<?> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<?> queryList(String hql, Object[] objs) throws SQLException {
		List<?> list = this.getHibernateTemplate().find(hql, objs);
		return list;
	}

	@Override
	public Object queryObject(String hql, Object[] objs) throws SQLException {
		Object obj = null;
		List<?> list = this.getHibernateTemplate().find(hql, objs);
		if(list!=null && list.size()>0){
			obj = list.get(0);
		}
		return obj;
	}

	@Override
	public List<?> queryListforPage(final String hql, final int start, final int limit)
			throws SQLException {
		List<?> list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setMaxResults(limit);
				query.setFirstResult(start);
				return query.list();
			}
		});
		return list;
	}

	public List queryBySQL(final String sql) throws SQLException {
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return list;
	}

	@Override
	public void executeBySQL(final String sql) throws SQLException {
		this.getHibernateTemplate().executeFind(new HibernateCallback(){
			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Connection connection = session.connection();
				Statement stmt = connection.createStatement();
				stmt.execute(sql);
				session.clear();
				return null;
			}
		});
	}

}

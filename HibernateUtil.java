package com.harvester;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = new Configuration().configure().addAnnotatedClass(Farmer.class).buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}

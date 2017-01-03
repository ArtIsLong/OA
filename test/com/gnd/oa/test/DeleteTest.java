package com.gnd.oa.test;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.domain.Privilege;

@SuppressWarnings("unchecked")
@Component
public class DeleteTest {

	@Resource
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.openSession();
	}
	
	@Transactional
	public void delete(){
		Session session = getSession();
		List<Privilege> privileges = session.createQuery("from Privilege").list();
		for(Privilege priv:privileges){
			session.delete(priv);
			session.flush();
		}
	}
	
	public static void main(String[] args) {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		DeleteTest del = (DeleteTest) ac.getBean("deleteTest");
		del.delete();
	}
	
	/*@Test
	public void testMd5(){
		System.out.println(DigestUtils.md5Hex("admin"));
	}*/
}

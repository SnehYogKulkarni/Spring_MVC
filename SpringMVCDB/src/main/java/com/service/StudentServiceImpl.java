package com.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import com.entities.Student;

//Implementation of the service methods
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	private StudentService studentService;
	
	@Autowired
	public StudentServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException ex) {
			session = sessionFactory.openSession();
		}
	}

	
	public StudentServiceImpl(StudentService studentService) {
		super();
		this.studentService = studentService;
	}
	
	@Override
	public List<Student> findAll() {
		Transaction tx = session.beginTransaction();
		List<Student> student = session.createQuery("from Student").list();
		System.out.print("findall list " + student.toString());
		tx.commit();
		return student;
	}

	@Override
	public Student findById(int id) {
		Transaction tx = session.beginTransaction();
		Student student = session.get(Student.class, id);
		tx.commit();
		return student;
	}

	@Override
	public void save(Student student) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(student);
		tx.commit();

	}

	@Override
	public void deleteById(int id) {
		Transaction tx = session.beginTransaction();
		Student student = session.get(Student.class, id);
		if (student != null)
			session.delete(student);
		tx.commit();

	}
}

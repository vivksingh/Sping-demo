package com.example;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class HQLExample {
    public static void main(String[] args) {
        // Obtain session from the SessionFactory
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            // Begin a new transaction
            transaction = session.beginTransaction();

            // Create and save new employee objects
            Employee emp1 = new Employee("John Doe", "Engineering");
            Employee emp2 = new Employee("Jane Smith", "Marketing");
            Employee emp3 = new Employee("Robert Brown", "Engineering");

            session.persist(emp1);
            session.persist(emp2);
            session.persist(emp3);

            // Commit the transaction
            transaction.commit();

            // Begin a new transaction for querying
            transaction = session.beginTransaction();

            // HQL query to fetch all employees
            String hql = "FROM Employee";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            List<Employee> employees = query.list();

            // Display the employees
            System.out.println("All Employees:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }

            // HQL query to fetch employees in the Engineering department
            hql = "FROM Employee E WHERE E.department = :dept";
            query = session.createQuery(hql, Employee.class);
            query.setParameter("dept", "Engineering");
            employees = query.list();

            // Display the employees in the Engineering department
            System.out.println("\nEmployees in Engineering Department:");
            for (Employee employee : employees) {
                System.out.println(employee);
            }

            // Commit the transaction
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UserDaoImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

   @Override
   public User carOwner(String model, int series) {
      String hql = "SELECT c.user FROM Car c WHERE c.model = :model AND c.series = :series";
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      query.setMaxResults(1);

      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         System.out.println("Owner not found");
         return null;
      }
   }
//    @Override
//    public User carOwner(String model, int series) {
//        String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
//        return sessionFactory.getCurrentSession()
//                .createQuery(hql, User.class)
//                .setParameter("model", model)
//                .setParameter("series", series)
//                .uniqueResult();
//    }

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public void addCar(Car car) {
       sessionFactory.getCurrentSession().save(car);

    }

}

package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();


		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);

			// 타입 정보를 명기할 수 있는 경우
			TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
			TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
			// 타입 정보를 명기할 수 없는 경우
			Query query3 = em.createQuery("select m.username, m.age from Member m");
			// 찾아오는 데이터의 타입이 복수 일 경우 Member.class 와 같이 타입을 명기할 수가 없게된다.

			List<Member> resultList = query1.getResultList(); // 결과가 하나 이상일 경우 List 타입으로 반환
			Member result = query1.getSingleResult(); // 결과가 하나 일 경우 사용하는 메소드(식별자를 잘 활용해야 한다.)


			// 파라미터 바인딩
			/*
			TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = :username", Member.class);
			query4.setParameter("username", "member1");
			Member singleResult = query4.getSingleResult();
			System.out.println("singleResult = " + singleResult.getUsername());
			 */

			// 파라미터 바인딩 - 체인 활용
			Member param_result = em.createQuery("select m from Member m where m.username = :username", Member.class)
					.setParameter("username", "member1")
					.getSingleResult();
			System.out.println("param_result = " + param_result.getUsername());
			tx.commit();
		} catch (Exception e){
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}


		emf.close();
	}

}

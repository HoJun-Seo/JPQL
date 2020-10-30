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

			/*
			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);
			 */

			for (int i = 0; i < 100; i++){
				Member member = new Member();
				member.setUsername("member" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();

			// 타입 정보를 명기할 수 있는 경우
			/*
			TypedQuery<Member> query1 = em.createQuery("select m from Member m", Member.class);
			TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
			// 타입 정보를 명기할 수 없는 경우
			Query query3 = em.createQuery("select m.username, m.age from Member m");
			// 찾아오는 데이터의 타입이 복수 일 경우 Member.class 와 같이 타입을 명기할 수가 없게된다.

			List<Member> resultList = query1.getResultList(); // 결과가 하나 이상일 경우 List 타입으로 반환
			Member result = query1.getSingleResult(); // 결과가 하나 일 경우 사용하는 메소드(식별자를 잘 활용해야 한다.)
			 */

			// 파라미터 바인딩
			/*
			TypedQuery<Member> query4 = em.createQuery("select m from Member m where m.username = :username", Member.class);
			query4.setParameter("username", "member1");
			Member singleResult = query4.getSingleResult();
			System.out.println("singleResult = " + singleResult.getUsername());
			 */

			// 파라미터 바인딩 - 체인 활용
			/*
			Member param_result = em.createQuery("select m from Member m where m.username = :username", Member.class)
					.setParameter("username", "member1")
					.getSingleResult();
			System.out.println("param_result = " + param_result.getUsername());
			 */

			// 프로젝션
			/*
			List<Member> result = em.createQuery("select m from Member m", Member.class)
					.getResultList();

			Member findMember = result.get(0);
			findMember.setAge(20);
			 */

			/* // 테이블 inner 조인 엔티티 프로젝션
			List<Team> result_team = em.createQuery("select m.team from Member m", Team.class)
					.getResultList(); */

			// 테이블 inner 조인을 SQL 로 직접 작성해준 코드
			/*
			List<Team> result_team = em.createQuery("select t from Member m join m.team t", Team.class)
					.getResultList();
			 */

			// 임베디드 타입 프로젝션
			/*
			List<Address> result_address = em.createQuery("select o.address from Order o", Address.class)
					.getResultList();
			 */

			// 스칼라 타입 프로젝션
			/*
			em.createQuery("select distinct m.username, m.age from Member m")
			.getResultList();
			 */

			// 프로젝션에서 한번에 여러 타입의 값을 가져와야 할 경우(Object[] 타입 활용)
			/*
			List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
					.getResultList();
			Object[] result = resultList.get(0);
			System.out.println("result = " + result[0]);
			System.out.println("result = " + result[1]);
			 */

			// new 명령어 활용(MemberDTO 클래스가 있어야 한다.)
			/*
			List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
					.getResultList(); // 마치 생성자를 통해 객체를 생성하는 것 처럼 JPQL 을 작성해준다.

			MemberDTO memberDTO = result.get(0);
			System.out.println("memberDTO = " + memberDTO.getUsername());
			System.out.println("memberDTO = " + memberDTO.getAge());
			 */

			List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
					.setFirstResult(1)
					.setMaxResults(10)
					.getResultList();

			System.out.println("result.s = " + result.size());
			for (Member member1 : result) {
				System.out.println("member1 = " + member1);
			}

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

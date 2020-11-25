package jpql;

import javax.persistence.*;
import java.util.Collection;
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

			// 페이징 기능 확인을 위한 반복작업
			/*
			for (int i = 0; i < 100; i++){
				Member member = new Member();
				member.setUsername("member" + i);
				member.setAge(i);
				em.persist(member);
			}

			em.flush();
			em.clear();
			 */

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

			// 페이징 기능 확인
			/*
			List<Member> result = em.createQuery("select m from Member m order by m.age desc", Member.class)
					.setFirstResult(1)
					.setMaxResults(10)
					.getResultList();

			System.out.println("result.s = " + result.size());
			for (Member member1 : result) {
				System.out.println("member1 = " + member1);
			}
			 */

			// 조인

			/*
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);
			 */

			/*
			Member member = new Member();
			//member.setUsername("teamA");
			//member.setUsername(null); // COALESCE 활용
			member.setUsername("관리자1"); // NULLIF 활용
			member.setTeam(team);
			member.setAge(10);
			 */
			//member.setTeam(team);

			// JPQL 타입 표현과 기타식
			//member.setType(MemberType.ADMIN);

			//member.setTeam(team);

			//em.persist(member);

			/*
			Member member1 = new Member();
			member1.setUsername("관리자2");
			member1.setAge(20);
			member1.setTeam(team);
			em.persist(member1);
			 */

			//em.flush();
			//em.clear();

			//String query = "select m from Member m inner join m.team t"; // 내부 조인
			//String query = "select m from Member m left outer join m.team t"; // 외부 조인
			//String query = "select m from Member m, Team t where m.username = t.name"; // 세타 조인
			//String query = "select m from Member m left join m.team t on t.name = 'teamA'"; // 조인 대상 필터링
			//String query = "select m from Member m left join Team t on m.username = t.name"; // 연관관계 없는 엔티티 외부 조인
			
			// select 절 에서 서브 쿼리를 사용하는 경우
			//String query = "select (select avg(m1.age) FROM Member m1) as avgAge from Member m join Team t on m.username = t.name";
			//List<Member> result = em.createQuery(query, Member.class)
			//		.getResultList();

			//현재 JPA 에서는 from 절에서 서브 쿼리를 작성하는 기능을 지원하지 않고 있다.
			/*
			String query_from = "select mm.age, mm.username" +
					"from (select m.age, m.username from Member m) as mm";
			List<Member> result_from = em.createQuery(query_from, Member.class)
					.getResultList();
			 */

			// JPQL 타입 표현과 기타식
			//String query = "select m.username, 'HELLO', TRUE From Member m " +
			//		"where m.type = jpql.MemberType.ADMIN";
			//List<Object[]> result = em.createQuery(query)
			//		.getResultList();
			/*
			String query = "select m.username, 'HELLO', TRUE From Member m " +
					"where m.type = :userType"; // 파라미터 바인딩
			List<Object[]> result = em.createQuery(query)
					.setParameter("userType", MemberType.ADMIN)
					.getResultList();

			for (Object[] objects : result){
				System.out.println("objects = " + objects[0]);
				System.out.println("objects = " + objects[1]);
				System.out.println("objects = " + objects[2]);
			}
			 */

			// JPQL 조건식(기본 CASE 식)
			/*
			String query = "select " +
					"case when m.age <= 10 then '학생요금' " +
					"     when m.age >= 60 then '경로요금' " +
					"     else '일반요금' " +
					"end " +
					"from Member m";
			 */

			// COALESCE 활용
			//String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
			// Member 객체에 삽입된 데이터의 회원 이름 값을 null 로 바꾼 후 실행해보자.

			// NULLIF 활용
			/*
			String query = "select NULLIF(m.username, '관리자') from Member m";
			// Member 객체에 삽입된 데이터의 회원 이름 값을 '관리자' 로 바꾼 후 실행해보자.
			List<String> result = em.createQuery(query, String.class)
					.getResultList();
			for (String s : result){
				System.out.println("s = " + s);
			}
			 */

			//String query = "select 'a' || 'b' FROM Member m";
			//String query = "select concat('a', 'b') FROM Member m"
			//String query = "select substring(m.username, 2, 3) From Member m";
			//String query = "select locate('de', 'abcdefg') From Member m";
			// 결과로 숫자를 반환하는 표준 함수 이기 때문에 List 의 반환값 타입을 Integer 로 설정해야 한다.
			// String query = "select t.members.size From Team t";
			/*
			List<Integer> result = em.createQuery(query, Integer.class)
					.getResultList();
			for(Integer s : result){
				System.out.println("s = " + s);
			}
			 */
			// 사용자 정의 함수
			// String query = "select function('group_concat', m.username) From Member m";



			// 경로 탐색 - 상태 필드
			// String query = "select m.username From Member m";

			// 경로 탐색 - 단일 값 연관 경로(탐색 가능)
			// String query = "select m.team From Member m";
			// m.team.name 과 같은 탐색이 가능하다.

			// 경로 탐색 - 컬렉션 값 연관 경로(탐색 불가능)
			//String query = "select t.members FROM Team t";
			// 여기서 members 는 Team 클래스에서 Member 클래스와 @OneToMany 연관관계로 선언된 컬렉션이다.

			/*
			Collection result = em.createQuery(query, Collection.class)
					.getResultList();
			for (Object o : result) {
				System.out.println("o = " + o);
			}
			 */

			Team teamA = new Team();
			teamA.setName("팀A");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("팀B");
			em.persist(teamB);

			Member member1 = new Member();
			member1.setUsername("회원1");
			member1.setTeam(teamA);
			em.persist(member1);

			Member member2 = new Member();
			member2.setUsername("회원2");
			member2.setTeam(teamA);
			em.persist(member2);

			Member member3 = new Member();
			member3.setUsername("회원3");
			member3.setTeam(teamB);
			em.persist(member3);

			em.flush();
			em.clear();

			//String query = "select m From Member m";
			// 다대일 관계일 때 페치 조인
			//String query = "select m from Member m join fetch m.team";
			// 다대일 관계 페치 조인에서 중복 제거
			//String query = "select distinct t from Team t join fetch t.members";
			// 페치 조인과 일반 조인의 차이
			String query = "select t from Team t join t.members m";

			List<Team> result = em.createQuery(query, Team.class)
					.getResultList();

			System.out.println("result = " + result.size());

			for (Team team : result){
				System.out.println("team = " + team.getName() + "| members = " + team.getMembers().size());
				for (Member member : team.getMembers()){
					System.out.println("-> member = " + member);
				}
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

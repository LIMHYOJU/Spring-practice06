package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = {"dao","springTest","service"})
@Configuration
@EnableTransactionManagement
public class MemberConfig {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("org.postgresql.Driver");
		ds.setUrl("jdbc:postgresql://localhost:5432/spring5test");
		ds.setUsername("adminhyoju");
		ds.setPassword("1234");
		ds.setInitialSize(2);//커넥션 풀을 초기화할때 초기 커넥션 개수지정 기본값 10
		ds.setMaxActive(100);//커넥션 풀에서 가져올수있는 최대 커넥션 개수지정 기본값 100
		ds.setTestWhileIdle(true);  //유휴 커넥션 검사
		ds.setMinEvictableIdleTimeMillis(1000* 60 * 3); //최소 유휴 시간 3분
		ds.setTimeBetweenEvictionRunsMillis(10 * 1000);//10초 주기
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
//	@Bean
//	public MemberDao memberDao() {
//		return new MemberDao(dataSource());
//	}
//
//	@Bean
//	public MemberRegisterService memberRegSvc() {
//		return new MemberRegisterService(memberDao());
//	}
//
//	@Bean
//	public ChangePasswordService changePwdSvc() {
//		ChangePasswordService pwdSvc = new ChangePasswordService();
//		pwdSvc.setMemberDao(memberDao());
//		return pwdSvc;
//	}
}

package my.learn.orderservice.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class DbConfig {

//@Bean
//public DriverManagerDataSource driverManagerDataSource() {
//    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//    dataSource.setUrl("jdbc:mysql://localhost:3306/testdb");
//    dataSource.setUsername("root");
//    dataSource.setPassword("qwerty123456");
//    return dataSource;
//}
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DriverManagerDataSource dataSource) {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource);
//        emf.setPackagesToScan("my.learn.model");
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        return emf;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(emf);
//        return transactionManager;
//    }
}

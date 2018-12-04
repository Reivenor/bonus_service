package ru.phil_it.tender.dev_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class BonusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonusServiceApplication.class, args);
	}

/*    @Bean
	public JettyServletWebServerFactory jettyServletWebServerFactory(){
	     JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
	     factory.setPort(8080);
	     factory.addServerCustomizers(
                 (Server server) -> {
                     final QueuedThreadPool threadPool = server.getBean(QueuedThreadPool.class);
                     //threadPool.setMinThreads(100);
                     threadPool.setMaxThreads(50);
                     threadPool.setIdleTimeout(2);
                 });

        return factory;
    }*/
}

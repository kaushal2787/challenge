package com.dws.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeApplication {

	/*@Autowired
	private static AccountsService accountsService;*/

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);

		/*AccountsController accountsController = new AccountsController(accountsService);

			Thread t1 = new Thread(() -> {
                try {
                    accountsController.transfer("1", "2", new Amount(new BigDecimal(100)));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

		Thread t2 = new Thread(() -> {
			try {
				accountsController.transfer("1", "2", new Amount(new BigDecimal(100)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		Thread t3 = new Thread(() -> {
			try {
				accountsController.transfer("1", "2", new Amount(new BigDecimal(100)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		Thread t4 = new Thread(() -> {
			try {
				accountsController.transfer("1", "2", new Amount(new BigDecimal(100)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		Thread t5 = new Thread(() -> {
			try {
				accountsController.transfer("1", "2", new Amount(new BigDecimal(100)));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();*/

		}
	}

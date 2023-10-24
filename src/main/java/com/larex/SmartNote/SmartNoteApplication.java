package com.larex.SmartNote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartNoteApplication {

	@Value("${jwt.secret}")
	static String secret;
	public static void main(String[] args) {
		SpringApplication.run(SmartNoteApplication.class, args);
		System.out.println(secret+"pppppppppppppppppppppppppppppppppppppp");
	}

}

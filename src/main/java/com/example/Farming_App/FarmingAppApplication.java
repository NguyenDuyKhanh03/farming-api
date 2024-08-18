package com.example.Farming_App;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Role;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FarmingAppApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(FarmingAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

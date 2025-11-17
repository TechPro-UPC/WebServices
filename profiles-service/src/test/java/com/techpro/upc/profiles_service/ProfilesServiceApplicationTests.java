package com.techpro.upc.profiles_service;

import com.techpro.upc.profiles_service.domain.model.aggregates.Psychologist;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProfilesServiceApplicationTests {

	@Test
	void contextLoads() {
	}

 //    @Test
 //   void shouldCreatePsychologist() {
 //       Psychologist psychologist = new Psychologist();
 //       psychologist.setFirstName("Juan");
 //       psychologist.setLastName("Pérez");
 //       psychologist.setDni("12345678");
 //       psychologist.setPhone("987654321");
 //       psychologist.setGender("M");
 //       psychologist.setLicenseNumber("LIC-2025");
 //       psychologist.setSpecialization("Terapia Cognitivo Conductual");

 //       assertEquals("Juan", psychologist.getFirstName());
 //   }

}

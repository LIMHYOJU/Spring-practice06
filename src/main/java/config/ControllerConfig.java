package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import controller.RegisterController;
import controller.SurveyController;

@Configuration
public class ControllerConfig {

	@Bean
	public RegisterController registerController() {
		return new RegisterController();
	}
	
	@Bean
	public SurveyController surveyController() {
		return new SurveyController();
	}
}

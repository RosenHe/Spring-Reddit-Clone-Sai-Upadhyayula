package com.example.communities_post_website;

import com.example.communities_post_website.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
/*added @Import(SwaggerConfiguration.class) to our SpringRedditCloneApplication. This should enable Swagger and Springfox in our application.
* */
public class CommunitiesPostWebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunitiesPostWebsiteApplication.class, args);
	}

}

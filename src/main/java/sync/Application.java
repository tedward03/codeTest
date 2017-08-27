package sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import sync.controller.ShoppingListController;
import sync.persistence.DataStore;
import sync.persistence.DataStoreManager;
import sync.persistence.util.ShoppingListMapper;
import sync.service.ShoppingListService;


@SpringBootApplication
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	@Bean
	public ShoppingListController shoppingListController(){
    	return new ShoppingListController();
	}

	@Bean
	public ShoppingListService shoppingListService(){
		return new ShoppingListService();
	}

	@Bean
	public DataStoreManager dataStoreManager(){
		return new DataStoreManager();
	}

	@Bean
	public DataStore dataStore(){
		return new DataStore();
	}

	@Bean
	public ShoppingListMapper shoppingListMapper(){
		return new ShoppingListMapper();
	}
}

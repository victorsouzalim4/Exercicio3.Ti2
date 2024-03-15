package app;
import static spark.Spark.*;
import Service.JogoService;

public class Aplicacao {

		private static JogoService JogoService = new JogoService();
		
	    public static void main(String[] args) {
	        port(4567);
	        
	        staticFiles.location("/public");
	        
	        post("/produto/insert", (request, response) -> JogoService.insert(request, response));

	        get("/produto/:id", (request, response) -> JogoService.get(request, response));
	        
	        get("/produto/list/:orderby", (request, response) -> JogoService.getAll(request, response));

	        get("/produto/update/:id", (request, response) -> JogoService.getToUpdate(request, response));
	        
	        post("/produto/update/:id", (request, response) -> JogoService.update(request, response));
	           
	        get("/produto/delete/:id", (request, response) -> JogoService.delete(request, response));

	             
	    }
	}


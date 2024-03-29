package Service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import DAO.JogosDao;
import Model.Jogo;
import spark.Request;
import spark.Response;


public class JogoService {

	private JogosDao jogosDao = new JogosDao();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public JogoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Jogo(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Jogo(), orderBy);
	}

	
	public void makeForm(int tipo, Jogo jogo, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umProduto = "";
		if(tipo != FORM_INSERT) {
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/jogo/list/1\">Novo Jogo</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String name, type, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Jogo";
				type = "FPS, RPG, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + jogo.getID();
				name = "Atualizar Produto (ID " + jogo.getID() + ")";
				type = jogo.getTipo();
				buttonLabel = "Atualizar";
			}
			umProduto += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ jogo.getNome() +"\"></td>";
			umProduto += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ jogo.getPreco() +"\"></td>";
			umProduto += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"tipo\" value=\""+ jogo.getTipo() +"\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"ano\" value=\""+ jogo.getAno() + "\"></td>";
			//umProduto += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+ produto.getDataValidade().toString() + "\"></td>";
			umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (ID " + jogo.getID() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;nome: "+ jogo.getNome() +"</td>";
			umProduto += "\t\t\t<td>Preco: "+ jogo.getPreco() +"</td>";
			umProduto += "\t\t\t<td>Tipo: "+ jogo.getTipo() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Ano "+ jogo.getAno() + "</td>";
			//umProduto += "\t\t\t<td>Data de validade: "+ produto.getDataValidade().toString() + "</td>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umProduto);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Jogo> jogos;
		if (orderBy == FORM_ORDERBY_ID) {                 	jogos = jogosDao.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		jogos = jogosDao.getOrderByType();
		} else {			                                jogos = jogosDao.getOrderByPreco();}
		

		int i = 0;
		String bgcolor = "";
		for (Jogo p : jogos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + p.getID() + "', '" + p.getNome() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);	
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		float preco = Float.parseFloat(request.queryParams("preco"));
		String tipo = request.queryParams(request.queryParams("tipo"));
		int ano = Integer.parseInt(request.queryParams("preco"));
		
		
		String resp = "";
		
		Jogo jogo = new Jogo(-1, nome, tipo, preco, ano);
		
		if(jogosDao.insert(jogo) == true) {
            resp = "Produto (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogosDao.get(id);
		
		if (jogo != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, jogo, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Jogo " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Jogo jogo = (Jogo) jogosDao.get(id);
		
		if (jogo != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, jogo, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Jogo jogo = jogosDao.get(id);
        String resp = "";       

        if (jogo != null) {
        	jogo.setNome(request.queryParams("Nome"));
        	jogo.setPreco(Float.parseFloat(request.queryParams("preco")));
        	jogo.setTipo(request.queryParams("Tipo"));
        	jogo.setAno(Integer.parseInt(request.queryParams("Ano")));
        	
        	jogosDao.update(jogo);
        	response.status(200); // success
            resp = "Produto (ID " + jogo.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (ID \" + produto.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogosDao.get(id);
        String resp = "";       

        if (jogo != null) {
            jogosDao.delete(id);
            response.status(200); // success
            resp = "Produto (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Produto (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
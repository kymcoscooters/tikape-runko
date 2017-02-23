package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.OpiskelijaDao;
import java.util.*;
import java.*;
import org.thymeleaf.*;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:nachofoorumi.db");
        database.init();
        
        OpiskelijaDao opiskelijaDao = new OpiskelijaDao(database);
        AlueDao alueDao = new AlueDao(database);

        get("/", (req, res) -> {
            res.redirect("/alueet");
            return "ok";
        });

        get("/opiskelijat", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelijat", opiskelijaDao.findAll());

            return new ModelAndView(map, "opiskelijat");
        }, new ThymeleafTemplateEngine());

        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
        }, new ThymeleafTemplateEngine());
        
        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            
            return new ModelAndView(map, "uusiindex");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet", (req, res) -> {
            alueDao.lisaaAlue(req.queryParams("alue"), req.queryParams("ketju"), req.queryParams("lahettaja"), req.queryParams("viesti"));
            res.redirect("/alueet/alue/ketju");
            return "ok"; 
        });
        
        get("/alueet/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            
            
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
    }
}

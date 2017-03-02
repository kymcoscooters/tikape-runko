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

        
        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAll());
            
            return new ModelAndView(map, "uusiindex");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet", (req, res) -> {
            alueDao.lisaaAlue(req.queryParams("alue"), req.queryParams("ketju"), req.queryParams("lahettaja"), req.queryParams("viesti"));
            res.redirect("/alueet/" + req.queryParams("alue") + "/" + req.queryParams("ketju"));
            return "ok"; 
        });
        
        get("/alueet/:alue", (req, res) -> {
            HashMap map = new HashMap<>();
            System.out.println(alueDao.haeAlue(req.params(":alue")).getNimi());
            map.put("alue", alueDao.haeAlue(req.params(":alue")));
            map.put("ketjut", alueDao.haeKetjut(req.params(":alue")));
            
            return new ModelAndView(map, "alue");
        }, new ThymeleafTemplateEngine());
        
        post("/alueet/:alue", (req, res) -> {
            alueDao.lisaaViestiketju(req.queryParams("ketju"), alueDao.haeAlueid(req.params(":alue")), req.queryParams("lahettaja"), req.queryParams("viesti"));
            res.redirect("/alueet/:alue/" + req.queryParams("ketju"));
            return "ok";
        });
        
        get("/alueet/:alue/:ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", alueDao.haeViestit(req.params(":ketju")));
            map.put("ketju", alueDao.haeKetjuPalauttaaKetjun(req.params(":ketju")));
                    
            return new ModelAndView(map, "ketju");
        }, new ThymeleafTemplateEngine());
    }
}

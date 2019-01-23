package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bhaslop
 */
public class HomeController {
    public static ModelAndView get(Request request, Response response) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.queryParams("name"));

        return new ModelAndView(params, "home");
    }
}

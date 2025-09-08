package com.example.Invoice_Backendd.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {

    @RequestMapping(value = { "/", "/{path:^(?!api$).*$}/**" })
    public String forward() {
        // Forward to index.html so React Router can handle the route
        return "forward:/index.html";
    }
}

package tfip.ssf.assessment2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tfip.ssf.assessment2.Model.Address;
import tfip.ssf.assessment2.Model.Cart;
import tfip.ssf.assessment2.Model.Item;

@Controller
@RequestMapping
public class PurchaseOrderController {
    
    @GetMapping(path={"/", "/index.html"})
    public String getIndex(Model model, HttpSession session) {
        session.invalidate();
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart) {
            //If cart is null, then start new session
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("item", new Item());
        return "index";
    }

    @PostMapping(path={"/", "/index.html"})
    public String postItem(@Valid Item item, BindingResult result, Model model, HttpSession session) {
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart) {
            //If cart is null, then start new session
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
        if (result.hasErrors()){
            model.addAttribute("cart", cart);
            return "index";
        }
        cart.addItem(item);
        model.addAttribute("cart", cart);
        return "index";
    }

    @PostMapping(path="/shippingaddress")
    public String postCart(Model model, HttpSession session) {
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart) {
            model.addAttribute("cart", cart);
            return "index";
        }
        model.addAttribute("address", new Address());
        return "address";
    }

}

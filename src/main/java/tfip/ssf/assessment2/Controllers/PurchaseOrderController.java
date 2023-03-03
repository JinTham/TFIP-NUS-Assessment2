package tfip.ssf.assessment2.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tfip.ssf.assessment2.Model.Address;
import tfip.ssf.assessment2.Model.Cart;
import tfip.ssf.assessment2.Model.Item;
import tfip.ssf.assessment2.Model.Quotation;
import tfip.ssf.assessment2.Services.CartService;
import tfip.ssf.assessment2.Services.QuotationService;

@Controller
@RequestMapping
public class PurchaseOrderController {
    
    @Autowired
    private CartService cartSvc;

    @Autowired
    private QuotationService quoteSvc;

    @GetMapping(path={"/", "/index.html"})
    public String getIndex(Model model, HttpSession session) {
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart) {
            //If cart is null, then start new session
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
        model.addAttribute("cart", cart);
        model.addAttribute("item", new Item());
        return "view1";
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
            return "view1";
        }
        List<ObjectError> errors = cartSvc.validateItem(item);
		if (!errors.isEmpty()) {
			for (ObjectError err: errors)
				result.addError(err);
            model.addAttribute("cart", cart);
			return "view1";
		}
        cart.addItem(item);
        model.addAttribute("cart", cart);
        return "view1";
    }

    @GetMapping(path="/shippingaddress")
    public String postCart(Model model, HttpSession session) {
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart || cart.getCartList().size()==0) {
            model.addAttribute("cart", cart);
            model.addAttribute("item", new Item());
            return "view1";
        }
        model.addAttribute("address", new Address());
        return "view2";
    }

    @PostMapping(path="/invoice")
    public String postQuotation(@Valid Address address, BindingResult result, Model model, HttpSession session) throws Exception {
        Cart cart = (Cart)session.getAttribute("cart");
        if (null == cart) {
            model.addAttribute("cart", cart);
            return "view1";
        }
        if (result.hasErrors()){
            return "address";
        }
        Quotation quotation = quoteSvc.getQuotations(cart.getItemNames());
        //Float price = quoteSvc.calculatePrice(quotation, cart.getCartList());
        String price = String.format("%.02f",quoteSvc.calculatePrice(quotation, cart.getCartList()));
        //price = String.format("%.02f", price);
        model.addAttribute("address", address);
        model.addAttribute("quotation", quotation);
        model.addAttribute("price", price);
        session.invalidate();
        return "view3";
    }

}

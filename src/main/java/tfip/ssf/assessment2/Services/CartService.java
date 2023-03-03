package tfip.ssf.assessment2.Services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import tfip.ssf.assessment2.Model.Item;

@Service
public class CartService {
    
    public List<ObjectError> validateItem(Item item) {
		List<ObjectError> errors = new LinkedList<>();
		FieldError error;
        String[] ITEM_NAMES = {"apple","orange","bread","cheese","chicken","mineral_water","instant_noodles"};
        Set<String> itemNames = new HashSet<>(Arrays.asList(ITEM_NAMES));
		if (!itemNames.contains(item.getItemName().toLowerCase())) {
			error = new FieldError("item", "itemName"
					, "We do not stock %s".formatted(item.getItemName()));
			errors.add(error);
		}
		return errors;
	}
}

package tfip.ssf.assessment2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tfip.ssf.assessment2.Model.Quotation;
import tfip.ssf.assessment2.Services.QuotationService;

@SpringBootApplication
public class Assessment2Application implements CommandLineRunner{

	@Autowired
	private QuotationService quoteSvc;

	public static void main(String[] args) {
		SpringApplication.run(Assessment2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String[] ITEMS= {"apple","orange","bread","cheese","instant_noodles","chicken","mineral_water"};
        List<String> items = new LinkedList<>(Arrays.asList(ITEMS));
		Quotation quotation = quoteSvc.getQuotations(items);
	}

}

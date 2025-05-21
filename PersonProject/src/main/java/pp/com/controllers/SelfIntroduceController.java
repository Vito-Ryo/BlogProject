package pp.com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelfIntroduceController {

	@GetMapping("/self-introduction")
	public String showSelfIntroductionPage(Model model) {
		return "self-introduction";
	}
}

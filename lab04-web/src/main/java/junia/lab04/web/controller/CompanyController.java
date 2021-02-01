package junia.lab04.web.controller;

import junia.lab04.core.entity.Company;
import junia.lab04.core.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CompanyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);


    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        LOGGER.debug("Initialisation de {}",this.getClass().getSimpleName());
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String getListOfCompanies(ModelMap model) {
        LOGGER.info("Affichage de la liste des companies");
        model.put("companies", companyService.findAllWithProjects());
        return "companiesList";
    }

    @GetMapping("/form")
    public String getForm(ModelMap model){
        LOGGER.info("Affichage du formulaire");
        model.addAttribute("company", new Company());
        return "companyForm";
    }

    @PostMapping("/form")
    public String submitForm(@ModelAttribute("company") Company company){
        LOGGER.info("Enregistrement d'une companie saisie dans le formulaire");
        companyService.save(company);
        return "redirect:list";
    }

    @GetMapping("/{id:\\d+}/delete")
    public String deleteCompany(@PathVariable("id")long id){
        LOGGER.info("Suppression de la company dont l'identifiant est : {}",id);
        companyService.deleteById(id);
        return "redirect:../list";
    }
}

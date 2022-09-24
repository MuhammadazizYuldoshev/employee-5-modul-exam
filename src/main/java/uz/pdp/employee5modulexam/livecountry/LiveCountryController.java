package uz.pdp.employee5modulexam.livecountry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/country")
@RequiredArgsConstructor
public class LiveCountryController {

    private final LiveCountryDao countryDao;

    @GetMapping("/{pageStr}")
    public String showCountry(@PathVariable String pageStr, Model model){

        int size = 2;
        int page = 1;

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        List<LiveCountry> allCountries = countryDao.getAllCountry(size,page);
        model.addAttribute("countries",allCountries);
        model.addAttribute("totalElementsCount",countryDao.getCountOfCountries());
        model.addAttribute("size",size);
        model.addAttribute("currentPage",page);
        return "view-country-form";


    }


    @GetMapping("/add-country-form")
    public String addCountry(){

        return "add-country-form";
    }

    @PostMapping
    public String saveCountry(LiveCountry country){
        countryDao.saveCountry(country);


        return "redirect:/country/1";

    }


    @GetMapping("edit/{id}")
    public String updateCountry(@PathVariable("id") int id,Model model){

        LiveCountry country = countryDao.getCountry(id);
        model.addAttribute("country",country);
        return "update-country-form";

    }


    @RequestMapping(value = "/update-country",method = RequestMethod.POST)
    public String update(LiveCountry country){
        countryDao.updateCountry(country);
        return "redirect:/country/1";

    }


    @GetMapping("/delete/{id}")
    public String deleteCountry(@PathVariable("id") int id){
        countryDao.delete(id);
        return "redirect:/country/1";
    }





}

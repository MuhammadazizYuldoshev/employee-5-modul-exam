package uz.pdp.employee5modulexam.position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final PositionDao positionDao;

    @GetMapping("/{pageStr}")
    public String showPosition(@PathVariable String pageStr, Model model){

        int size = 2;
        int page = 1;

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }

        List<Position> allPositions = positionDao.getAllPositions(size,page);
        System.out.println("allPositions = " + allPositions);
        model.addAttribute("positions",allPositions);
        model.addAttribute("totalElementsCount",positionDao.getCountOfPositions());
        model.addAttribute("size",size);
        model.addAttribute("currentPage",page);
        return "view-position-form";

    }


    @GetMapping("/add-position-form")
    public String addPosition(){

        return "add-position-form";
    }

    @PostMapping
    public String savePosition(Position position){
        positionDao.savePosition(position);


        return "redirect:/position/1";

    }


    @GetMapping("edit/{id}")
    public String updateAuthors(@PathVariable("id") int id,Model model){

        Position position = positionDao.getPosition(id);
        model.addAttribute("position",position);
        return "update-position-form";

    }


    @RequestMapping(value = "/update-position",method = RequestMethod.POST)
    public String update(Position position){
        positionDao.updatePosition(position);
        return "redirect:/position/1";

    }


    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable("id") int id){
        positionDao.delete(id);
        return "redirect:/position/1";
    }





}

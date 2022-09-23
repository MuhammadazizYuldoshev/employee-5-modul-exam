package uz.pdp.employee5modulexam.employee;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.employee5modulexam.position.Position;
import uz.pdp.employee5modulexam.position.PositionDao;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeDao employeeDao;
    private final PositionDao positionDao;

    @GetMapping("/{pageStr}")
    public String viewProduct(@PathVariable String pageStr, Model model){
        int size = 2;
        int page = 1;

        if (pageStr != null){
            page = Integer.parseInt(pageStr);
        }

        List<Employee> allEmployees = employeeDao.getAllEmployees(size, page);
        int countOfEmployees = employeeDao.getCountOfEmployees();
        model.addAttribute("employees",allEmployees);
        model.addAttribute("count",countOfEmployees);
        model.addAttribute("size",size);
        model.addAttribute("page",page);
        return "view-employee-form";
    }

    @GetMapping("/add-form")
    public String addEmployeeForm(Model model){
        model.addAttribute("positionList",positionDao.getAllPositionsForSelect());
        return "add-employee-form";
    }

    @PostMapping
    public String saveEmployee(Employee employee){
        employeeDao.saveEmployee(employee);
        return "redirect:/employees/1";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable int id,Model model){
        Employee employee = employeeDao.getEmployeeById(id);
        System.out.println(employee);
        List<Position> allPositionsForSelect = positionDao.getAllPositionsForSelect();
        model.addAttribute("employee",employee);
        model.addAttribute("position",allPositionsForSelect);
        return "update-employee-form";
    }

    @PostMapping("/update-form")
    public String updateEmployee(Employee employee){
        System.out.println(employee);
        employeeDao.updateEmployee(employee);
        return "redirect:/employees/1";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id){
        employeeDao.delete(id);
        return "redirect:/employees/1";
    }



}

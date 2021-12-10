package com.ipiecoles.java.java320.controller;

import com.ipiecoles.java.java320.model.Employe;
import com.ipiecoles.java.java320.repository.EmployeRepository;
import com.ipiecoles.java.java320.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String detail(final ModelMap model, @PathVariable(value = "id") Long id) {
        model.put("employe", employeService.findById(id));
        return "detail";
    }

    @RequestMapping(method = RequestMethod.GET, value = "", params = "matricule")
    public String findByMatricule(final ModelMap model, @RequestParam(value = "matricule") String matricule) {
        model.put("employe", employeService.findMyMatricule(matricule));
        return "detail";
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value = "")
    public String findEmployeesPagined(final ModelMap model,
                                       @RequestParam Integer page,
                                       @RequestParam Integer size,
                                       @RequestParam String sortProperty,
                                       @RequestParam String sortDirection) {
        model.put("employes", employeService.findAllEmployes(page, size, sortProperty, sortDirection));
        model.put("start", page * size + 1);
        model.put("end", page * size + size);
        model.put("next", page + 1);
        model.put("previous", page - 1);
        return "list";
    }
}

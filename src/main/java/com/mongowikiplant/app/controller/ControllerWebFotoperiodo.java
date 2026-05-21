package com.mongowikiplant.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mongowikiplant.app.entity.Fotoperiodo;
import com.mongowikiplant.app.exception.NotFoundException;
import com.mongowikiplant.app.repository.EstacionRepository;
import com.mongowikiplant.app.repository.FotoperiodoRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/fotoperiodo")
public class ControllerWebFotoperiodo {

    @Autowired
    private FotoperiodoRepository fotoperiodoRepository;

    @Autowired
    private EstacionRepository estacionRepository;

    @GetMapping("/crear")
    public String fotoperiodosNewTemplate(Model model) {
        model.addAttribute("fotoperiodo", new Fotoperiodo());
        model.addAttribute("listaEstaciones", estacionRepository.findAll());
        return "fotoperiodo-form";
    }

    @GetMapping("/lista")
    public String fotoperiodosListTemplate(Model model) {
        // Ordenar por el campo 'fecha' de manera ascendente
        model.addAttribute("fotoperiodos", fotoperiodoRepository.findAll(Sort.by(Sort.Order.asc("fecha"))));
        return "fotoperiodo-lista";
    }

    @GetMapping("/edit/{id}")
    public String fotoperiodoEditTemplate(@PathVariable("id") String id, Model model) {
        Fotoperiodo fotoperiodo = fotoperiodoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fotoperiodo no encontrado"));
        model.addAttribute("fotoperiodo", fotoperiodo);
        model.addAttribute("listaEstaciones", estacionRepository.findAll());
        return "fotoperiodo-form";
    }

    @PostMapping("/save")
    public String fotoperiodosSaveProcess(@Valid @ModelAttribute("fotoperiodo") Fotoperiodo fotoperiodo, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            // Si hay errores en los datos del formulario, se vuelve a mostrar el formulario
            model.addAttribute("listaEstaciones", estacionRepository.findAll());
            return "fotoperiodo-form";
        }
        if (fotoperiodo.getId() == null || fotoperiodo.getId().isEmpty()) {
        	fotoperiodo.setId(null); // MongoDB generará un ID automáticamente
        }

        // Si no hay errores, se guarda el fotoperiodo
        fotoperiodoRepository.save(fotoperiodo);

        // Redirigir al usuario a la lista de fotoperiodos después de guardar los datos
        return "redirect:/fotoperiodo/lista";
    }
    
    @GetMapping("/delete/{id}")
    public String fotoperiodoDeleteProcess(@PathVariable("id") String id) {
    	fotoperiodoRepository.deleteById(id);
        return "redirect:/fotoperiodo/lista";
    }

    
    @GetMapping("/info")
    public String fotoperiodoInfoTemplate() {
        return "fotoperiodo-info";
    }

}

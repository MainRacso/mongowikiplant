package com.mongowikiplant.app.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongowikiplant.app.entity.Fotoperiodo;
import com.mongowikiplant.app.repository.FotoperiodoRepository;

@Service
	public class FotoperiodoService {

	    @Autowired
	    private FotoperiodoRepository fotoperiodoRepository;

	    // Calcula la sumatoria de todos los valores de enero para un rango de años
	    public Double calcularSumatoriaEnero(int yearStart, int yearEnd) {
	        List<Fotoperiodo> fotoperiodos = fotoperiodoRepository.findByFechaBetween(yearStart, yearEnd);

	        return fotoperiodos.stream()
	                .mapToDouble(Fotoperiodo::getEnero)  // Extrae el valor de enero
	                .filter(Objects::nonNull)  // Filtra los valores nulos
	                .sum();  // Suma los valores de enero
	    }
	    
	    public Double calcularSumatoriaMes(int mes, int yearStart, int yearEnd) {
	        List<Fotoperiodo> fotoperiodos = fotoperiodoRepository.findByFechaBetween(yearStart, yearEnd);

	        return fotoperiodos.stream()
	                .mapToDouble(fotoperiodo -> obtenerValorPorMes(fotoperiodo, mes))
	                .filter(Objects::nonNull)
	                .sum();
	    }

	    private Double obtenerValorPorMes(Fotoperiodo fotoperiodo, int mes) {
	        switch (mes) {
	            case 1: return fotoperiodo.getEnero();
	            case 2: return fotoperiodo.getFebrero();
	            case 3: return fotoperiodo.getMarzo();
	            case 4: return fotoperiodo.getAbril();
	            case 5: return fotoperiodo.getMayo();
	            case 6: return fotoperiodo.getJunio();
	            case 7: return fotoperiodo.getJulio();
	            case 8: return fotoperiodo.getAgosto();
	            case 9: return fotoperiodo.getSeptiembre();
	            case 10: return fotoperiodo.getOctubre();
	            case 11: return fotoperiodo.getNoviembre();
	            case 12: return fotoperiodo.getDiciembre();
	            default: return null;
	        }
	    }

	}

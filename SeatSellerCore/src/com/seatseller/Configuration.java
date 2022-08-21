package com.seatseller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.seatseller.cartoesdecredito.ISistemaDeCartoesDeCreditoAdapter;
import com.seatseller.core.lugares.alocacao.IEncontrarLugarStrategy;

/*
 * Configuration gathers all possible configuration variables defined in the config.properties files.
 * 
 *  Configuration follows the singleton pattern.
 */
public class Configuration {
	/*Permite aceder ao ficheiro config.properties e obter as
	 * propriedades la definidas
	 */
	private Properties props = new Properties();
	
	private static Configuration INSTANCE;
	
	/**
	 * Cria uma nova instancia de configuration apenas se esta
	 * nao existir ainda caso contrario retorna a existente
	 * @return A unica instancia de Configuration
	 */
	public static Configuration getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Configuration();
		}
		return INSTANCE;
	}
	
	/*
	 * Tenta fazer load do ficheiro que contem as properties 
	 */
	private Configuration () {
		try {
			props.load(new FileInputStream("config.properties.txt"));
		} catch (IOException e) {
			/*Ter properties apenas com os valores default ou lancar
			outra excepcao?*/
		}
	}

	
	/**
	 * Lê do ficheiro config.properties qual o adapter a usar
	 * e cria uma instancia do mesmo
	 * @return A instancia do adapter criado
	 */
	@SuppressWarnings("unchecked")
	public ISistemaDeCartoesDeCreditoAdapter getSCCAdapter() {
		String className = getString("cartaoAdapter");
		Class<ISistemaDeCartoesDeCreditoAdapter> classType;
		
		try {
			/*
			 * ClassType contem a classe do Adapter que se quer usar, 
			 * procedendo a criar uma instancia desta e retornando-a
			 */
			classType = (Class<ISistemaDeCartoesDeCreditoAdapter>) Class.forName(className);
			
			return classType.newInstance();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			//Saber o que retornar aqui e se é preciso por multiplos catch
			return null;
		}
	}
	
	private String getString (String key) {
		return props.getProperty(key);
	}
	
	public boolean cativarDuranteReservas() {
		String cativarValue = getString("cativar");
		return Boolean.getBoolean(cativarValue);
	}
	
	public double valorDuranteReservas() {
		String retirarValue = getString("retirar");
		return Double.parseDouble(retirarValue);
	}
	
	/**
	 * Le do ficheiro config.properties qual a estrategia
	 * a ser usada para encontrar um lugar e cria uma instancia
	 * da mesma
	 * @return A instancia da estrategia encontrada
	 */
	@SuppressWarnings("unchecked")
	public IEncontrarLugarStrategy getLugarStrategy () {
		String className = getString("encontrarLugarStrategy");
		Class<IEncontrarLugarStrategy> classType;
		
		try {
			/*
			 * ClassType contem a classe da strategy que se quer usar, 
			 * procedendo a criar uma instancia desta e retornando-a
			 */
			classType = (Class<IEncontrarLugarStrategy>) Class.forName(className);
			
			return classType.newInstance();
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			//Saber o que retornar aqui e se sao preciso por multiplos catch
			return null;
		}
	}
}

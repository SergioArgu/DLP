package symboltable;

import java.util.*;
import ast.Definition;

public class SymbolTable {
	
	private int scope=0;//ámbito (0 global, 1 local)
	//Los parámetros y variables locales tienen scope 1
	private List<Map<String,Definition>> table;//tabla de símbolos
	public SymbolTable()  {
		table = new ArrayList<>();
		table.add(new HashMap<>());
	}

	public void set() {//Entro en nuevo ámbito
		table.add(new HashMap<>());
		scope++;
	}
	
	public void reset() {//Salgo del ámbito
		table.remove(table.getLast());
		scope--;
	}

	//True si no existe ya, sino false
	public boolean insert(Definition definition) {
		boolean current = findInCurrentScope(definition.getName());
		if (!current){
			definition.setScope(scope);
			table.get(scope).put(definition.getName(), definition);
			return true;
		} else {
			return false;
		}
	}

	//Devuelve si existe
	public Definition find(String id) {//Desde el ambito actual hasta el global
		for (int i = scope; i >= 0; i--) {
			if (table.get(i).containsKey(id)) {
				return table.get(i).get(id);
			}
		}
		return null;
	}

	//package-protected for testing pourposes
	boolean findInCurrentScope(String id) {
		Map<String, Definition> simbolo = table.get(scope);
		if (simbolo.containsKey(id)){
			return true;
		}
		return false;
	}
}

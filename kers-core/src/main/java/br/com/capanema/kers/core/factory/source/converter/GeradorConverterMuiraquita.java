package br.com.capanema.kers.core.factory.source.converter;

import java.io.IOException;

import br.com.capanema.kers.common.model.domain.Attribute;
import br.com.capanema.kers.common.model.domain.Entity;
import br.com.capanema.kers.common.model.projectea.EAProjectConfig;
import br.com.capanema.kers.core.factory.CalangoFactory;
import br.com.capanema.kers.core.templates.CopyrightTpl;
import br.com.capanema.kers.core.util.file.FileManager;

public class GeradorConverterMuiraquita  extends CalangoFactory {

    public GeradorConverterMuiraquita() {

    }

    public static void build(EAProjectConfig projeto) throws IOException {

	StringBuffer srtBuffer;
	String caminho = projeto.getDestinoSource() + projeto.getPackageTree().replace(".", "/") + "converter";

	for (Entity entidade : projeto.getEntidades()) {

	    srtBuffer = new StringBuffer();
	    String nomeClasse = entidade.getNomeEntidade() + "Converter";
	    if (!entidade.getIsEnum()) {
        	    System.out.println(entidade.getNomeEntidade() + ".......");
        
        	    srtBuffer.append(CopyrightTpl.getConteudo());
        	    srtBuffer.append("package " + projeto.getPackageTree() + "converter; \n\n");
        
        	    srtBuffer.append("import " + projeto.getPackageTree() + "entity." + entidade.getNomeEntidade() + "; \n");
        	    srtBuffer.append("import " + projeto.getPackageTree() + "dto." + entidade.getNomeDto() + "; \n");
        	    srtBuffer.append("import java.util.List; \n");
        	    srtBuffer.append("import java.util.ArrayList; \n");
        	    srtBuffer.append("import "+ projeto.getPackageTree() +"entity."+ entidade.getNomeEntidade() +"; \n");
        	    srtBuffer.append("import "+ projeto.getPackageTree() +"dto."+ entidade.getNomeDto() +"; \n\n");
        	    
        	    
        	    srtBuffer.append("/** Converter para " + entidade.getNomeEntidade() + " **/");
        	    srtBuffer.append("\n");
        
        	    srtBuffer.append("public final class " + nomeClasse + " {");
        
        	    // Metodo getEntidade
        
        	    geradorGetEntidade(entidade, srtBuffer);
        	    geradorGetEntidadeDto(entidade, srtBuffer);
        	    geradorGetEntidadePersistido(entidade, srtBuffer);
        	    geradorGetListaEntidades(entidade, srtBuffer);
        	    geradorGetListaEntidadesDto(entidade, srtBuffer);
        
        	    System.out.println("");
        	    System.out.println("******************Converter para " + entidade.getNomeEntidade() + " criado. \n \n");
        	    srtBuffer.append("\n");
        	    srtBuffer.append("}");
        
        	    // gerarDto(objeto, caminhoArquivos);
        	    // gerarClasseConverter(nomeClasse, caminho,
        	    // srtConverter.toString());
        	    FileManager.createFife(nomeClasse + ".java", caminho, srtBuffer.toString());
	    }

	}
	// return srtBuffer.toString();

    }

    private static void geradorGetEntidade(Entity entidade, StringBuffer srtConverter) {

	// Contatenacao das strings

	// Metodo getEntidade
	System.out.println("gerando o metodo get" + entidade.getNomeEntidade());

	srtConverter.append("\n \n");

	srtConverter.append("// Metodo get" + entidade.getNomeEntidade());
	srtConverter.append("\n");
	srtConverter.append("public static " + entidade.getNomeEntidade() + " get" + entidade.getNomeEntidade() + "(" + entidade.getNomeDto()
		+ " dto) { \n");
	srtConverter.append("\t " + entidade.getNomeEntidade() + " obj = new " + entidade.getNomeEntidade() + "(); \n");

	for (Attribute atributo : entidade.getAtributos()) {
	    if (!atributo.getNome().equals("id")) { // TODO Melhorar. Nem sempre
						    // o nome do identificador �
						    // id!
		srtConverter.append("\t obj." + atributo.getSetMetodo() + "(dto." + atributo.getGetMetodo() + "()); \n");
	    }
	}

	srtConverter.append("return obj; \n");
	srtConverter.append("} \n\n");

    }

    private static void geradorGetEntidadeDto(Entity entidade, StringBuffer srtConverter) {

	// Contatenacao das strings

	// Metodo getEntidade
	System.out.println("gerando o metodo get" + entidade.getNomeDto());

	srtConverter.append("// Metodo get" + entidade.getNomeDto());
	srtConverter.append("\n");
	srtConverter.append("public static " + entidade.getNomeDto() + " get" + entidade.getNomeDto() + "(" + entidade.getNomeEntidade() + " obj) {");
	srtConverter.append("\n");
	srtConverter.append("\t " + entidade.getNomeDto() + " dto = new " + entidade.getNomeDto() + "();");
	srtConverter.append("\n");

	for (Attribute atributo : entidade.getAtributos()) {
	    srtConverter.append("\t dto." + atributo.getSetMetodo() + "(obj." + atributo.getGetMetodo() + "()); \n");
	}

	srtConverter.append("return dto;");

	srtConverter.append("\n");
	srtConverter.append("}");
	srtConverter.append("\n \n");

    }

    private static void geradorGetEntidadePersistido(Entity entidade, StringBuffer srtConverter) {

	System.out.println("gerando o metodo get" + entidade.getNomeEntidade() + "Persistido");

	srtConverter.append("// Metodo get" + entidade.getNomeEntidade() + "Persistido \n");
	srtConverter.append("public static " + entidade.getNomeEntidade() + " get" + entidade.getNomeEntidade() + "Persistido ("
		+ entidade.getNomeEntidade() + " obj, " + entidade.getNomeDto() + " dto) { \n");

	int next = 1;
	for (Attribute atributo : entidade.getAtributos()) {
	    srtConverter.append("\t obj." + atributo.getSetMetodo() + "(dto." + atributo.getGetMetodo() + "()); \n");
	    next++;
	}
	srtConverter.append("return obj; \n");

	srtConverter.append("} \n \n");

    }

    private static void geradorGetListaEntidades(Entity entidade, StringBuffer srtConverter) {

	System.out.println("gerando o metodo get" + entidade.getNomeEntidade() + "Lista");
	srtConverter.append("// Metodo get" + entidade.getNomeEntidade() + "Lista");
	srtConverter.append("\n");
	srtConverter.append("public static List<" + entidade.getNomeEntidade() + "> get" + entidade.getNomeEntidade() + "Lista (List<"
		+ entidade.getNomeDto() + "> listaDto) {");
	srtConverter.append("\n");
	srtConverter.append("\t List<" + entidade.getNomeEntidade() + "> lista = new ArrayList<" + entidade.getNomeEntidade() + ">();");
	srtConverter.append("\n");
	srtConverter.append("\t for (" + entidade.getNomeDto() + " " + entidade.getNomeEntidade().toLowerCase() + "Dto" + ": listaDto) {");
	srtConverter.append("\n");
	srtConverter.append("\t \t lista.add(get" + entidade.getNomeEntidade() + "(" + entidade.getNomeEntidade().toLowerCase() + "Dto));");
	srtConverter.append("\n");
	srtConverter.append("\t }");
	srtConverter.append("\n return lista;");
	srtConverter.append("\n }");
	srtConverter.append("\n \n");

    }

    private static void geradorGetListaEntidadesDto(Entity entidade, StringBuffer srtConverter) {

	System.out.println("gerando o metodo get" + entidade.getNomeEntidade() + "ListaDto");
	srtConverter.append("// Metodo get" + entidade.getNomeEntidade() + "ListaDto");
	srtConverter.append("\n");
	srtConverter.append("public static List<" + entidade.getNomeDto() + "> get" + entidade.getNomeDto() + "Lista (List<"
		+ entidade.getNomeEntidade() + "> lista ) {");
	srtConverter.append("\n");
	srtConverter.append("\t List<" + entidade.getNomeDto() + "> listaDto = new ArrayList<" + entidade.getNomeDto() + ">();");
	srtConverter.append("\n");
	srtConverter.append("\t for (" + entidade.getNomeEntidade() + " " + entidade.getNomeEntidade().toLowerCase() + ": lista) {");
	srtConverter.append("\n");
	srtConverter.append("\t \t listaDto.add(get" + entidade.getNomeDto() + "(" + entidade.getNomeEntidade().toLowerCase() + "));");
	srtConverter.append("\n");
	srtConverter.append(" \t }");
	srtConverter.append("\n return listaDto;");
	srtConverter.append("\n }");
	srtConverter.append("\n \n");

    }

    private static void gerarClasseConverter(String nome, String caminho, String conteudo) throws IOException {
	// FileManager.gerarDiretorio(caminho);
	FileManager.createFife(nome, caminho, conteudo);
    }

}
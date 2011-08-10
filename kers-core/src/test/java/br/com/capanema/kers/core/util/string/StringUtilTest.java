package br.com.capanema.kers.core.util.string;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.capanema.kers.common.util.string.StringUtil;

public class StringUtilTest {

	String strAllApper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String strMix = "AbBcDeFGHIJKLMNOPQRSTUVWXYZ";
	String strMixSpace = "AB CD EFG hIJKLM_NOP _QRS_tUVWXYZ";
	String outros = "ção";
	String nomeComposto = "PãoDeBalançar";
	String nomeComposto2 = "AreaConhecimento";
	
	//@Test
	public void testToVariavelFormat() {
		fail("Not yet implemented");
	}

	//@Test
	public void testToClasseNameFormat() {
		fail("Not yet implemented");
	}

	@Test
	public void testToTableSintaxFormt() {
		
		//System.out.println(strAllApper + "\n" + StringUtil.toTableSintaxFormt(strAllApper));
		
		//System.out.println(strAllApper.toLowerCase() + "\n" + StringUtil.toTableSintaxFormt(strAllApper.toLowerCase()));
		
		//System.out.println(strMix + "\n" + StringUtil.toTableSintaxFormt(strMix));
		//
		//System.out.println(strMixSpace + "\n " + StringUtil.toTableSintaxFormt(strMixSpace));
		
		//System.out.println(outros + "\n" + StringUtil.toTableSintaxFormt(outros));
		
		//System.out.println(nomeComposto +  " " + StringUtil.toLabelSintaxFormt(nomeComposto));
		//System.out.println(nomeComposto2 + "  " + StringUtil.toLabelSintaxFormt(nomeComposto2));
	  
	  System.out.println(outros + "\n" + StringUtil.toVariavelFormat("CPF"));
	  
	  System.out.println(outros + "\n" + StringUtil.toVariavelFormat(nomeComposto2));

	}

}

package br.com.capanema.kers.velocity.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class VelocityStringUtilTest {

  @Test
  public void testCapitalizeFirstLetterString() {

    System.out.println(VelocityStringUtil.capitalizeFirstLetter("teste"));
    System.out.println(VelocityStringUtil.capitalizeFirstLetter("Teste"));
    System.out.println(VelocityStringUtil.capitalizeFirstLetter("TesTe"));
    System.out.println();
  }

  @Test
  public void testUnCapitalizeFirstLetter() {
    System.out.println(VelocityStringUtil.unCapitalizeFirstLetter("teste"));
    System.out.println(VelocityStringUtil.unCapitalizeFirstLetter("Teste"));
    System.out.println(VelocityStringUtil.unCapitalizeFirstLetter("TesTe"));
    System.out.println();
  }

  @Test
  public void testNormalizeClassName() {
    System.out.println(VelocityStringUtil.normalizeClassName("teste"));
    System.out.println(VelocityStringUtil.normalizeClassName("Teste"));
    System.out.println(VelocityStringUtil.normalizeClassName("Testão"));
    System.out.println();
  }

  @Test
  public void testIndexOf() {
    fail("Not yet implemented");
  }

  @Test
  public void testDelimiterParameters() {
    System.out.println(VelocityStringUtil.delimiterParameters(0, 0));
  }

}

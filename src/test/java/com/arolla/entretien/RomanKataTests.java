package com.arolla.entretien;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

public class RomanKataTests {

  private static final java.util.regex.Pattern PATTERN = Pattern.compile("^[IXLCMV]+$");

  @Test
  void should_return_1_when_I() throws Exception {
    assertThat(ArabicToRomanNumber("I")).isEqualTo(1);
  }

  @Test
  void should_return_2_when_II() throws Exception {
    assertThat(ArabicToRomanNumber("II")).isEqualTo(2);
  }

  @Test
  void should_return_3_when_III() throws Exception {
    assertThat(ArabicToRomanNumber("III")).isEqualTo(3);
  }

  @Test
  void should_return_4_when_IV() throws Exception {
    assertThat(ArabicToRomanNumber("IV")).isEqualTo(4);
  }

  @Test
  void should_return_103_when_CIII() throws Exception {
    assertThat(ArabicToRomanNumber("CIII")).isEqualTo(103);
  }

  @Test
  void should_return_2021_when_MMXXI() throws Exception {
    assertThat(ArabicToRomanNumber("MMXXI")).isEqualTo(2021);
  }

  @Test
  void should_return_89_when_LXXXIX() throws Exception {
    assertThat(ArabicToRomanNumber("LXXXIX")).isEqualTo(89);
  }

  @Test
  void should_return_exception_when_IIII() {

    assertThatThrownBy(() -> ArabicToRomanNumber("IIII"))
        .isInstanceOf(Exception.class)
        .hasMessageContaining(
            "la chaine passée en paramètre n'est pas dans un format correct : IIII");
  }

  @Test
  void should_return_exception_when_wrong_format_CMLXXIIVVM() throws Exception {
    assertThatThrownBy(() -> ArabicToRomanNumber("CMLXXIIVVM"))
        .isInstanceOf(Exception.class)
        .hasMessageContaining(
            "la chaine passée en paramètre n'est pas dans un format correct : CMLXXIIVVM");
  }

  @Test
  void should_return_exception_when_wrong_format_CMM() throws Exception {
    assertThatThrownBy(() -> ArabicToRomanNumber("CMM"))
        .isInstanceOf(Exception.class)
        .hasMessageContaining(
            "la chaine passée en paramètre n'est pas dans un format correct : CMM");
  }

  @Test
  void should_return_exception_when_wrong_format_SXIII() throws Exception {
    assertThatThrownBy(() -> ArabicToRomanNumber("SXIII"))
        .isInstanceOf(Exception.class)
        .hasMessageContaining(
            "la chaine passée en paramètre n'est pas dans un format correct : SXIII");
  }

  Map<String, Integer> doubleBraceMap = new LinkedHashMap<String, Integer>() {{
    put("I", 1);
    put("IV", 4);
    put("V", 5);
    put("IX", 9);
    put("X", 10);
    put("XL", 40);
    put("L", 50);
    put("XC", 90);
    put("C", 100);
    put("CD", 400);
    put("D", 500);
    put("CM", 900);
    put("M", 1000);
  }};


  private Set<String> getInvalideString() {
    Set<String> strings = Sets.newHashSet();
    strings.add("IC");
    strings.add("IL");
    strings.add("ID");
    strings.add("IM");
    strings.add("XD");
    strings.add("XM");
    strings.add("XCC");
    strings.add("LC");
    strings.add("LD");
    strings.add("LM");
    strings.add("IIII");
    strings.add("IVIV");
    strings.add("VV");
    strings.add("VC");
    strings.add("VX");
    strings.add("VM");
    strings.add("XXXXX");
    strings.add("XLXL");
    strings.add("XCXC");
    strings.add("CDCD");
    strings.add("CMCM");
    strings.add("CCCCC");
    strings.add("CMM");
    strings.add("LL");
    strings.add("DD");
    strings.add("DM");
    strings.add("MMMMM");
    return strings;
  }

  private Integer ArabicToRomanNumber(String romanI) throws Exception {
    validateFormatRoman(romanI);
    Integer value = 0;
    value = translateRomanToArabic(romanI, value);
    return value;
  }

  private void validateFormatRoman(String romanI) throws Exception {
    String invalideString = getInvalideString()
        .stream()
        .filter(romanI::contains)
        .findFirst()
        .orElse(null);

    Matcher matcher = PATTERN.matcher(romanI);

    if (StringUtils.isNotBlank(invalideString) || !matcher.find()) {
      throw new Exception(String
          .format("la chaine passée en paramètre n'est pas dans un format correct : %s", romanI));
    }

  }

  private Integer translateRomanToArabic(String romanI, Integer value) {
    Set<String> collect = doubleBraceMap
        .keySet()
        .stream()
        .sorted(Comparator.comparing(String::length).reversed())
        .collect(Collectors.toCollection(LinkedHashSet::new));

    for (String s : collect) {
      while (romanI.contains(s)) {
        value += doubleBraceMap.get(s);
        romanI = romanI.replaceFirst(s, "");
      }
    }
    return value;
  }
}

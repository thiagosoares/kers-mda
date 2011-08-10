package br.com.capanema.kers.common.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexFilter extends MatchFilter {

  private final Pattern searchRegex;

  /**
   * The replacement string for identified matches in input strings. Set in the
   * constructor.
   */
  private final String replacementString;

  /**
   * Creates a new RegexFilter using the given regex pattern. Substrings of the
   * input matching the regex in the input string are replaced with the
   * replacementString string. The constructor checks that the pattern does not
   * match the empty string.
   * 
   * @param searchRegex The pattern to be used to find replacements.
   * @param replacementString The string to replace matching replacements with.
   * 
   * @throws NullPointerException if pattern or replaceWith is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   */
  public RegexFilter(Pattern searchRegex, String replacementString) {
    if (searchRegex == null) {
      throw new NullPointerException("searchRegex should not be null.");
    }
    if (replacementString == null) {
      throw new NullPointerException("replacementString should not be null.");
    }
    if (searchRegex.matcher(FilterUtility.EMPTY).matches()) {
      throw new IllegalArgumentException("searchRegex should not match empty string.");
    }

    this.searchRegex = searchRegex;
    this.replacementString = replacementString;
  }

  /**
   * Creates a new RegexFilter with the given string compilied into a regex
   * pattern. Instances matching the regex in the input string are replaced with
   * the replaceWith string. The constructor checks that the regex does not
   * match the empty string.
   * 
   * @param searchRegex A valid regex for detecting matching substrings when
   *          filtering.
   * @param replacementString The string to replace matching replacements with.
   * 
   * @throws NullPointerException if searchRegex or replacementString is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   * @throws PatternSyntaxException if the expression's syntax is invalid.
   */
  public RegexFilter(String searchRegex, String replacementString) {
    this(RegexFilter.compileRegex(searchRegex), replacementString);
  }

  /**
   * Returns the regex that this filter uses for filtering.
   * 
   * @return The regular expression identifying parts of the string to be
   *         replaced.
   */
  public Pattern getSearchRegex() {
    return this.searchRegex;
  }

  /**
   * Returns the replacement used for matches of the regular expression.
   * 
   * @return The replacement used for matches of the regular expression.
   */
  public String getReplacementString() {
    return this.replacementString;
  }

  /**
   * Implements the MatchFilter.getAllReplacements() method.
   * 
   * The returned list should contain a replacement for every substring of the
   * input where searchRegex matches substring. Since the MatchFilter class
   * specifies that the returned list of replacements be made relative to having
   * all of the previous ones in the list applied, this method should keep an
   * offset as it is finding the replacements and correct the indexes of the
   * found substrings by the offset. It should not actually perform the
   * replacement on the input string after each match is found because of the
   * performance cost of creating new strings.
   * 
   * @param input The string to generate all changes necessary for filtering.
   * @return A list of necessary changes to filter the string.
   * @throws NullPointerException Thrown if input is null.
   */
  public List<DefaultFilterReplacement> getAllReplacements(String input) {
    if (input == null) {
      throw new NullPointerException("input should not be null.");
    }

    List<DefaultFilterReplacement> allReplacements =
            new ArrayList<DefaultFilterReplacement>();
    Matcher matcher = this.searchRegex.matcher(input);

    // Keep the last match position to avoid duplicate matching.
    int lastMatch = 0;
    int offset = 0;

    for (;;) {
      // Break off if no more matches available.
      if (!matcher.find(lastMatch)) {
        break;
      }

      allReplacements.add(new DefaultFilterReplacement(this.replacementString, matcher.start()
              + offset, matcher.end() + offset));

      // Advance lastMatch so that we do not match on processed region. Offset
      // is adjusted accordingly.
      lastMatch = matcher.end();
      offset +=
              this.replacementString.length()
                      - (matcher.end() - matcher.start());
    }

    return allReplacements;
  }

  /**
   * Implements the MatchFilter.getFirstReplacement() method.
   * 
   * The indexes of the returned replacement should just be the ends of the
   * first substrign of the input that matches the searchRegex, and the
   * replacement should be the value specified in the constructor for this
   * class. Return null if there is no substring matching the searchRegex in the
   * input.
   * 
   * @param input The input string to get the first replacement for.
   * 
   * @return The first replacement to be made in the process of filtering the
   *         input. If no such replacement is necessary, returns null.
   * 
   * @throws NullPointerException Thrown if input is null.
   */
  public FilterReplacement getFirstReplacement(String input) {
    if (input == null) {
      throw new NullPointerException("input should not be null.");
    }

    Matcher matcher = this.searchRegex.matcher(input);

    if (!matcher.find()) {
      return null;
    }

    return new DefaultFilterReplacement(this.replacementString, matcher.start(), matcher.end());
  }

  /**
   * Creates an inclusion type regex-filter for the given regular expression, by
   * calling the RegexFilter(regex, EMPTY) constructor, and then wrapping the
   * result in a NegatingMatchFilter.
   * 
   * @param regex The regex pattern to search for.
   * 
   * @return An inclusion type regex filter.
   * 
   * @throws NullPointerException if regex is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   * @throws PatternSyntaxException if the expression's syntax is invalid.
   */
  public static MatchFilter createIncludeFilter(String regex) {
    return RegexFilter.createIncludeFilter(RegexFilter.compileRegex(regex));
  }

  /**
   * Creates an inclusion type regex-filter for the given regular expression, by
   * calling the RegexFilter(pattern, EMPTY) constructor, and then wrapping the
   * result in a NegatingMatchFilter.
   * 
   * @param pattern The pattern to search for.
   * 
   * @return An inclusion type regex filter.
   * 
   * @throws NullPointerException if pattern is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   */
  public static MatchFilter createIncludeFilter(Pattern pattern) {
    return new NegatingMatchFilter(new RegexFilter(pattern, FilterUtility.EMPTY));
  }

  /**
   * Creates an exclusion type regex-filter for the given regular expression, by
   * calling the RegexFilter(regex, EMPTY) constructor.
   * 
   * @param regex The regex pattern to search for.
   * 
   * @return An exclusion type regex filter.
   * 
   * @throws NullPointerException if regex is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   * @throws PatternSyntaxException if the expression's syntax is invalid.
   */
  public static MatchFilter createExcludeFilter(String regex) {
    return RegexFilter.createExcludeFilter(RegexFilter.compileRegex(regex));
  }

  /**
   * Creates an exclusion type regex-filter for the given regular expression, by
   * calling the RegexFilter(pattern, EMPTY) constructor.
   * 
   * @param pattern The pattern to search for.
   * 
   * @return An exclusion type regex filter.
   * 
   * @throws NullPointerException if pattern is null.
   * @throws IllegalArgumentException if pattern.matcher("").matches().
   */
  public static MatchFilter createExcludeFilter(Pattern pattern) {
    return new RegexFilter(pattern, FilterUtility.EMPTY);
  }

  /**
   * Compile a regex into Pattern instance without any flags.
   * 
   * @param regex the regex to compile.
   * 
   * @return the compiled regex pattern.
   * 
   * @throws NullPointerException if regex is null.
   * @throws PatternSyntaxException if the expression's syntax is invalid.
   */
  private static Pattern compileRegex(String regex) {
    if (regex == null) {
      throw new NullPointerException("regex should not be null.");
    }
    return Pattern.compile(regex);
  }

}
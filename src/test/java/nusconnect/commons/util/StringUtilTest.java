package nusconnect.commons.util;

import static nusconnect.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {

    //---------------- Tests for isNonZeroUnsignedInteger --------------------------------------

    @Test
    public void isNonZeroUnsignedInteger() {

        // EP: empty strings
        assertFalse(StringUtil.isNonZeroUnsignedInteger("")); // Boundary value
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));

        // EP: not a number
        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));

        // EP: zero
        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));

        // EP: zero as prefix
        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));

        // EP: signed numbers
        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));

        // EP: numbers with white space
        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 ")); // Leading/trailing spaces
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0")); // Spaces in the middle

        // EP: number larger than Integer.MAX_VALUE
        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));

        // EP: valid numbers, should return true
        assertTrue(StringUtil.isNonZeroUnsignedInteger("1")); // Boundary value
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for containsPartialIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsPartialIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsPartialIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsPartialIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", () ->
                StringUtil.containsPartialIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsPartialIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsPartialIgnoreCase(null, "abc"));
    }

    //---------------- Valid inputs tests ----------------------------

    @Test
    public void containsPartialIgnoreCase_emptySentence_returnsFalse() {
        // An empty sentence should return false, as nothing can be matched.
        assertFalse(StringUtil.containsPartialIgnoreCase("", "abc"));
    }

    @Test
    public void containsPartialIgnoreCase_emptyWord_returnsFalse() {
        // An empty word should not match anything
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", () ->
                StringUtil.containsPartialIgnoreCase("typical sentence", ""));
    }

    @Test
    public void containsPartialIgnoreCase_wordMatchesPartOfSentence() {
        // Check for a match where the word is part of a sentence
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "sent"));
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "Typ"));
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "ence"));
    }

    @Test
    public void containsPartialIgnoreCase_wordNotMatchingPartialSentence() {
        // Check for a match where the word doesn't match part of any sentence word
        assertFalse(StringUtil.containsPartialIgnoreCase("typical sentence", "xen"));
    }

    @Test
    public void containsPartialIgnoreCase_caseInsensitiveMatch() {
        // Case-insensitive matching
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "TYP"));
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "SEN"));
        assertTrue(StringUtil.containsPartialIgnoreCase("TYPICAL sentence", "typical"));
    }

    @Test
    public void containsPartialIgnoreCase_leadingTrailingSpacesInWord() {
        // Match with leading/trailing spaces in the word
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "  sen "));
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "typ "));
    }

    @Test
    public void containsPartialIgnoreCase_multipleSpacesInSentence() {
        // Test when the sentence has multiple spaces between words
        assertTrue(StringUtil.containsPartialIgnoreCase("typical    sentence", "sen"));
        assertTrue(StringUtil.containsPartialIgnoreCase("typical    sentence", "typ"));
    }

    @Test
    public void containsPartialIgnoreCase_exactMatch() {
        // Exact match using partial substring
        assertTrue(StringUtil.containsPartialIgnoreCase("typical sentence", "sentence"));
    }

    @Test
    public void containsPartialIgnoreCase_nonMatch() {
        // Non-match cases where the word doesn't exist anywhere in the sentence
        assertFalse(StringUtil.containsPartialIgnoreCase("typical sentence", "xyz"));
    }

    @Test
    public void containsPartialIgnoreCase_specialCharactersMatch() {
        // Test matching with special characters in the word
        assertTrue(StringUtil.containsPartialIgnoreCase("this is a #typical sentence!", "#typical"));
        assertFalse(StringUtil.containsPartialIgnoreCase("this is a #typical sentence!", "typ!"));
    }

    @Test
    public void containsPartialIgnoreCase_numericMatch() {
        // Check for match with numeric characters
        assertTrue(StringUtil.containsPartialIgnoreCase("123 456 789", "456"));
        assertTrue(StringUtil.containsPartialIgnoreCase("test 123 test", "123"));
        assertFalse(StringUtil.containsPartialIgnoreCase("test 123 test", "124"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}

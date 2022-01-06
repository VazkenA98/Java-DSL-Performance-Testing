package com.picsart.tests;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class TestUtils {

    public static final String SYMBOLS = "~!@#$%^&*-+=`|(){}[]:;\"',.?/<>";
    public static final String LINE_SYMBOL = "_";
    public static final String SPACE = " ";

    public static final String DIGITS = "0123456789";
    public static final String LOWER_CASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    public static final String UPPER_CASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String UPPER_CASE_CYRILLIC_CHARS = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String ARABIC_CHARS = "أبجدﻩوزحطيكلمنسعفصقرشتثخذضظغ";
    private static final AtomicInteger atomicCount = new AtomicInteger(0);

    /**
     * Generated random String.
     *
     * @param len
     * @return Random String with corresponding length.
     */
    public static String randomString(int len) {
        return RandomStringUtils.random(len, true, true);
    }

    /**
     * Generated random Lower Case String.
     *
     * @param len
     * @return Random String with corresponding length.
     */
    public static String randomLowerCaseString(int len) {
        return randomString(len).toLowerCase();
    }

    /**
     * Generated random Upper Case String.
     *
     * @param len
     * @return Random String with corresponding length.
     */
    public static String randomUpperCaseString(int len) {
        return randomString(len).toUpperCase();
    }

    /**
     * Generated random letters.
     *
     * @param len
     * @return Random Letters with corresponding length.
     */
    public static String randomLetters(int len) {
        return RandomStringUtils.random(len, true, false);
    }

    /**
     * Generated random Lower Case String.
     *
     * @param len
     * @return Random Letters with corresponding length.
     */
    public static String randomLowerCaseLetters(int len) {
        return randomLetters(len).toLowerCase();
    }

    /**
     * Generated random numbers.
     *
     * @param len
     * @return Random numbers with corresponding length.
     */
    public static String randomNumber(int len) {
        return RandomStringUtils.random(len, false, true);
    }


    /**
     * Generated random Password.
     *
     * @return Random password length ranges from 8 to 16 beacuse the valid password length ranges in the same way
     */
    public static String randomPassword() {
        return randomPassword(randomInt(8, 16));
    }


    /**
     * Generates random Wrong Password.
     *
     * @return Random password which contains: digits, lowerCase, upperCase
     */
    public static String wrongRandomPasswordWithNoLetters(int length) {
        final String all = SYMBOLS + LINE_SYMBOL + DIGITS;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(SYMBOLS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }


    /**
     * Generates random Wrong Password.
     *
     * @return Random password which contains: specials, lowerCase, upperCase
     */
    public static String wrongRandomPasswordWithNoDigit(int length) {
        final String all = LOWER_CASE_CHARS + UPPER_CASE_CHARS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(SYMBOLS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(UPPER_CASE_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random Wrong Password.
     *
     * @return Random password which contains: space, specials, digits lowerCase, upperCase
     */
    public static String wrongRandomPasswordWithSpace(int length) {
        final String all = LOWER_CASE_CHARS + SPACE + DIGITS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(SYMBOLS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(SPACE);
        appendChar.accept(DIGITS);
        appendChar.accept(UPPER_CASE_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random Password in Cyrillic language.
     *
     * @return Random password which contains: digits, specials, lowerCase, upperCase
     */
    public static String randomCyrillicPassword(int length) {
        final String lowerCaseCyrillicChars = UPPER_CASE_CYRILLIC_CHARS.toLowerCase();
        final String all = lowerCaseCyrillicChars + UPPER_CASE_CYRILLIC_CHARS + DIGITS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(SYMBOLS);
        appendChar.accept(lowerCaseCyrillicChars);
        appendChar.accept(UPPER_CASE_CYRILLIC_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random Password in Arabian language.
     *
     * @return Random password which contains: digits, specials, lowerCase, upperCase
     */
    public static String randomArabicPassword(int length) {
        final String all = ARABIC_CHARS + DIGITS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(SYMBOLS);
        appendChar.accept(ARABIC_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }


    /**
     * Generates random Password non-Latin.
     *
     * @return Random password which contains: digits, specials, lowerCase, upperCase
     */
    public static String randomNonLatinPassword(int length) {
        final String lowerCaseCyrillicChars = UPPER_CASE_CYRILLIC_CHARS.toLowerCase();
        final String all = ARABIC_CHARS + UPPER_CASE_CYRILLIC_CHARS + lowerCaseCyrillicChars + DIGITS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(SYMBOLS);
        appendChar.accept(ARABIC_CHARS);
        appendChar.accept(lowerCaseCyrillicChars);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }


    /**
     * Generates random Password.
     *
     * @return Random password which contains: digits, symbols, lowerCase, upperCase
     */
    public static String randomPassword(int length) {
        final String all = LOWER_CASE_CHARS + UPPER_CASE_CHARS + DIGITS + SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(SYMBOLS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(UPPER_CASE_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random Password with only Symbols.
     *
     * @return Random password which contains only symbols
     */
    public static String randomPasswordSymbols(int length) {
        final String all = SYMBOLS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(SYMBOLS);
        appendChar.accept(LINE_SYMBOL);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }


    /**
     * Generated random Username.
     *
     * @return Random Username length ranges from 3 to 20
     */
    public static String randomUsername() {
        return randomUsername(randomInt(10, 20));
    }

    /**
     * Generates random valid Username
     *
     * @return Random Username which contains letters, digits and line symbol
     */
    public static String randomUsername(int length) {
        final String all = LOWER_CASE_CHARS + DIGITS + LINE_SYMBOL;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(LINE_SYMBOL);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random invalid Username
     *
     * @return Random invalid Username which contains wrong symbols
     */

    public static String wrongUsernameWithSymbols(int length) {
        final String all = LOWER_CASE_CHARS + DIGITS + LINE_SYMBOL + SYMBOLS;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(SYMBOLS);
        appendChar.accept(LINE_SYMBOL);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random invalid Username
     *
     * @return Random invalid Username which contains space
     */

    public static String wrongUsernameWithSpace(int length) {
        final String all = LOWER_CASE_CHARS + DIGITS + LINE_SYMBOL + SPACE;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(SPACE);
        appendChar.accept(LINE_SYMBOL);
        while (result.size() < length) {
            appendChar.accept(all);
            result.add(SPACE);
        }
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generates random invalid Non-Latin Username
     *
     * @return Random invalid Username which contains Cyrillic and Arabian symbols
     */

    public static String wrongNonLatinUsername(int length) {
        final String all = LOWER_CASE_CHARS + DIGITS + LINE_SYMBOL + ARABIC_CHARS + UPPER_CASE_CYRILLIC_CHARS;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(DIGITS);
        appendChar.accept(LOWER_CASE_CHARS);
        appendChar.accept(UPPER_CASE_CYRILLIC_CHARS.toLowerCase());
        appendChar.accept(LINE_SYMBOL);
        appendChar.accept(ARABIC_CHARS);
        while (result.size() < length)
            appendChar.accept(all);
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

    /**
     * Generated random Email.
     * password: Test12@12
     * Note: Must be valid gmail
     *
     * @return Valid email.
     */
    public static String generateValidEmail() {
        return "pcsa2a+" + atomicCount.incrementAndGet() + currentTimeMillis(10) + "@gmail.com";
    }

    public static String currentTimeMillis(int amount) {
        String millis = String.valueOf(System.currentTimeMillis());
        return millis.substring(millis.length() - amount);
    }

    /**
     * Implemented this method bevause update email request must not contain + sign
     * Generated random Email.
     *
     * @return Test email.
     */
    public static String generateTestEmail() {
        return randomLowerCaseString(randomInt(4, 10)) + "@picsart.test";
    }

    /**
     * Restricted domains [163.com, yopmail.com]
     * Generated Restricted Email.
     *
     * @return Restricted domain Email.
     */
    public static String generateRestrictedEmail() {
        String[] restrictedDomains = new String[]{"@163.com", "@yopmail.com"};
        final String domain = getRandomDomain(restrictedDomains);
        return randomLowerCaseString(randomInt(4, 10)) + domain;
    }

    public static String getRandomDomain(String[] domains) {
        return domains[new Random().nextInt(domains.length)];
    }

    /**
     * Generated random Phone Number.
     *
     * @return Random Phone Number
     */
    public static String randomPhoneNumber() {
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);
        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros
        return df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);
    }

    /**
     * Generated random Site URL.
     *
     * @return Random Site URL starting with + https.
     */
    public static String randomSiteUrl() {
        return "https://www." + randomLowerCaseLetters(randomInt(4, 8)) + "." + randomLowerCaseLetters(randomInt(2, 4));
    }

    /**
     * Generated random Integer.
     *
     * @param count
     * @return Random integer with corresponding count.
     */
    public static int randomInt(int count) {
        return Integer.parseInt(randomNumber(count));
    }

    /**
     * Generated random Integer.
     *
     * @param min
     * @param max
     * @return Random integer in range [min, max]
     */
    public static int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    /**
     * Generated random UUID.
     *
     * @return Random UUID.
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generated random Image.
     *
     * @return Random Imagem with random filename.
     */
    @SneakyThrows
    public static File createRandomImage() {
        return createRandomImage(randomLetters(randomInt(4, 8)), 256, 256);
    }

    /**
     * Generated random Image.
     *
     * @param filename
     * @return Random Image.
     */
    @SneakyThrows
    public static File createRandomImage(String filename) {
        return createRandomImage(filename, 256, 256);
    }

    /**
     * Generated random Image.
     *
     * @param filename
     * @param width:   image dimension
     * @param height:  image dimension
     * @return Random File.
     */
    @SneakyThrows
    public static File createRandomImage(String filename, int width, int height) {
        int colorRed = (int) (Math.random() * 256);
        int colorGreen = (int) (Math.random() * 256);
        int colorBlue = (int) (Math.random() * 256);
        //create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = img.createGraphics();
        graphics.setPaint(new Color(colorRed, colorGreen, colorBlue));
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        //file object
        File f = new File(filename);
        //create random image pixel by pixel
        //write image
        ImageIO.write(img, "png", f);
        f.deleteOnExit();
        return f;
    }

    /**
     * @param queryParams Elements at an even index are used as keys for the elements at the odd index.
     *                    Elements at an even index are asserted to be Strings.
     * @return mutable map containing the provided elements
     */
    public static HashMap<String, Object> createMap(Object... queryParams) {
        assert queryParams.length % 2 == 0 : "Params must have an even number of elements.";
        HashMap map = new HashMap<>();
        for (int i = 0; i < queryParams.length; i += 2) {
            assert queryParams[i] instanceof String : "Keys must be strings.";
            map.put(queryParams[i], queryParams[i + 1]);
        }
        return map;
    }

    public static List<String> getAllRestrictedWords() {
        String filePath = "src/test/resources/restrictions/restrictedWords.txt";
        List<String> restrictedWords = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            stream.forEach(s -> restrictedWords.add(s));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restrictedWords;
    }

    public static String getRandomRestrictedWord() {
        List<String> restrictedWords = getAllRestrictedWords();
        return restrictedWords.get(randomInt(0, restrictedWords.size() - 1));
    }

    public static String getRandomString(String input, int index) {
        if (index > input.length() - 1)
            return null;
        return shuffle(input).substring(0, index);
    }

    public static String shuffle(String input) {
        List<Character> characters = new ArrayList<Character>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        StringBuilder output = new StringBuilder(input.length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }
        return output.toString();
    }

    /**
     * Modify the given string to get a similar one based on Levenshtein Distance
     *
     * @param s     initial String
     * @param score the maximum Levenshtein distance between initial and modified strings
     * @return the modified String
     * TODO  clean up the following method
     */
    public static String modify(String s, int score) {
        StringBuilder sb = new StringBuilder(s);
        int r;
        final int SIZE = LOWER_CASE_CHARS.length() - 1;
        for (int i = 0; i < score; i++) {
            int o = randomInt(0, 4); // 3 operations
            int l = sb.length();
            switch (o) {
                case 0: // Delete
                    r = randomInt(0, l);
                    sb.delete(r, r + 1);
                    break;
                case 1: // Insert
                    r = randomInt(1, l);
                    sb.insert(r, LOWER_CASE_CHARS.charAt(randomInt(0, SIZE)));
                    break;
                default: // Replace
                    r = randomInt(0, l);
                    sb.replace(r, r + 1, "" + LOWER_CASE_CHARS.charAt(randomInt(0, SIZE)));
            }
        }
        return sb.toString();
    }


}


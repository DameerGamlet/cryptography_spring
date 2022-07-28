package com.example.cryptography.content.caesar;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CaesarCipher {
    public StringBuilder convertString(Map<Integer, String> map, String key) {
        StringBuilder result = new StringBuilder();
        for (String item : key.split("")) {
            map.forEach((k, v) -> {
                if (v.equals(item)) result.append(item);
            });
        }
        return result;
    }

    public Map<Integer, String> createAbc() {
        int j = 0;
        Map<Integer, String> map = new LinkedHashMap<>();
        // буквы
        for (char i = 'А'; i <= 'Я'; i++) {
            map.put(j++, i + "");
            if (i == 'Е') map.put(j++, "Ё");
        }
        // числа
        for (int i = 0; i <= 9; i++) {
            map.put(j++, String.valueOf(i));
        }
        // знаки пунктуации
        for (Object k : new ArrayList<>(
                Arrays.asList('!', '"', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '_', '{', '}', ' ', '\n'))) {
            map.put(j++, "" + k);
        }
        return map;
    }

    public List<Integer> keyNumber(Map<Integer, String> map, List<String> keyList, String message, String key) {
        int mLength = message.length(), kLength = key.length(), j = 0;
        List<Integer> kNumber = new ArrayList<>();
        for (int i = 0; i < mLength; i++) {
            String element = keyList.get(j++);
            AtomicInteger atomicInteger = new AtomicInteger();
            map.forEach((k, v) -> {
                if (v.equals(element)) atomicInteger.set(k);
            });
            kNumber.add(atomicInteger.get());
            if (j == kLength) j = 0;
        }
        return kNumber;
    }

    public String encrypt(String message, String key) {
        Map<Integer, String> map = createAbc();
        message = String.valueOf(convertString(map, message.toUpperCase()));
        key = String.valueOf(convertString(map, key.toUpperCase()));

        List<Integer> keyNumber = keyNumber(map, Arrays.asList(key.split("")), message, key);
        List<String> en = new ArrayList<>(), list = Arrays.asList(message.split(""));
        int j = 0, len = map.size();
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(j);
            int textNumber = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(item)) {
                    textNumber = entry.getKey();
                    break;
                }
            }
            // новый результат
            int resultNumber = textNumber + keyNumber.get(j++);
            en.add(map.get(resultNumber > len - 1 ? resultNumber % len : resultNumber));
        }
        String result = String.join("", en),
                res = result.length() == 0 ? null : result.substring(0, result.length() - 1);
        return String.join("", res);
    }

    public static List<Integer> getNumberList(Map<Integer, String> map, String message, String key) {
        int j = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            String element = Arrays.asList(key.split("")).get(j++);
            AtomicInteger indexCurrent = new AtomicInteger();

            map.forEach((k, v) -> {
                if (v.equals(element)) indexCurrent.set(k);
            });
            list.add(indexCurrent.get());
            if (j == key.length()) j = 0;
        }
        return list;
    }

    public String decrypt(String message, String key) {
        key = key.toUpperCase();
        Map<Integer, String> map = createAbc();
        map.forEach((k, v) -> System.out.println(k + " -> " + v));

        List<Integer> keyListInt = getNumberList(map, message, key);
        List<String> de = new ArrayList<>(),
                list = Arrays.asList(message.split(""));
        int j = 0, len = map.size();
        for (int i = 0; i < list.size(); i++) {
            int indexCurrent = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(list.get(j))) {
                    indexCurrent = entry.getKey();
                    break;
                }
            }
            int newIndex = indexCurrent - keyListInt.get(j++);
            if (newIndex < 0) newIndex = len - Math.abs(newIndex);
            de.add(map.get(newIndex > len - 1 ? newIndex % len : newIndex));
        }
        String result = String.join("", de);
        return result.length() == 0 ? null : result.substring(0, result.length() - 1);
    }
}

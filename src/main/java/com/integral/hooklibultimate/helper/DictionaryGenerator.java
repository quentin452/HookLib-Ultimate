package com.integral.hooklibultimate.helper;

import org.apache.commons.io.FileUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Генерирует из mcp-шного methods.csv словарь с названиями методов для хуклибы.
 * Файл methods.csv лежит в mcp/conf/
 *
 * Настоятельно рекомендую сгенерировать methods.bin самостоятельно для своей версии mcp, иначе могут быть
 * внезапные ошибки уровня "can not find target method of hook".
 */
public class DictionaryGenerator {

    public static Path curDir = Paths.get(".").toAbsolutePath().normalize();

    public static void main(String[] args) throws Exception {
        // List<String> lines = FileUtils.readLines(Paths.get(("/Users/veritaris/Documents/Study/Projects/ArcaneFactoryMods/AnomalyFix/src/main/java/hooklib/helper/methods.csv")).toFile());
        List<String> lines = FileUtils.readLines(new File(String.format("%s/%s", curDir, "/methods.csv")));
        lines.remove(0);
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        for (String str : lines) {
            String[] splitted = str.split(",");
            int first = splitted[0].indexOf('_');
            int second = splitted[0].indexOf('_', first+1);
            int id = Integer.valueOf(splitted[0].substring(first+1, second));
            map.put(id, splitted[1]);
        }

        DataOutputStream out = new DataOutputStream(new FileOutputStream("methods.bin"));
        out.writeInt(map.size());

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            out.writeInt(entry.getKey());
            out.writeUTF(entry.getValue());
        }

        out.close();

    }
}

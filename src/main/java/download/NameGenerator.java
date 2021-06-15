package download;


import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameGenerator {

    public static String genName(String url) {
        Pattern withSuf = Pattern.compile("(?!.*/)[a-zA-Z]+\\.[a-zA-Z]+$");
        Matcher m = withSuf.matcher(url);
        StringBuilder fullName = new StringBuilder();
        while (m.find()) {
            fullName.append(m.group());
        }
        return addNumber(fullName.toString());
    }

    private static String addNumber(String name) {
        String[] file = name.split("\\.");
        Pattern p = Pattern.compile("\\d");
        Matcher m = p.matcher(name);
        FileWalker walker = new FileWalker(n -> n.getFileName().toString().equals(name));
        try {
            Set<FileVisitOption> options = new HashSet<>();
            Files.walkFileTree(Path.of(""), options, 1, walker);
            if (walker.nameIsExist()) {
                    int number = -1;
                    while (m.find()) {
                        number = Integer.parseInt(m.group());
                    }
                    if (number != -1) {
                        String newName = name.replace(String.valueOf(number), String.valueOf(number + 1));
                        return addNumber(newName);
                    }
                return addNumber(file[0] + 0 + "." + file[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }
}

package io.github.ctlove0523.commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Tools {

	/**
	 * 将一个java原文件中的蛇形字段修改为小驼峰，并添加JsonProperty等注解
	 *
	 * @param fileName 源文件
	 * @throws IOException
	 */
	public static void snakeToCamel(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		List<String> newLines = new ArrayList<>();
		for (String line : lines) {
			if (line.startsWith("package")) {
				newLines.add(line + "\n");
				newLines.add("import com.fasterxml.jackson.annotation.JsonProperty;\n"
						+ "import lombok.Getter;\n"
						+ "import lombok.Setter;");
			}
			else if (line.startsWith("public class")) {
				newLines.add("@Getter\n"
						+ "@Setter");
				newLines.add(line);
			}
			else if (line.contains("_")) {
				String[] words = line.split(" ");
				String filedName = words[words.length - 1];
				String[] filedNameWords = filedName.split("_");
				StringBuilder sb = new StringBuilder();
				sb.append(filedNameWords[0]);

				for (int i = 1; i < filedNameWords.length; i++) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(Character.toUpperCase(filedNameWords[i].charAt(0)));
					for (int j = 1; j < filedNameWords[i].length(); j++) {
						sb1.append(filedNameWords[i].charAt(j));
					}
					sb.append(sb1.toString());
				}

				String newFiledName = sb.toString();
				newLines.add("    @JsonProperty(\"" + filedName.replace(";", "") + "\")");
				newLines.add(line.replace(filedName, newFiledName) + "\n");
			}
			else {
				newLines.add(line + "\n");
			}
		}

		Files.write(Paths.get(fileName), newLines);
	}
}

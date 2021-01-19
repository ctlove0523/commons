package io.github.ctlove0523.commons;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Tools {
	
	/**
	 * 读取一个project下所有的pom文件
	 *
	 * @param directory project名（绝对路径）
	 * @return project下所有的pom文件，绝对路径
	 */
	public static Map<String, List<String>> readAllPomFilesFromDirectory(String directory) throws Exception {
		File[] files = new File(directory).listFiles();
		if (files == null || files.length == 0) {
			return new HashMap<>();
		}
		Map<String, List<String>> project2poms = new HashMap<>();
		Arrays.stream(files).forEach(new Consumer<File>() {
			@Override
			public void accept(File file) {
				project2poms.put(file.getName(), new ArrayList<>());
				readAllFilesInDirectory(file.getAbsolutePath(), project2poms.get(file.getName()));
			}
		});


		return project2poms;
	}

	private static void readAllFilesInDirectory(String directory, List<String> output) {
		File file = new File(directory);
		if (file.isFile() && file.getName().contains("pom.xml")) {
			output.add(file.getAbsolutePath());
		}
		else {
			File[] subFiles = file.listFiles();
			if (subFiles == null || subFiles.length == 0) {
				return;
			}

			for (File f : subFiles) {
				if (f != null) {
					readAllFilesInDirectory(f.getAbsolutePath(), output);
				}
			}
		}
	}

	public static Set<String> readDependenciesFromFile(String fileName) throws Exception {
		Set<String> dependencies = new HashSet<>();
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		for (int i = 0; i < lines.size(); ) {
			if (lines.get(i).contains("groupId")) {
				int beginIndex = lines.get(i).indexOf(">");
				int endIndex = lines.get(i).lastIndexOf("<");
				String groupId = lines.get(i).substring(beginIndex + 1, endIndex);

				beginIndex = lines.get(i + 1).indexOf(">");
				endIndex = lines.get(i + 1).lastIndexOf("<");
				String artifactId = lines.get(i + 1).substring(beginIndex + 1, endIndex);
				dependencies.add(groupId + ":" + artifactId);
				i = i + 2;
			}
			else {
				i++;
			}
		}
		return dependencies;
	}

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

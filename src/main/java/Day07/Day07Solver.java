package Day07;

import java.util.*;
import java.util.stream.Collectors;

public class Day07Solver {
    Map<String, FileContent> tree;

    public Day07Solver(List<String> input) {
        this.tree = createTree(input);
    }

    public int solveDay07a() {
        return tree.values().stream()
                .filter(fileContent -> fileContent.isDir)
                .map(fileContent -> fileContent.size)
                .filter(size -> size <= 100000)
                .reduce(0, Integer::sum);
    } // High: 4693413; Low: 819055l High: 1145643

    public int solveDay07b() {
        FileContent root = tree.get("///");
        int free = 70000000 - tree.get("///").size;
        int toFree = 30000000 - free;
        return tree.values().stream()
                .filter(fileContent -> fileContent.isDir)
                .filter(fileContent -> fileContent.size >= toFree)
                .map(fileContent -> fileContent.size)
                .sorted()
                .collect(Collectors.toList())
                .get(0);
    }
    private Map<String, FileContent> createTree(List<String> commands) {
        FileContent currentFolder = new FileContent("///", "", true);
        Map<String, FileContent> createdFileContents = new HashMap<>();
        createdFileContents.put(currentFolder.name, currentFolder);
        while (commands.size() > 0) {
            String command = commands.remove(0);
            if (command.startsWith("$ ls")) {
                continue;
            }
            if (command.startsWith("$ cd")) {
                String name = command.substring(5);
                if (name.equals("..")) {
                    currentFolder = createdFileContents.get(currentFolder.parent);
                } else {
                    name = currentFolder.name + "/" + name;
                    if (!createdFileContents.containsKey(name)) {
                        createdFileContents.put(name, new FileContent(name, currentFolder.name, true));
                    }
                    currentFolder = createdFileContents.get(name);
                }
            } else {
                String[] commandParts = command.split(" ");
                String name = currentFolder.name + "/" + commandParts[1];
                if (commandParts[0].equals("dir")) {
                    createdFileContents.put(name, new FileContent(name, currentFolder.name, true));
                } else {
                    createdFileContents.put(name, new FileContent(name, currentFolder.name, Integer.parseInt(commandParts[0])));
                }
            }
        }
        createdFileContents.values().stream()
                .filter(fileContent -> fileContent.isDir)
                .forEach(fileContent -> fileContent.setSize(new ArrayList<>(createdFileContents.values())));
        System.out.println(createdFileContents.values());
        return createdFileContents;
    }

    static class FileContent {
        String name;
        String parent;
        int size;
        boolean isDir;

        FileContent(String name, String parent, boolean isDir) {
            this.name = name;
            this.parent = parent;
            this.size = 0;
            this.isDir = isDir;
        }

        FileContent(String name, String parent, int size) {
            this.name = name;
            this.parent = parent;
            this.size = size;
            this.isDir = false;
        }

        public void setSize(List<FileContent> fileContents) {
            if (size != 0) {
                return;
            }
            List<FileContent> children = fileContents.stream()
                    .filter(fileContent -> fileContent.parent.equals(this.name))
                    .collect(Collectors.toList());
            children.stream()
                    .filter(child -> child.isDir)
                    .filter(child -> child.size == 0)
                    .forEach(child -> child.setSize(fileContents));
            size = children.stream()
                    .map(child -> child.size)
                    .reduce(0, Integer::sum);
        }

        @Override
        public String toString() {
            return name + " " + size + " " + isDir;
        }
    }
}



//            if (command.startsWith("$")) {
//                String[] commandParts = command.substring(2).split(" ");
//                switch (commandParts[0]) {
//                    case "cd":
//                        if (commandParts[1].equals("..")) {
//                            currentFolder = createdFileContents.get(currentFolder.parent);
//                        } else
//                        if (createdFileContents.keySet().contains(commandParts[1])) {
//                            currentFolder = createdFileContents.get(commandParts[1]);
//                        } else {
//                            createdFileContents.put(commandParts[1], new FileContent(commandParts[1], currentFolder.name, true));
//                            currentFolder = createdFileContents.get(commandParts[1]);
//                        }
//                        break;
//                    case "ls":
//                        while (commands.size() > 0 && !commands.get(0).startsWith("$")) {
//                            command = commands.remove(0);
//                            commandParts = command.split(" ");
//                            if (commandParts[0].equals("dir")) {
//                                createdFileContents.put(commandParts[1], new FileContent(commandParts[1], currentFolder.name, true));
//                            } else {
//                                createdFileContents.put(commandParts[1], new FileContent(commandParts[1], currentFolder.name, Integer.parseInt(commandParts[0])));
//                            }
//                        }
//                        break;
//                    default:
//                        throw new IllegalArgumentException("illegal command: " + commandParts[0]);
//                }
//            }

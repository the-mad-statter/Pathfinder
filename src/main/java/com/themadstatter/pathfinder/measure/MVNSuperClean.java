// TODO: remove MVNSuperClean class and associated Apache StringUtils dependency

package com.themadstatter.pathfinder.measure;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MVNSuperClean {

    static void deleteDirRecur(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                if (e == null) {
                    int n = StringUtils.countMatches(dir.toString(), "combo-box-app");
                    System.out.println(n);

                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                } else {
                    throw e;
                }
            }
        });
    }

    public static void main(String[] args) throws IOException {
        deleteDirRecur(Paths.get("C:/Users/schuelke/Documents/github/Pathfinder/target"));
    }
}

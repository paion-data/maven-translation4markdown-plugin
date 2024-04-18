package com.paiondata.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GitChanges {

    public static void main(String[] args) {
        String projectDir = System.getProperty("user.dir");
        String gitRepoPath = projectDir + File.separator + ".git";
        System.out.println("Local Git repository path: " + gitRepoPath);

        File gitDir = new File(gitRepoPath);
        try (Repository repository = new FileRepositoryBuilder().setGitDir(gitDir).build()) {
            try (Git git = new Git(repository)) {
                // 指定目录路径
                String directoryPath = "docs"; // 替换为你想要检查的目录路径

                List<DiffEntry> diffs = git.diff()
                        .setOldTree(prepareTreeParser(repository, "HEAD^", directoryPath))
                        .setNewTree(prepareTreeParser(repository, "HEAD", directoryPath))
                        .call();

                // 输出变更的文件名
                for (DiffEntry diff : diffs) {
                    System.out.println("Change Type: " + diff.getChangeType());
                    System.out.println("Old Path: " + diff.getOldPath());
                    System.out.println("New Path: " + diff.getNewPath());
                }
            }
        } catch (IOException | org.eclipse.jgit.api.errors.GitAPIException e) {
            e.printStackTrace();
        }
    }

    private static ObjectId getHeadObjectId(Repository repository, String revision) throws IOException {
        return repository.resolve(revision + "^{tree}");
    }

    private static CanonicalTreeParser prepareTreeParser(Repository repository, String revision, String directoryPath) throws IOException {
        try (ObjectReader reader = repository.newObjectReader()) {
            ObjectId head = getHeadObjectId(repository, revision);
            try (Git git = new Git(repository)) {
                // 获取指定目录的树对象
                String treePath = head.getName() + ":" + directoryPath;
                ObjectId treeId = git.getRepository().resolve(treePath);
                return new CanonicalTreeParser(null, reader, treeId);
            }
        }
    }
}

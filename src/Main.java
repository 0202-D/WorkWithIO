import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author Dm.Petrov
 * DATE: 06.07.2022
 */
public class Main {
    private static final StringBuilder LOGGER = new StringBuilder();

    public static void main(String[] args) {
        //Task1
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\src"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\res"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\temp"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\src\\main"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\src\\test"));
        createFile(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\src\\main\\Main.java"));
        createFile(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\src\\main\\Utils.java"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\res\\drawables"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\res\\vectors"));
        createDir(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\res\\icons"));
        createFile(new File("C:\\IdeaProjects\\WorkWithIO\\Games\\temp\\temp.txt"));
        try (FileWriter writer = new FileWriter("C:\\IdeaProjects\\WorkWithIO\\Games\\temp\\temp.txt", true)) {
            writer.write(LOGGER.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Task2
        GameProgress gameProgress1 = new GameProgress(100, 1, 1, 0.0);
        GameProgress gameProgress2 = new GameProgress(80, 3, 2, 10.0);
        GameProgress gameProgress3 = new GameProgress(50, 7, 5, 100.0);
        saveGame("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save1.dat", gameProgress1);
        saveGame("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save2.dat", gameProgress2);
        saveGame("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save3.dat", gameProgress3);
        List<String> paths = new ArrayList<>();
        paths.add("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save1.dat");
        paths.add("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save2.dat");
        paths.add("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\save3.dat");
        zipFiles("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\savegames.zip", paths);
        deleteNotZipFiles("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\");
        // Task3
        openZip("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\savegames.zip", "C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\openzip");
        System.out.println(openProgress("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\openzip\\save1.dat"));
        System.out.println(openProgress("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\openzip\\save2.dat"));
        System.out.println(openProgress("C:\\IdeaProjects\\WorkWithIO\\Games\\savegames\\openzip\\save3.dat"));
    }

    public static void createDir(File file) {
        if (file.mkdir()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            LOGGER.append("Create dir : ").append(file.getName()).append(" ").append(dateFormat.format(date)).append("\n");
        }
    }

    public static void createFile(File file) {
        try {
            if (file.createNewFile()) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                LOGGER.append("Create file : ").append(file.getName()).append(" ").append(dateFormat.format(date)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(String savePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

    public static void zipFiles(String path, List<String> paths) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (int i = 0; i < paths.size(); i++) {
                FileInputStream fis = new FileInputStream(paths.get(i));
                ZipEntry entry = new ZipEntry("save" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteNotZipFiles(String path) {
        File dir = new File(path);
        for (File myFile : Objects.requireNonNull(dir.listFiles()))
            if (!myFile.getName().endsWith("zip")) myFile.delete();
    }

    public static void openZip(String path, String targetPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(path))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(targetPath + "\\" + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        }
    }

}


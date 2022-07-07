import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dm.Petrov
 * DATE: 06.07.2022
 */
public class Main {
    private static final StringBuilder LOGGER = new StringBuilder();

    public static void main(String[] args) {
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
}

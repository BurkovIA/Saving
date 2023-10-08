import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    static String saveDirectory = "C:/Game/savegames";
    public static void main(String[] args) {
        GameProgress gameProgress = new GameProgress(100, 100, 1, 2);
                                    new GameProgress(90, 90, 2, 5);
                                    new GameProgress(80, 90, 3, 10);

        ArrayList<String> listSave = new ArrayList<>();
        listSave.add(saveDirectory + "//save1.dat");
        listSave.add(saveDirectory + "//save2.dat");
        listSave.add(saveDirectory + "//save3.dat");

        ArrayList<String> listZip = new ArrayList<>();
        listZip.add(saveDirectory + "/saveZip1.zip");
        listZip.add(saveDirectory + "/saveZip2.zip");
        listZip.add(saveDirectory + "/saveZip3.zip");

        saveGame(listSave, gameProgress);

        zipFiles(listZip, listSave);

        fileNoZip(listZip, listSave);
    }
    private static void fileNoZip(ArrayList<String> listZip, ArrayList<String> listSave) {

        for (String fileName : listSave) {
            File fileDelete = new File(fileName);
            if (fileName.contains(".dat")) {
                fileDelete.delete();
            }
        }
        for (String fileName2 : listZip) {
            File fileDelete = new File(fileName2);
            if (fileName2.contains(".dat")) {
                fileDelete.delete();
            }
        }
    }
    private static void zipFiles(ArrayList<String> listZip, ArrayList<String> listSave) {
        for (int i = 0; i < listZip.size(); i++) {
            String zipFile = listZip.get(i);
            String saveFile = listSave.get(i);
            try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
                 FileInputStream fis = new FileInputStream(saveFile)) {
                ZipEntry entry = new ZipEntry(saveFile);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    static void saveGame(ArrayList<String> listSave, GameProgress gameProgress) {
        for (String fileName : listSave) {
            try (FileOutputStream fos = new FileOutputStream(fileName);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(gameProgress);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

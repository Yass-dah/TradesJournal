package fx.tradesjournal.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePersistenceManager {
    private static final String dataPath = "data";

    public static File dataFolderExists(){
        File folder = new File(dataPath);
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static List<String> getJournalFiles(){
        File folder = dataFolderExists();
        List<String> journalNames = new ArrayList<String>();
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                String cleanName = fileName.substring(0, fileName.length() - 5);
                journalNames.add(cleanName);
            }
        }
        return journalNames;
    }

    public static int getJournalsQt(){
        File folder = dataFolderExists();
        File[] files = folder.listFiles();
        return files == null ? 0 : files.length;
    }

    public static boolean isDataFolderEmpty(){
        return getJournalsQt() == 0;
    }
}

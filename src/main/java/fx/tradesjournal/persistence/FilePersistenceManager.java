package fx.tradesjournal.persistence;

import com.google.gson.Gson;
import fx.tradesjournal.model.Journal;
import fx.tradesjournal.model.Trade;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilePersistenceManager {
    private static final String dataPath = "data";
    private static final Gson gson = new Gson();

    public static File createJournalFile(Journal journal) {
        File folder = dataFolderExists();
        File file = new File(folder, journal.getName() + ".json");

        if(file.exists())
            return null;

        return writeJournalToFile(journal, file);
    }

    public static boolean saveJournal(Journal journal) {
        if (journal == null || journal.getName() == null)
            return false;

        File folder = dataFolderExists();
        File file = new File(folder, journal.getName() + ".json");

        return writeJournalToFile(journal, file) != null;
    }

    public static boolean addTradeToJournal(Journal journal, Trade trade) {
        if (journal == null || trade == null)
            return false;

        journal.getTrades().add(trade);
        return saveJournal(journal);
    }

    public static Journal loadJournal(String journalName){
        File file = new File(dataFolderExists(), journalName + ".json");

        if (!file.exists()) {
            System.out.println("Error: can't find file " + file.getPath());
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Journal.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static File writeJournalToFile(Journal journal, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(journal, writer);
            return file;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static File dataFolderExists(){
        File folder = new File(dataPath);
        if(!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static List<String> getJournalFiles(){
        File folder = dataFolderExists();
        if(isDataFolderEmpty()) return null;
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

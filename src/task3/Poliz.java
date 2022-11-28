package task3;

import task1.files.ReadFile;
import task1.files.WriteFiles;
import task4.Interpreter;

import java.util.ArrayList;
import java.util.List;

public class Poliz {
    public List<PolizEntry> allPolizEntries;

    public Poliz() {
        this.allPolizEntries = new ArrayList<PolizEntry>();
    }

    public void addNew(EnEntryType entryType, String lexema) {
        allPolizEntries.add(new PolizEntry(entryType, lexema));
    }

    public void addNew(PolizEntry polizEntry) {
        allPolizEntries.add(polizEntry);
    }

    public void addNew(int ind, EnEntryType entryType, String lexema) {
        allPolizEntries.add(ind, new PolizEntry(entryType, lexema));
    }

    public List<PolizEntry> getAllPolizEntries() {
        return allPolizEntries;
    }

    public void print() {
        WriteFiles.writeFile2("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/FL_Labs/src/poliz.txt" ,this.allPolizEntries);

        List<Double> varValues = ReadFile.readFileForInterpreter("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/FL_Labs/src/varValues.txt");
        Interpreter interpreter = new Interpreter(this, varValues);
        interpreter.interpreterWork();
        WriteFiles.writeFile1("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/FL_Labs/src/interpreter.txt", interpreter.getVarCurrValues());
    }

    public void setAllPolizEntries(List<PolizEntry> allPolizEntries) {
        this.allPolizEntries = allPolizEntries;
    }
}

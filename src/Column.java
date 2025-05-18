import java.util.ArrayList;
import java.util.List;

public class Column {
    private Object label;
    private List<Cell> cells;

    public Column(Object label) {
        this.label = label;
        this.cells = new ArrayList<>();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public Cell getCell(int index) {
        return cells.get(index);
    }

    public int size() {
        return cells.size();
    }
}

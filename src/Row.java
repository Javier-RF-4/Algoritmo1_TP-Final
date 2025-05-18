import java.util.List;

public class Row {
    private int rowIndex;
    private List<Column> columns; // Referencia a las columnas originales

    public Row(int rowIndex, List<Column> columns) {
        this.rowIndex = rowIndex;
        this.columns = columns;
    }

    public Cell getCell(int columnIndex) {
        return columns.get(columnIndex).getCell(rowIndex);
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class DataFrame {

    private List<Column> columns;
    private List<Row> rows;

    // Constructor por defecto
    public DataFrame() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }


    // Constructor desde lista de listas
    public DataFrame(List<List<Object>> data, List<Object> labels) {
        this();
        if (data.isEmpty() || labels.size() != data.get(0).size()) {
            throw new IllegalArgumentException("Datos o labels inválidos");
        }

        // Poblar columnas
        for (int i = 0; i < labels.size(); i++) {
            Column column = new Column(labels.get(i));
            for (List<Object> row : data) {
                column.addCell(new Cell(row.get(i)));
            }
            columns.add(column);
        }

        // Crear vistas de filas
        for (int i = 0; i < data.size(); i++) {
            rows.add(new Row(i, columns));
        }
    }

    // Métodos de acceso
    public Column getColumn(int index) {
        return columns.get(index);
    }

    public Row getRow(int index) {
        return rows.get(index);
    }

}
